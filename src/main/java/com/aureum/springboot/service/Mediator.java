package com.aureum.springboot.service;

import com.aureum.springboot.interfaces.*;
import com.aureum.springboot.core.Lazy;
import com.aureum.springboot.exceptions.UnsupportedEventException;
import com.aureum.springboot.processor.EventHandlerAggregateExecutor;
import org.springframework.beans.factory.ListableBeanFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 *
 */
public class Mediator implements Publisher, Sender {

    /**
     *
     */
    private final Lazy<ConcurrentMap<Class<?>, EventHandlerAggregateExecutor>> eventHandlersMap;

    /**
     * @param factory
     */
    public Mediator(ListableBeanFactory factory) {
        this.eventHandlersMap = new Lazy<>(() -> getEventHandlersMap(factory));
    }

    /**
     * @param event
     * @param <TEvent>
     */
    @Override
    public <TEvent extends Event> void publish(TEvent event) throws UnsupportedEventException {

        if (event == null)
            throw new IllegalArgumentException("Undefined event argument.");

        ConcurrentMap<Class<?>, EventHandlerAggregateExecutor> map = eventHandlersMap.get();

        if (!map.containsKey(event.getClass()))
            throw new UnsupportedEventException(event.getClass());

        EventHandlerAggregateExecutor<TEvent> processor = map.get(event.getClass());

        processor.handle(event);
    }

    @Override
    public <Response> Response send(Request<Response> request) {
        return null;
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
                .collect(Collectors.groupingBy(handler -> getEventTypeArgument(handler.getClass())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new EventHandlerAggregateExecutor(entry.getValue())));

        return new ConcurrentHashMap<>(handlersMap);
    }

    /**
     * @param handlerClass -
     * @return
     */
    private Class<?> getEventTypeArgument(Class<?> handlerClass) {

        ParameterizedType handlerInterface = Arrays.stream(handlerClass.getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .filter(type -> type.getRawType().equals(EventHandler.class))
                .findFirst()
                .get();

         Type[] arguments = handlerInterface.getActualTypeArguments();

         return (Class<?>) arguments[0];
    }
}
