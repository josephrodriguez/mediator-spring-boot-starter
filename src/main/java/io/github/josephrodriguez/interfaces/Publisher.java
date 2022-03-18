package io.github.josephrodriguez.interfaces;

import io.github.josephrodriguez.exceptions.UnsupportedEventException;

import java.util.concurrent.CompletableFuture;

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

    /**
     * @param event
     * @param <T>
     */
    <T extends Event> CompletableFuture<Void> publishAsync(T event);
}
