package com.example.connectdb.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public Session getSession(){
        return new Session();
    }
}
