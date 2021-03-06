package events;

import io.github.josephrodriguez.interfaces.Event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateTimeEvent implements Event {

    private final LocalDateTime datetime;

    public DateTimeEvent() {
        this.datetime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
}
