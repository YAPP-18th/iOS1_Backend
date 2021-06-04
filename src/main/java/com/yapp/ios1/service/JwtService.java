package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.dto.jwt.TokenResponseDto;
import com.yapp.ios1.error.exception.jwt.JwtParseException;
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

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtIssueService jwtIssueService;

    public JwtPayload getPayload(String token) throws JsonProcessingException, SignatureException, ExpiredJwtException, MalformedJwtException, UnsupportedJwtException {
        Claims claims = getAllClaimsFromToken(token);
        return objectMapper.readValue(claims.getSubject(), JwtPayload.class);
    }

    public String getSubject(String identityToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(identityToken);
            ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();
            return payload.getSubject();
        } catch (ParseException e) {
            throw new JwtParseException();
        }
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

    private boolean isTokenValid(String token) {
        return !getExpirationDateFromToken(token).before(new Date());
    }

    public TokenResponseDto createTokenResponse(Long userId) {
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

    public TokenResponseDto reissueToken(String refreshToken) {
        if (!isTokenValid(refreshToken)) { // 만료된 토큰인 경우
            throw new IllegalArgumentException(ResponseMessage.EXPIRED_TOKEN);
        }
        // 토큰에서 userId를 꺼낸다.
        Long userId = Long.parseLong(getSubject(refreshToken));
        String token = jwtIssueService.getRefreshTokenByUserId(userId);
        if (!token.equals(refreshToken)) {
            throw new IllegalArgumentException(ResponseMessage.EXPIRED_TOKEN);
        }
        return createTokenResponse(userId);
    }
}
