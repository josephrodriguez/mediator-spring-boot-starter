package io.github.josephrodriguez;

import io.github.josephrodriguez.core.Lazy;
import io.github.josephrodriguez.exceptions.UnsupportedEventException;
import io.github.josephrodriguez.exceptions.UnsupportedRequestException;
import io.github.josephrodriguez.executors.EventHandlerAggregateExecutor;
import io.github.josephrodriguez.interfaces.*;
import org.springframework.beans.factory.ListableBeanFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static io.github.josephrodriguez.core.BooleanExtensions.not;

/**
 *
 */
public class Mediator implements Publisher, Sender {

    /**
     *
     */
    private final Lazy<ConcurrentMap<Class<?>, EventHandlerAggregateExecutor>> eventHandlersMap;

    /**
     *
     */
    private final Lazy<ConcurrentMap<Class<?>, RequestHandler>> requestHandlersMap;

    /**
     * @param factory Instance of {@link ListableBeanFactory} to list the EventHandler and RequestHandler beans
     */
    public Mediator(ListableBeanFactory factory) {
        this.eventHandlersMap = Lazy.of(() -> getEventHandlersMap(factory));
        this.requestHandlersMap = Lazy.of(() -> getRequestHandlers(factory));
    }

    /**
     * @param event The event to handle
     * @param <T> Type of class which implements Event marker interface
     * @throws UnsupportedEventException When there is not registered {@code EventHandler<TEvent>} to handle the event type
     */
    @Override
    public <T extends Event> void publish(T event) throws UnsupportedEventException {
        if (event == null)
            throw new IllegalArgumentException("Undefined event argument.");

        Class<? extends Event> eventClazz = event.getClass();

        boolean supportedEvent = eventHandlersMap
                .get()
                .containsKey(eventClazz);

        boolean unsupportedEvent = not(supportedEvent);
        if (unsupportedEvent)
            throw new UnsupportedEventException(eventClazz);

        EventHandlerAggregateExecutor processor = eventHandlersMap
                .get()
                .get(eventClazz);

        processor.handle(event);
    }

    @Override
    public <T extends Event> CompletableFuture<Void> publishAsync(T event) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                publish(event);
            }
            catch (UnsupportedEventException ex) {
                throw new CompletionException(ex);
            }

            return null;
        });
    }

    /**
     * @param request Request to be handled
     * @param <T> Type of the response
     * @return The result of routing the request to the corresponding {@code RequestHandler<Request, Response>} instance
     * @throws UnsupportedRequestException When there is no registered bean that implements {@code RequestHandler<Request, Response>} interface
     */
    @Override
    public <T> T send(Request<T> request) throws UnsupportedRequestException {
        if (request == null)
            throw new IllegalArgumentException("Undefined request argument");

        Class<? extends Request> requestClazz = request.getClass();

        boolean supportedRequest = requestHandlersMap
                .get()
                .containsKey(requestClazz);

        boolean unsupportedRequest = not(supportedRequest);
        if (unsupportedRequest)
            throw new UnsupportedRequestException(requestClazz);

        RequestHandler<Request<T>, T> handler = requestHandlersMap
                .get()
                .get(requestClazz);

        return handler.handle(request);
    }

    @Override
    public <T> CompletableFuture<T> sendAsync(Request<T> request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return send(request);
            }
            catch (UnsupportedRequestException ex) {
                throw new CompletionException(ex);
            }
        });
    }

    private ConcurrentMap<Class<?>, EventHandlerAggregateExecutor> getEventHandlersMap(ListableBeanFactory factory) {
        Map<Class<?>, EventHandlerAggregateExecutor> handlersMap = factory
                .getBeansOfType(EventHandler.class)
                .values()
                .stream()
                .collect(Collectors.groupingBy(handler -> getEventHandlerTypeArgument(handler.getClass())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new EventHandlerAggregateExecutor(entry.getValue())));

        return new ConcurrentHashMap<>(handlersMap);
    }

    private ConcurrentMap<Class<?>, RequestHandler> getRequestHandlers(ListableBeanFactory factory) {
        Map<Class<?>, RequestHandler> map = factory
                .getBeansOfType(RequestHandler.class)
                .values()
                .stream()
                .collect(Collectors.toMap(
                        handler -> getRequestHandlerTypeArgument(handler.getClass()),
                        handler -> handler
                ));

        return new ConcurrentHashMap<>(map);
    }

    /**
     * @param handlerClazz -
     * @return
     */
    private Class<?> getEventHandlerTypeArgument(Class<?> handlerClazz) {
        Optional<ParameterizedType> eventHandlerInterface = getGenericInterfaceParameterizedType(
                handlerClazz,
                EventHandler.class);

         Type[] arguments = eventHandlerInterface
                 .orElseThrow(() -> new IllegalArgumentException(String.format("%s does not implement interface %s", handlerClazz, EventHandler.class)))
                 .getActualTypeArguments();

         return (Class<?>) arguments[0];
    }

    /**
     * @param handlerClazz
     * @return
     */
    private Class<?> getRequestHandlerTypeArgument(Class<?> handlerClazz) {
        Optional<ParameterizedType> requestHandlerInterface = getGenericInterfaceParameterizedType(
                handlerClazz,
                RequestHandler.class);

        Type[] arguments = requestHandlerInterface
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s does not implement interface %s", handlerClazz, RequestHandler.class)))
                .getActualTypeArguments();

        return (Class<?>) arguments[0];
    }

    /**
     * @param objectClazz
     * @param interfaceClazz
     * @return
     */
    private Optional<ParameterizedType> getGenericInterfaceParameterizedType(Class<?> objectClazz, Class<?> interfaceClazz) {
        return
                Arrays.stream(objectClazz.getGenericInterfaces())
                        .filter(ParameterizedType.class::isInstance)
                        .map(ParameterizedType.class::cast)
                        .filter(type -> type.getRawType().equals(interfaceClazz))
                        .findFirst();
    }
}
