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
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class JwtProperties {

    // TODO AccessToken, RefreshToken SecretKey 분리하기
    private String secretKey;
    private AccessToken accessToken;
    private RefreshToken refreshToken;

    @Getter @Setter
    public static class AccessToken {
        private Long validTime;
    }

    @Getter @Setter
    public static class RefreshToken {
        private Long validTime;
    }
}
