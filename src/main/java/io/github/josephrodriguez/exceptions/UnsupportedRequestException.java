package com.aureum.springboot.exceptions;

import com.aureum.springboot.interfaces.Request;

/**
 *
 */
public class UnsupportedRequestException extends Exception {

    /**
     * @param message The exception message
     */
    public UnsupportedRequestException(String message) {
        super(message);
    }

    /**
     * @param clazz The class of the request not handled by the Mediator service
     */
    public UnsupportedRequestException(Class<? extends Request> clazz) {
        super(String.format("Unsupported request %s", clazz));
    }
}
