package com.aureum.springboot.interfaces;

/**
 * @param <TEvent> The type of Event handled by this class
 */
public interface EventHandler<TEvent extends Event> {

    /**
     * @param event The event instance
     */
    void handle(TEvent event);
}
