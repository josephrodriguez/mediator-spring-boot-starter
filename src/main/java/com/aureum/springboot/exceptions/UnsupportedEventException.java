package com.aureum.springboot.exceptions;

import com.aureum.springboot.interfaces.Event;

/**
 *
 */
public class UnsupportedEventException extends Exception {

    /**
     * @param message
     */
    public UnsupportedEventException(String message) {
        super(message);
    }

    /**
     * @param clazz
     */
    public UnsupportedEventException(Class<? extends Event> clazz) {
        super(String.format("Unsupported event %s", clazz));
    }
}
