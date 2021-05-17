package com.yapp.ios1.service;

import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * 임시 토큰 테스트
 *
 * created by ayoung 2021/05/15
 */
public class JwtServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void decode_테스트() throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjIyODU5ODk0fQ.nLl9b-mGhDdL7p3jnJzVE6Se07nzFkPnHvKF8mhA_6Q");
        ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();

        Date currentTime = new Date(System.currentTimeMillis());
        if (!currentTime.before(payload.getExpirationTime())) {
            logger.info("유효기간 지남");
        }

        logger.info("" + payload.getSubject());
    }
}
