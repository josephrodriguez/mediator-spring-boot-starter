package io.github.josephrodriguez.executors;

import io.github.josephrodriguez.interfaces.*;

import java.util.Optional;

public class RequestHandlerAggregateExecutor<T extends Request<V>, V> {

    private final Optional<PreRequestProcessor<T, V>> preRequestProcessor;

    private final RequestHandler<T, V> requestHandler;

    private final Optional<PostRequestProcessor<T, V>> postRequestProcessor;

    public RequestHandlerAggregateExecutor(Optional<PostRequestProcessor<T, V>> postRequestProcessor) {
        this.postRequestProcessor = postRequestProcessor;
        preRequestProcessor = null;
        requestHandler = null;
    }

    public V handle(T request) {
        preRequestProcessor.ifPresent(processor -> processor.process(request));

        try {
            V response = requestHandler.handle(request);
            postRequestProcessor.ifPresent(processor -> processor.process(request, response));
            return response;
        }
        catch (Exception exception) {
            throw exception;
        }
    }
}
