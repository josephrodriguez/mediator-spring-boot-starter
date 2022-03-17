package io.github.josephrodriguez.exceptions;

import io.github.josephrodriguez.interfaces.Event;

/**
 *
 */
public class UnsupportedEventException extends Exception {

    /**
     * @param message The message of the exception
     */
    public UnsupportedEventException(String message) {
        super(message);
    }

    /**
     * @param clazz The event class not supported by the Mediator service
     */
    public UnsupportedEventException(Class<? extends Event> clazz) {
        super(String.format("Unsupported event %s", clazz));
    }
}
