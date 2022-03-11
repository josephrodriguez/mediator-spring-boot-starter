package com.aureum.springboot.config;

import com.aureum.springboot.service.Mediator;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class SpringBootMediatorAutoConfiguration {

    /**
     * @param factory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Mediator mediator(ListableBeanFactory factory) {
        return new Mediator(factory);
    }
}
