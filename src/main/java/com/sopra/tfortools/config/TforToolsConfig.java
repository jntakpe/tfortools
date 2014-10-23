package com.sopra.tfortools.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe de configuration globale de l'application
 *
 * @author jntakpe
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.sopra.tfortools")
@EntityScan("com.sopra.tfortools.domain")
@EnableJpaRepositories(basePackages = "com.sopra.tfortools.repository")
public class TforToolsConfig extends SpringBootServletInitializer {

    private static final String MESSAGE_PREFIX = "classpath:/messages/";

    /**
     * {@inheritDoc}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this.getClass());
    }

    /**
     * Configuration des bundles de messages
     *
     * @return le bundle de messages
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGE_PREFIX + "gui-labels", MESSAGE_PREFIX + "gui-messages");
        messageSource.setCacheSeconds(10);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
