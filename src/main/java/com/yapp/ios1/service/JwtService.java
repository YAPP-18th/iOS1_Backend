package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.dto.JwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
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

    public String createToken(JwtPayload payload) throws JsonProcessingException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(objectMapper.writeValueAsString(payload))
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(expiresAt())
                .compact();
    }

    public JwtPayload getPayload(String token) throws JsonProcessingException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims == null || claims.getSubject() == null) {
            throw new IllegalArgumentException("토큰이 잘못되었습니다.");
        }

        return objectMapper.readValue(claims.getSubject(), JwtPayload.class);
    }

    // 만료 기간을 yml 파일에 따로 빼는게 나으려남..
    private Date expiresAt() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //한달 24*31
        cal.add(Calendar.HOUR, 744);
        return cal.getTime();
    }
}

