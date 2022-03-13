package com.aureum.springboot.interfaces;

public interface Sender {

    /**
     * @param request
     * @param <Response>
     * @return
     */
    <Response> Response send(Request<Response> request);
}
