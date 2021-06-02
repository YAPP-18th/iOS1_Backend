package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.dto.jwt.TokenResponseDto;
import com.yapp.ios1.utils.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;


/**
 * created by jg 2021/04/11
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JwtService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisUtil redisUtil;

    private final JwtIssueService jwtIssueService;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public JwtPayload getPayload(String token) throws JsonProcessingException, SignatureException, ExpiredJwtException, MalformedJwtException, UnsupportedJwtException {
        Claims claims = getAllClaimsFromToken(token);
        return objectMapper.readValue(claims.getSubject(), JwtPayload.class);
    }

    public String getSubject(String identityToken) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(identityToken);
        ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();

        return payload.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenResponseDto createTokenResponse(Long userId) throws JsonProcessingException {
        JwtPayload jwtPayload = new JwtPayload(userId);
        String accessToken = jwtIssueService.createAccessToken(jwtPayload);
        String refreshToken = jwtIssueService.createRefreshToken(jwtPayload);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpiredAt(getExpirationDateFromToken(accessToken))
                .refreshExpiredAt(getExpirationDateFromToken(refreshToken))
                .build();
    }

    public TokenResponseDto reissueToken(String refreshToken) throws JsonProcessingException {
        String data = redisUtil.getData(refreshToken);
        if (data == null) {
            throw new IllegalArgumentException(ResponseMessage.EXPIRED_TOKEN);
        }
        redisUtil.deleteData(refreshToken);
        return createTokenResponse(Long.parseLong(data));
    }

}

