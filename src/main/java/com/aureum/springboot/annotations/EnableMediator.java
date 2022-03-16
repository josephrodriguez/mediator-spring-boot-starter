package com.aureum.springboot.annotations;

import com.aureum.springboot.config.SpringBootMediatorAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Import the SpringBootMediatorAutoConfiguration class that contains the Mediator service
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpringBootMediatorAutoConfiguration.class)
public @interface EnableMediator {
}
