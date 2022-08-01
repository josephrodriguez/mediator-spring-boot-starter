import handlers.EchoRequestHandler;
import io.github.josephrodriguez.config.SpringBootMediatorAutoConfiguration;
import io.github.josephrodriguez.exceptions.UnsupportedRequestException;
import io.github.josephrodriguez.Mediator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import requests.EchoRequest;
import responses.EchoResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RequestHandlerTest {

    @Test
    void shouldThrowUnsupportedRequest() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .run(context -> {
                    assertThrows(UnsupportedRequestException.class, () -> context.getBean(Mediator.class).send(new EchoRequest("Hi")));
                });
    }

    @Test
    void shouldHandleEchoRequest() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run(context -> {
                    assertNotNull(context.getBean(Mediator.class).send(new EchoRequest("Hi")));
                });
    }

    @Test
    void shouldMatchEchoResponseClass() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run(context -> {
                    EchoResponse response = context.getBean(Mediator.class).send(new EchoRequest("Hi"));
                    assertEquals(response.getClass(), EchoResponse.class);
                });
    }

    @Test
    void shouldMatchEchoResponseMessage() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run(context -> {
                    EchoResponse response = context.getBean(Mediator.class).send(new EchoRequest("Hi"));
                    assertEquals("Hi", response.getMessage());
                });
    }

    @Test
    void shouldMatchRandomEchoResponseMessage() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run(context -> {
                    String message = UUID.randomUUID().toString();
                    EchoResponse response = context.getBean(Mediator.class).send(new EchoRequest(message));
                    assertEquals(response.getMessage(), message);
                });
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run( context -> {
                    Mediator mediator = context.getBean(Mediator.class);
                    assertThrows(IllegalArgumentException.class, () -> mediator.send(null));
                });
    }

    @Test
    void shouldHandleAsyncRequest() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run(context -> {
                   Mediator mediator = context.getBean(Mediator.class);
                   EchoRequest request = new EchoRequest(UUID.randomUUID().toString());
                   CompletableFuture<EchoResponse> future = mediator.sendAsync(request);
                   EchoResponse response = future.join();

                   assertEquals(EchoResponse.class, response.getClass());
                   assertEquals(request.getMessage(), response.getMessage());
                });
    }

    @Test
    void shouldThrowCompletionException() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .run( context -> {
                    Mediator mediator = context.getBean(Mediator.class);
                    EchoRequest request = new EchoRequest("Hi Mediator");
                    CompletableFuture<EchoResponse> response = mediator.sendAsync(request);

                    assertThrows(CompletionException.class, () -> response.join());
                });
    }

    @Test
    void shouldHandleMultipleRequestsAsync() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(EchoRequestHandler.class)
                .run( context -> {
                    Mediator mediator = context.getBean(Mediator.class);

                    CompletableFuture[] futures =
                            IntStream.range(0, 10)
                                    .mapToObj(i -> new EchoRequest(((Integer) i).toString()))
                                    .parallel()
                                    .map(mediator::sendAsync)
                                    .toArray(CompletableFuture[]::new);

                    CompletableFuture<Void> aggregateFuture = CompletableFuture.allOf(futures);

                    assertNull(aggregateFuture.join());
                    assertTrue(aggregateFuture.isDone());
                });
    }
}
