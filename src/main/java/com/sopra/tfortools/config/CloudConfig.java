package com.sopra.tfortools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Configuration spécifique à l'environnement cloud
 *
 * @author jntakpe
 */
@Configuration
@Profile("cloud")
public class CloudConfig {

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource("java:comp/env/jdbc/tftdb");
    }
}
