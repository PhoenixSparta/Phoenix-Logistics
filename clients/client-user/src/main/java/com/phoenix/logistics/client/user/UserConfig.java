package com.phoenix.logistics.client.user;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.phoenix.logistics.client.user")
@Configuration
public class UserConfig {

}
