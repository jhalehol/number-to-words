package com.jholguin.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConverterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConverterApplication.class, args);
    }
}
