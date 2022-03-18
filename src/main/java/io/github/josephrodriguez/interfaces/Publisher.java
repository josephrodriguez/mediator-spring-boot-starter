package io.github.josephrodriguez.interfaces;

import io.github.josephrodriguez.exceptions.UnsupportedEventException;

import java.util.concurrent.CompletableFuture;

/**
 * Class to broadcast an Event for every registered EventHandler instance
 */
public interface Publisher {

    /**
     * @param event The {@code Event} instance to be handled in synchronous way for the corresponding {@code EventHandler}
     * @param <T> The type of {@code Event}
     * @throws UnsupportedEventException When there is not registered beans of type {@code EventHandler<TEvent>}
     */
    <T extends Event> void publish(T event) throws UnsupportedEventException;

    /**
     * @param event The {@code Event} instance to be handled in asynchronous way for the corresponding {@code EventHandler}
     * @param <T> The type of {@code Event}
     * @return A {@code CompletableFuture} object instance for the asynchronous operation
     */
    <T extends Event> CompletableFuture<Void> publishAsync(T event);
}
