package handlers;

import io.github.josephrodriguez.interfaces.RequestHandler;
import requests.EchoRequest;
import responses.EchoResponse;

public class EchoRequestHandler implements RequestHandler<EchoRequest, EchoResponse> {

    @Override
    public EchoResponse handle(EchoRequest request) {
        return new EchoResponse(request.getMessage());
    }
}
