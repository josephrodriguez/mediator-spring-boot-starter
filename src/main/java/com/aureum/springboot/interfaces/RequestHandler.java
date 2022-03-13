package com.aureum.springboot.interfaces;

import javax.xml.ws.Response;

/**
 * @param <TResponse>
 */
public interface RequestHandler<TRequest extends Request<TResponse>, TResponse> {

    TResponse handle(TRequest request);
}
