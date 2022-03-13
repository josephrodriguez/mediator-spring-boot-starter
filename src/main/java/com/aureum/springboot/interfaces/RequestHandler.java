package com.aureum.springboot.interfaces;

/**
 * @param <TResponse>
 */
public interface RequestHandler<TRequest extends Request<TResponse>, TResponse> {

    TResponse handle(TRequest request);
}
