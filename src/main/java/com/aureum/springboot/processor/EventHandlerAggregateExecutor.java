package com.aureum.springboot.processor;

import com.aureum.springboot.interfaces.Event;
import com.aureum.springboot.interfaces.EventHandler;

import java.util.Collection;
import java.util.Collections;

/**
 * @param <TEvent>
 */
public class EventHandlerAggregateExecutor<TEvent extends Event> {

    /**
     *
     */
    private final Collection<EventHandler<TEvent>> handlers;

    /**
     * @param handlers
     */
    public EventHandlerAggregateExecutor(Collection<EventHandler<TEvent>> handlers) {
        this.handlers = Collections.synchronizedCollection(handlers);
    }

    /**
     * @param event
     */
    public void handle(TEvent event) {

        if(event == null)
            throw new IllegalArgumentException("Undefined event instance.");

        handlers.forEach(handler -> handler.handle(event));
    }
}
