import events.DateTimeEvent;
import events.RandomUUIDEvent;
import handlers.DateTimeEventHandler1;
import handlers.RandomUUIDEventHandler;
import io.github.josephrodriguez.config.SpringBootMediatorAutoConfiguration;
import io.github.josephrodriguez.exceptions.UnsupportedEventException;
import io.github.josephrodriguez.service.Mediator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class EventHandlerTest {

    @Test
    void executeUnsupportedEventWithBean() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .run( context -> {
                    assertThrows(UnsupportedEventException.class,
                            () -> context.getBean(Mediator.class).publish(new RandomUUIDEvent()));
                });
    }

    @Test
    void executeUnsupportedEventWithNoBean() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .run( context -> {
                    assertThrows(UnsupportedEventException.class,
                            () -> context.getBean(Mediator.class).publish(new RandomUUIDEvent()));
                });
    }

    @Test
    void executeSingleHandler() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .run( context -> {
                    context.getBean(Mediator.class).publish(new DateTimeEvent());
                });
    }

    @Test
    void executeMultipleHandler() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .withBean(RandomUUIDEventHandler.class)
                .run( context -> {
                    context.getBean(Mediator.class).publish(new RandomUUIDEvent());
                });
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .run( context -> {
                    Mediator mediator = context.getBean(Mediator.class);
                   assertThrows(IllegalArgumentException.class, () -> mediator.publish(null));
                });
    }
}
