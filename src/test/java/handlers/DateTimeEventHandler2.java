package handlers;

import events.DateTimeEvent;
import com.aureum.springboot.interfaces.EventHandler;

public class DateTimeEventHandler2 implements EventHandler<DateTimeEvent> {

    @Override
    public void handle(DateTimeEvent event) {
        System.out.println(String.format("%s: Handle %s, datetime:%s.", getClass(), event.getClass(), event.getDatetime()));
    }
}
