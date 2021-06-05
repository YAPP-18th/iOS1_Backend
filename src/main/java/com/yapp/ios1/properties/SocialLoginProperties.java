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
@ConfigurationProperties(prefix = "social")
@Configuration
public class SocialLoginProperties {

    private String key;
    private Host host;

    @Getter @Setter
    public static class Host {
        private String google;
        private String kakao;
    }
}
