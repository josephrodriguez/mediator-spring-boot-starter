import io.github.josephrodriguez.config.SpringBootMediatorAutoConfiguration;
import io.github.josephrodriguez.service.Mediator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@ExtendWith(SpringExtension.class)
public class AutoConfigurationTest {

    @Test
    void executeApplicationContext() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                        .run(context -> {
                            Assert.notNull(context, "ApplicationContext should not be null.");
                        });
    }

    @Test
    void executeMediatorService() {

        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .run(context -> {
                    Assert.notNull(context.getBean(Mediator.class), "Mediator bean can´t be null.");
                });
    }
}
