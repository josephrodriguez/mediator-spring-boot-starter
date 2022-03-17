package io.github.josephrodriguez.interfaces;

/**
 * @param <<T> The type of Event handled by this class
 */
public interface EventHandler<T extends Event> {

    /**
     * @param event The event instance
     */
    void handle(T event);
}
