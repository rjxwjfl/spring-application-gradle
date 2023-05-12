package com.portfolio.springapplication.security.jwt;

import com.portfolio.springapplication.config.RedisConfig;
import com.portfolio.springapplication.dto.RtkRespDto;
import com.portfolio.springapplication.dto.SignInRespDto;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.exception.CustomException;
import com.portfolio.springapplication.security.exception.ErrorMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public SignInRespDto generateToken(UserPrincipalDetail userPrincipalDetail) {

        Date tokenExpireDate = new Date(new Date().getTime() + (1000 * 60 * 60));

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userPrincipalDetail.user().getId());
        claims.put("username", userPrincipalDetail.getUsername());
        claims.put("roles", userPrincipalDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        String accessToken = Jwts.builder()
                .setSubject(userPrincipalDetail.getUsername())
                .addClaims(claims)
                .setExpiration(tokenExpireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = generateRefreshToken(userPrincipalDetail);
        long rtkLive = 1000 * 60 * 60 * 24 * 7;

        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();
        String checkRtk = (String) redisTemplate.opsForValue().get(userPrincipalDetail.getUsername());
        System.out.println(checkRtk);

        if (checkRtk != null){
            throw new CustomException("Duplicate sign-in request.", ErrorMap.builder().put("signin", "중복된 로그인 요청입니다.").build());
        }

        redisTemplate.opsForValue().set(userPrincipalDetail.getUsername(), refreshToken, Duration.ofMillis(rtkLive));

        return SignInRespDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateRefreshToken(UserPrincipalDetail userPrincipalDetail) {
        return UUID.randomUUID().toString();
    }

    public RtkRespDto reissueToken(UserPrincipalDetail userPrincipalDetail){
        Date tokenExpireDate = new Date(new Date().getTime() + (1000 * 60 * 60));

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userPrincipalDetail.user().getId());
        claims.put("username", userPrincipalDetail.getUsername());
        claims.put("roles", userPrincipalDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        String accessToken = Jwts.builder()
                .setSubject(userPrincipalDetail.getUsername())
                .addClaims(claims)
                .setExpiration(tokenExpireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return RtkRespDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public boolean validateToken(String token) {
        System.out.println(token);
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgument JWT Token", e);
        } catch (Exception e) {
            log.info("JWT Token Error", e);
        }

        return false;
    }

    public String getToken(String token) {
        String type = "Bearer";
        if (StringUtils.hasText(token) && token.startsWith(type)) {
            return token.substring(type.length() + 1);
        }
        return null;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaims(accessToken);

        UserPrincipalDetail userPrincipalDetail =
                (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(claims.get("username").toString());

        return new UsernamePasswordAuthenticationToken(userPrincipalDetail, null, userPrincipalDetail.getAuthorities());
    }
}
