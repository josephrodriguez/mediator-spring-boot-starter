package com.aureum.springboot.service;

import com.aureum.springboot.interfaces.Event;
import com.aureum.springboot.interfaces.EventHandler;
import com.aureum.springboot.interfaces.Publisher;
import com.aureum.springboot.core.Lazy;
import com.aureum.springboot.exceptions.UnsupportedEventException;
import com.aureum.springboot.processor.EventHandlerProcessor;
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
public class Mediator implements Publisher {

    /**
     *
     */
    private final Lazy<ConcurrentMap<Class<?>, EventHandlerProcessor>> eventHandlersMap;

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

        ConcurrentMap<Class<?>, EventHandlerProcessor> map = eventHandlersMap.get();

        if (!map.containsKey(event.getClass()))
            throw new UnsupportedEventException(event.getClass());

        EventHandlerProcessor<TEvent> processor = map.get(event.getClass());

        processor.handle(event);
    }

    /**
     * @param factory
     * @return
     */
    private ConcurrentMap<Class<?>, EventHandlerProcessor> getEventHandlersMap(ListableBeanFactory factory) {

        Map<Class<?>, EventHandlerProcessor> handlersMap = factory
                .getBeansOfType(EventHandler.class)
                .values()
                .stream()
                .collect(Collectors.groupingBy(handler -> getEventTypeArgument(handler.getClass())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new EventHandlerProcessor(entry.getValue())));

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
