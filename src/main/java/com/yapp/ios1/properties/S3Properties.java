package com.yapp.ios1.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * created by jg 2021/06/04
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws.s3")
@Configuration
public class S3Properties {
    private String bucket;
    private String dir;
}
