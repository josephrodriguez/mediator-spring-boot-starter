package com.aureum.springboot.interfaces;

/**
 * Handle a incoming request and returns a response
 * @param <TRequest> The type of request that handle
 * @param <TResponse> The type of the response that return
 */
public interface RequestHandler<TRequest extends Request<TResponse>, TResponse> {

    /**
     * @param request The request to be handled
     * @return The result of process the request
     */
    TResponse handle(TRequest request);
}
