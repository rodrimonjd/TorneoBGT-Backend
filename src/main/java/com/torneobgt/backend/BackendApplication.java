package com.torneobgt.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BackendApplication.class, args);
        var env = ctx.getEnvironment();
        System.out.println(">>>>>> MONGO URI: " + env.getProperty("spring.data.mongodb.uri"));
    }
}