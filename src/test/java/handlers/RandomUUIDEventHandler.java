package handlers;

import events.RandomUUIDEvent;
import com.aureum.springboot.interfaces.EventHandler;

public class RandomUUIDEventHandler implements EventHandler<RandomUUIDEvent> {

    @Override
    public void handle(RandomUUIDEvent event) {
        System.out.println(String.format("%s: Handle %s, UUID:%s.", getClass(), event.getClass(), event.getUuid()));
    }
}
