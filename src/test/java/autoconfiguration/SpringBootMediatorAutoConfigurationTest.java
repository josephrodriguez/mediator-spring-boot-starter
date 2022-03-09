package autoconfiguration;

import com.aureum.springboot.config.SpringBootMediatorAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringBootMediatorAutoConfiguration.class })
public class SpringBootMediatorAutoConfigurationTest {

    @Test
    void testApplicationContext() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                        .run(context -> {
                            Assert.notNull(context, "ApplicationContextRunner should not be null.");
                        });
    }
}
