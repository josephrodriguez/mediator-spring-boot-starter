import io.github.josephrodriguez.Mediator;
import io.github.josephrodriguez.config.SpringBootMediatorAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class AutoConfigurationTest {

    @Test
    void executeApplicationContext() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                        .run(context -> {
                            assertNotNull(context);
                        });
    }

    @Test
    void executeMediatorService() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .run(context -> {
                    assertNotNull(context.getBean(Mediator.class));
                });
    }
}
