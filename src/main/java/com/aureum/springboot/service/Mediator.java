package com.aureum.springboot.service;

import com.aureum.springboot.exceptions.UnsupportedRequestException;
import com.aureum.springboot.interfaces.*;
import com.aureum.springboot.core.Lazy;
import com.aureum.springboot.exceptions.UnsupportedEventException;
import com.aureum.springboot.executors.EventHandlerAggregateExecutor;
import org.springframework.beans.factory.ListableBeanFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static com.aureum.springboot.core.BooleanExtensions.not;

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
     * @param factory
     */
    public Mediator(ListableBeanFactory factory) {
        this.eventHandlersMap = new Lazy<>(() -> getEventHandlersMap(factory));
        this.requestHandlersMap = new Lazy<>(() -> getRequestHandlers(factory));
    }

    /**
     * @param event
     * @param <TEvent>
     */
    @Override
    public <TEvent extends Event> void publish(TEvent event) throws UnsupportedEventException {

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

    /**
     * @param request
     * @param <Response>
     * @return
     * @throws UnsupportedRequestException
     */
    @Override
    public <Response> Response send(Request<Response> request) throws UnsupportedRequestException {

        if (request == null)
            throw new IllegalArgumentException("Undefined request argument");

        Class<? extends Request> requestClazz = request.getClass();

        boolean supportedRequest = requestHandlersMap
                .get()
                .containsKey(requestClazz);

        boolean unsupportedRequest = not(supportedRequest);
        if (unsupportedRequest)
            throw new UnsupportedRequestException(requestClazz);

        RequestHandler<Request<Response>, Response> handler = requestHandlersMap
                .get()
                .get(requestClazz);

        Response response = handler.handle(request);

        return response;
    }

    /**
     * @param factory
     * @return
     */
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

    /**
     * @param factory
     * @return
     */
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

        Optional<ParameterizedType> interfaceParameterizedType = Arrays.stream(objectClazz.getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .filter(type -> type.getRawType().equals(interfaceClazz))
                .findFirst();

        return interfaceParameterizedType;
    }
}
