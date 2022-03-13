package com.aureum.springboot.interfaces;

import com.aureum.springboot.exceptions.UnsupportedRequestException;

public interface Sender {

    /**
     * @param request
     * @param <Response>
     * @return
     * @throws UnsupportedRequestException
     */
    <Response> Response send(Request<Response> request) throws UnsupportedRequestException;
}
