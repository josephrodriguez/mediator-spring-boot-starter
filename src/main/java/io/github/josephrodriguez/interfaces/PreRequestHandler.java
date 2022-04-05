package io.github.josephrodriguez.interfaces;

/**
 * @param <T>
 * @param <V>
 */
public interface PreRequestHandler<T extends Request<V>, V> {

    /**
     * @param request
     */
    void process(T request);
}
