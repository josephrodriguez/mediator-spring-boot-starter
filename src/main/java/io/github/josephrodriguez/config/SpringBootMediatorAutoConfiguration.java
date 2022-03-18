package io.github.josephrodriguez.config;

import io.github.josephrodriguez.Mediator;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration that expose the conditional @Bean for Mediator service
 */
@Configuration
public class SpringBootMediatorAutoConfiguration {

    /**
     * @param factory Instance of bean factory to list the bean instances
     * @return Return the Mediator service instance
     */
    @Bean
    @ConditionalOnMissingBean
    public Mediator mediator(ListableBeanFactory factory) {
        return new Mediator(factory);
    }
}
