package com.aureum.springboot.processor;

import com.aureum.springboot.contracts.Event;
import com.aureum.springboot.contracts.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventHandlerProcessor<TEvent extends Event> {

    private List<EventHandler<TEvent>> handlers;

    public EventHandlerProcessor() {
        handlers = Collections.synchronizedList(new ArrayList<>());
    }

    public void handle(TEvent event) {

        if(event == null)
            throw new IllegalArgumentException("Undefined event instance.");

        handlers.forEach(handler -> handler.handle(event));
    }

    public void register(EventHandler<TEvent> handler) {
        handlers.add(handler);
    }

    public void unregister(EventHandler<TEvent> handler) {
        handlers.remove(handler);
    }

}
