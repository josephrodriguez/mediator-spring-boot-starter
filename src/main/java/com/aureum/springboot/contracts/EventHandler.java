package com.aureum.springboot.contracts;

public interface EventHandler<TEvent extends Event> {

    void handle(TEvent event);
}
