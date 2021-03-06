package com.sopra.tfortools.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Configuration de Spring security
 *
 * @author jntakpe
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Déclaration de l'objet responsable de l'encode des mots de passe
     *
     * @return encodeur de mot de passe
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bower_components/**", "/img/**", "/css/**", "/js/**", "/fonts/**", "/manage/**");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/register/**").permitAll().anyRequest().authenticated();
        http
                .formLogin().loginPage("/login").loginProcessingUrl("/authenticate").defaultSuccessUrl("/").permitAll().and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID").permitAll().and()
                .rememberMe().and().csrf().disable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
