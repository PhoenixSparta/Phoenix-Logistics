package com.phoenix.logistics.storage.db.core.order.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CoreDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "storage.datasource.core")
    public HikariConfig coreHikariConfig() {
        return new HikariConfig();
    }

    // todo 로컬 외 설정 추가시 작성
    // @Bean
    // @Profile("!local")
    // public HikariDataSource coreDataSource(@Qualifier("coreHikariConfig") HikariConfig
    // config) {
    // return new HikariDataSource(config);
    // }

    @Bean
    @Profile("local")
    public HikariDataSource localCoreDataSource(@Qualifier("coreHikariConfig") HikariConfig config)
            throws SQLException {
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9097").start();
        return new HikariDataSource(config);
    }

}
