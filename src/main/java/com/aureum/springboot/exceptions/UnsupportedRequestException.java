package com.aureum.springboot.exceptions;

import com.aureum.springboot.interfaces.Request;

/**
 *
 */
public class UnsupportedRequestException extends Exception {

    /**
     * @param message
     */
    public UnsupportedRequestException(String message) {
        super(message);
    }

    public UnsupportedRequestException(Class<? extends Request> clazz) {
        super(String.format("Unsupported request %s", clazz));
    }
}
