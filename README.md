# Spring Boot Library for Mediator pattern

![GitHub release (latest by date)](https://img.shields.io/github/v/release/josephrodriguez/mediator-spring-boot-starter)
![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/josephrodriguez/mediator-spring-boot-starter/Maven%20Package/main)
[![codecov](https://codecov.io/gh/josephrodriguez/mediator-spring-boot-starter/branch/main/graph/badge.svg?token=FVTMMF2BB1)](https://codecov.io/gh/josephrodriguez/mediator-spring-boot-starter)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=josephrodriguez_mediator-spring-boot-starter&metric=alert_status)](https://sonarcloud.io/dashboard?id=josephrodriguez_mediator-spring-boot-starter)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=josephrodriguez_mediator-spring-boot-starter&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=josephrodriguez_mediator-spring-boot-starter)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=josephrodriguez_mediator-spring-boot-starter&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=josephrodriguez_mediator-spring-boot-starter)

## Main Purpose

This Spring Boot library provide an implementation for the behavioral pattern Mediator.

## Getting Started

The library is a Maven Artifact published on GitHub Package Registry. Check the package versions list [here](https://github.com/josephrodriguez/mediator-spring-boot-starter/packages/). 

#### Dependency

The library version dependency should be declared on pom.xml file.

```xml
<dependency>
  <groupId>com.aureum.springboot</groupId>
  <artifactId>mediator-spring-boot-starter</artifactId>
  <version>0.0.1</version>
</dependency>
```

#### Install

Install the package with Maven running the command line.

```shell
$ mvn install
```

## Usage

Auto-configuration feature is supported by the library using the annotation `@EnableMediator` annotation in the Spring Boot Application.

```java
import com.aureum.springboot.annotations.EnableMediator;

@EnableMediator
@SpringBootApplication
public class SpringBootStarterKitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterKitApplication.class, args);
    }
}
```

Declare the classes that implements `EventHandler` or `RequestHandler` interfaces to handle the events or request instances. 

```java
import com.aureum.springboot.interfaces.RequestHandler;

@Service
public class EchoRequestHandler implements RequestHandler<EchoRequest, EchoResponse> {

    @Override
    public EchoResponse handle(EchoRequest request) {
        return new EchoResponse(request.getMessage());
    }
}
```

Use the Mediator service with dependency injection.

```java
import com.aureum.springboot.service.Mediator;

@RestController
public class EchoController {

    private Mediator mediator;
    
    public EchoController(Mediator mediator) {
        this.mediator = mediator;
    }

    @RequestMapping("/echo")
    public ResponseEntity<EchoResponse> echo() {
        
        EchoRequest request = new EchoRequest("echo");
        EchoResponse response = mediator.send(request);

        return ResponseEntity
                .ok()
                .body(response);
    }
}
```