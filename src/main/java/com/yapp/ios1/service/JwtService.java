package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yapp.ios1.dto.JwtPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;

/**
 * created by jg 2021/04/11
 */
@Slf4j
@Service
public class JwtService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.validTime}")
    private long VALID_TIME;

    public String createToken(JwtPayload payload) throws JsonProcessingException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(objectMapper.writeValueAsString(payload.getId()))
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + VALID_TIME))
                .compact();
    }

    public JwtPayload getPayload(String token) throws JsonProcessingException, SignatureException, ExpiredJwtException, MalformedJwtException, UnsupportedJwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return objectMapper.readValue(claims.getSubject(), JwtPayload.class);
    }

    public String getSubject(String identityToken) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(identityToken);
        ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();

        return payload.getSubject();
    }
}

