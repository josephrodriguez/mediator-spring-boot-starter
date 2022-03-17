package io.github.josephrodriguez.interfaces;

import io.github.josephrodriguez.exceptions.UnsupportedEventException;

/**
 * Class to broadcast an Event for every registered EventHandler instance
 */
public interface Publisher {

    /**
     * @param event The event to handle
     * @param <T> The type of the Event
     * @throws UnsupportedEventException When there is not registered beans of type {@code EventHandler<TEvent>}
     */
    <T extends Event> void publish(T event) throws UnsupportedEventException;
}
