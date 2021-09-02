package com.twillio.sendgrid.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configuration {
    @Value("${sendgrid.api.key}")
    private String key;

    @Bean
    public SendGrid getSendGridId(){
        return new SendGrid(key);
    }
}
