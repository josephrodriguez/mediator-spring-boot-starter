package io.github.josephrodriguez.interfaces;

/**
 * @param <T>
 * @param <V>
 */
public interface PostRequestHandler<T extends Request<V>,V> {

    /**
     * @param request
     */
    void process(T request,V response);
}
