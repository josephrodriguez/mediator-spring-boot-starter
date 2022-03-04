package com.aureum.springboot.service;

import com.aureum.springboot.contracts.Event;
import com.aureum.springboot.contracts.Publisher;

public class Mediator implements Publisher {

    @Override
    public <TEvent extends Event> void publish(TEvent event) {

    }
}
