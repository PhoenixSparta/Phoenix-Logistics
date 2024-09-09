package com.phoenix.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CoreUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreUserApplication.class, args);
    }

}
