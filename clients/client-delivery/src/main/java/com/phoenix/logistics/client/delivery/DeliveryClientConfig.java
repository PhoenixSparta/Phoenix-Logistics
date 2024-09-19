package com.phoenix.logistics.client.delivery;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.phoenix.logistics.client.delivery")
@Configuration
public class DeliveryClientConfig {

}
