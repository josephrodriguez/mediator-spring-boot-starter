package com.aureum.springboot.interfaces;

/**
 * @param <TEvent>
 */
public interface EventHandler<TEvent extends Event> {

    /**
     * @param event
     */
    void handle(TEvent event);
}
