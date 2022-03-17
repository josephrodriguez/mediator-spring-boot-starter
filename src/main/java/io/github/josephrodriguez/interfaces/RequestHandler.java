package io.github.josephrodriguez.interfaces;

/**
 * Handle a incoming request and returns a response
 * @param <T> The type of request that handle
 * @param <V> The type of the response that return
 */
public interface RequestHandler<T extends Request<V>, V> {

    /**
     * @param request The request to be handled
     * @return The result of process the request
     */
    V handle(T request);
}
