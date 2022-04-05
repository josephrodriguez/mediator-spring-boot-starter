package handlers;

import events.RandomUUIDEvent;
import io.github.josephrodriguez.interfaces.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class RandomUUIDEventHandler implements EventHandler<RandomUUIDEvent> {

    @Override
    public void handle(RandomUUIDEvent event) {
        System.out.println(String.format("%s: Handle %s, UUID:%s.", getClass(), event.getClass(), event.getUuid()));
    }
}
