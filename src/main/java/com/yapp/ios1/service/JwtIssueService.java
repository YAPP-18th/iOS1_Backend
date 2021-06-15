package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.config.CacheKey;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.error.exception.common.JsonWriteException;
import com.yapp.ios1.mapper.TokenMapper;
import com.yapp.ios1.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final TokenMapper tokenMapper;

    private String createToken(JwtPayload payload, Long expireTime) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(jwtProperties.getSecretKey());
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
        return createToken(payload, jwtProperties.getAccessToken().getValidTime());
    }

    @Transactional
    @CachePut(value = CacheKey.TOKEN, key = "#payload.id")
    public String createRefreshToken(JwtPayload payload) {
        String refreshToken = createToken(payload, jwtProperties.getRefreshToken().getValidTime());
        tokenMapper.updateToken(refreshToken, payload.getId());
        return refreshToken;
    }

    @Cacheable(value = CacheKey.TOKEN, key = "#userId")
    public String getRefreshTokenByUserId(Long userId) {
        return tokenMapper.getTokenByUserId(userId);
    }
}
