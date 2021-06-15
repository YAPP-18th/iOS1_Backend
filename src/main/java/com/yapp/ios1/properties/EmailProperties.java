package com.yapp.ios1.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * created by ayoung 2021/05/30
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "buok.email")
@Configuration
public class EmailProperties {
    private String name;
    private String link;
    private Long validTime;
}
