package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.config.CacheKey;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.error.exception.common.JsonWriteException;
import com.yapp.ios1.mapper.TokenMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * created by ayoung 2021/06/03
 */
@RequiredArgsConstructor
@Service
public class JwtIssueService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final TokenMapper tokenMapper;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.accessToken.validTime}")
    private Long ACCESS_VALID_TIME;

    @Value("${jwt.refreshToken.validTime}")
    private Long REFRESH_VALID_TIME;

    private String createToken(JwtPayload payload, Long expireTime) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(writeJsonAsString(payload))
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    private String writeJsonAsString(JwtPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload.getId());
        } catch (JsonProcessingException e) {
            throw new JsonWriteException();
        }
    }

    public String createAccessToken(JwtPayload payload) {
        return createToken(payload, ACCESS_VALID_TIME);
    }

    @CachePut(value = CacheKey.TOKEN, key = "#payload.id")
    public String createRefreshToken(JwtPayload payload) {
        String refreshToken = createToken(payload, REFRESH_VALID_TIME);
        tokenMapper.updateToken(refreshToken, payload.getId());
        return refreshToken;
    }

    @Cacheable(value = CacheKey.TOKEN, key = "#userId")
    public String getRefreshTokenByUserId(Long userId) {
        return tokenMapper.getTokenByUserId(userId);
    }
}
