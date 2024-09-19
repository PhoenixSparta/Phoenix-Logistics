package com.phoenix.logistics.storage.db.core.delivery.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.phoenix.logistics.storage.db.core")
@EnableJpaRepositories(basePackages = "com.phoenix.logistics.storage.db.core")
class CoreJpaConfig {

}
