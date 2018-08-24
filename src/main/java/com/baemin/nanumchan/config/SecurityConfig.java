package com.baemin.nanumchan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable();
    }

    @Profile("production")
    public static class HttpsSecurityConfig extends SecurityConfig {
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            super.configure(httpSecurity);
            httpSecurity.requiresChannel().anyRequest().requiresSecure();
        }
    }

}