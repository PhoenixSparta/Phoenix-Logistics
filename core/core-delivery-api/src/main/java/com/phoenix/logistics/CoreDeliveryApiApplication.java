package com.phoenix.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CoreDeliveryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreDeliveryApiApplication.class, args);
    }

}
