package io.github.josephrodriguez.interfaces;

import io.github.josephrodriguez.exceptions.UnsupportedRequestException;

import java.util.concurrent.CompletableFuture;

/**
 * Route
 */
public interface Sender {

    /** Route the incoming request to the registered RequestHandler
     * @param request The type of the class that implements Request interface
     * @param <T> The type of the result
     * @return The result of handle the Request object
     * @throws UnsupportedRequestException When there is no registered bean instance of type {@code RequestHandler<Request, Response>}
     */
    <T> T send(Request<T> request) throws UnsupportedRequestException;

    /**
     * @param request
     * @param <T>
     * @return
     */
    <T> CompletableFuture<T> sendAsync(Request<T> request);
}
