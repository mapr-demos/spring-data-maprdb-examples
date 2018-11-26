package com.mapr.springframework.data.maprdb.examples.crud.config;

import com.mapr.springframework.data.maprdb.config.AbstractMapRConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatabaseConfig extends AbstractMapRConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected String getHost() {
        return "";
    }

    @Override
    protected String getUsername() {
        return "";
    }

    @Override
    protected String getPassword() {
        return "";
    }

}
