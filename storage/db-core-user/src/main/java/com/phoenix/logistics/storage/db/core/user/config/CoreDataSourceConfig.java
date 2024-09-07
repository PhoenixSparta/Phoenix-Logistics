package com.phoenix.logistics.storage.db.core.user.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
class CoreDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "storage.datasource.core")
    public HikariConfig coreHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Profile("!local")
    public HikariDataSource coreDataSource(@Qualifier("coreHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    @Profile("local")
    public HikariDataSource localCoreDataSource(@Qualifier("coreHikariConfig") HikariConfig config)
            throws SQLException {
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093").start();
        return new HikariDataSource(config);
    }

}
