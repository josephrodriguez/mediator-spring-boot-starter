package com.aureum.springboot.interfaces;

import com.aureum.springboot.exceptions.UnsupportedRequestException;

/**
 * Route
 */
public interface Sender {

    /** Route the incoming request to the registered RequestHandler
     * @param request The type of the class that implements Request interface
     * @param <Response> The type of the result
     * @return The result of handle the Request object
     * @throws UnsupportedRequestException When there is no registered bean instance of type {@code RequestHandler<Request, Response>}
     */
    <Response> Response send(Request<Response> request) throws UnsupportedRequestException;
}
