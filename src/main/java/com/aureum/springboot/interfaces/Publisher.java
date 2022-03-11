package com.aureum.springboot.interfaces;

import com.aureum.springboot.exceptions.UnsupportedEventException;

/**
 *
 */
public interface Publisher {

    /**
     * @param event
     * @param <TEvent>
     */
    <TEvent extends Event> void publish(TEvent event) throws UnsupportedEventException;
}
