package com.yapp.ios1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * created by ayoung 2021/05/30
 */
@Configuration
@ConfigurationProperties(prefix = "buok.email")
@Getter
@Setter
public class BuokEmailProperties {
    private String name;
    private String link;
    private String logoUrl;
    private Long validTime;
}
