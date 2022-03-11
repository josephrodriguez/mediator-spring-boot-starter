package events;

import com.aureum.springboot.interfaces.Event;

import java.util.UUID;

public class RandomUUIDEvent implements Event {

    private final UUID uuid;

    public RandomUUIDEvent() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
