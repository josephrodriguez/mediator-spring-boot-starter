package com.aureum.springboot.annotations;

import com.aureum.springboot.config.SpringBootMediatorAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpringBootMediatorAutoConfiguration.class)
public @interface EnableMediator {
}
