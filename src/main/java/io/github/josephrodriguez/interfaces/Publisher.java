package com.aureum.springboot.interfaces;

import com.aureum.springboot.exceptions.UnsupportedEventException;

/**
 * Class to broadcast an Event for every registered EventHandler instance
 */
public interface Publisher {

    /**
     * @param event The event to handle
     * @param <TEvent> The type of the Event
     * @throws UnsupportedEventException When there is not registered beans of type {@code EventHandler<TEvent>}
     */
    <TEvent extends Event> void publish(TEvent event) throws UnsupportedEventException;
}
