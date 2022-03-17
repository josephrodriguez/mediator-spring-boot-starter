package io.github.josephrodriguez.executors;

import io.github.josephrodriguez.interfaces.Event;
import io.github.josephrodriguez.interfaces.EventHandler;

import java.util.Collection;
import java.util.Collections;

/**
 * @param <T> Type of event for the handlers aggregate
 */
public class EventHandlerAggregateExecutor<T extends Event> {

    /**
     * Collection of event handlers
     */
    private final Collection<EventHandler<T>> handlers;

    /**
     * @param handlers Collection of handlers for generic Event
     */
    public EventHandlerAggregateExecutor(Collection<EventHandler<T>> handlers) {
        this.handlers = Collections.synchronizedCollection(handlers);
    }

    /**
     * @param event The event that will be handled for the EventHandler collection
     */
    public void handle(T event) {

        if(event == null)
            throw new IllegalArgumentException("Undefined event instance.");

        handlers.forEach(handler -> handler.handle(event));
    }
}
