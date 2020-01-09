package com.simple.productInfo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "mail")
@Data
public class MailProperties {

    private String from;

    private String to;
    
}