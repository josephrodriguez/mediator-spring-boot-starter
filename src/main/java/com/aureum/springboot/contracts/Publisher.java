package com.aureum.springboot.contracts;

public interface Publisher {

    <TEvent extends Event> void publish(TEvent event);
}
