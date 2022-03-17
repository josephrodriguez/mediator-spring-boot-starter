import handlers.DateTimeEventHandler1;
import io.github.josephrodriguez.config.SpringBootMediatorAutoConfiguration;
import io.github.josephrodriguez.exceptions.UnsupportedRequestException;
import io.github.josephrodriguez.service.Mediator;
import handlers.EchoRequestHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import requests.EchoRequest;
import responses.EchoResponse;

import java.util.UUID;

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
                    assertEquals(response.getMessage(), "Hi");
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
                    assertThrows(IllegalArgumentException.class, () -> context.getBean(Mediator.class).send(null));
                });
    }
}
