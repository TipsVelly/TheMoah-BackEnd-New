package com.themoah.themoah.common.security;

import com.themoah.themoah.common.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String CREDENTIAL_KEY = "credential";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    
    private final Key key; 
    private final UserDetailsService userDetailsService;

    // 생성자(Constructor)
    public TokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userService;
    }

    /**
     * 토큰 생성 메서드
     * @param authentication
     * @return
     */
    public TokenDto generateToken(Authentication authentication) {

        String userId = "";
        String pwd = "";

        //권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        
        long now = (new Date()).getTime();

        // getPrincipal String 타입이면 id이다. 그렇지 않으면 pricipal가 UserDetails 객체이다.
        userId = authentication.getPrincipal() instanceof String ? (String)authentication.getPrincipal() : ((UserDetails)authentication.getPrincipal()).getUsername();
        pwd = authentication.getPrincipal() instanceof String ? (String)authentication.getCredentials() :  ((UserDetails)authentication.getPrincipal()).getPassword();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
            .setSubject(userId)                               // payload "sub": "name"
            .claim(CREDENTIAL_KEY, pwd)             // payload "sub": "name"
            .claim(AUTHORITIES_KEY, authorities)                                // payload "auth": "ROLE_USER"
            .setExpiration(accessTokenExpiresIn)                                // payload "exp": 151621022 (ex)
            .signWith(key, SignatureAlgorithm.HS512)                            // header "alg": "HS512"
            .compact();
        
        // Refresh Token 생성
        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        return TokenDto.builder()
            .grantType(BEARER_TYPE)
            .accessToken(accessToken)
            .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
            .refreshToken(refreshToken)
            .build();
    }

    /**
     * 
     * @param accessToken(String)
     * @return UsernamePasswordAuthenticationToken
     * 
     * @exception RuntimeException
     * @Writer yooeuiseon
     * 
     * @description
     * 인증이 확정된 권한으로 토큰에서 바꿔주는 메서드 (토큰 ---> 인증된 권한)
     */
    public Authentication getVerifiedAuthentication(String accessToken) {
        
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        
        String credentials = userDetailsService.loadUserByUsername(claims.getSubject()).getPassword();

        return new UsernamePasswordAuthenticationToken((String)claims.getSubject(), credentials, authorities);
    }
    /**
     * 
     * @param accessToken(String)
     * @return UsernamePasswordAuthenticationToken
     * 
     * @exception RuntimeException
     * @Writer yooeuiseon
     * 
     * @description
     * 인증이 비확정된 권한으로 토큰에서 바꿔주는 메서드 (토큰 ---> 비인증된 권한)
     * 
     * @Authentication --> 인증하기 위해서는 AuthenticationManager.authenticate(Authentication auth)를 통해서 비인증 권한을 인증권한으로 바꿔야 한다.
     */
    public Authentication getAuthentication(String accessToken) {
        
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.getSubject() == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.(1)");
        }

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.(2)");
        }

        if(claims.get(CREDENTIAL_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.(3)");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken((String)claims.getSubject(), (String)claims.get(CREDENTIAL_KEY));
    }

    /**
     * 토큰 에러 체크
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        }
        
    }

    /**
     * 토큰에서 userId를 가져온다.
     * @param accessToken
     * @return
     */
    public String extractUserId(String accessToken) {
        Claims claim = parseClaims(accessToken);
        String userId =String.valueOf(claim.getSubject());
        return userId;
    }

    /**
     * 권한에서 userId를 가져온다.
     * @param Authentication
     * @return
     */
    public String extractUserId(Authentication Authentication) {
        return Authentication.getName();
    }

    /**
     * 토큰 복호화 메서드
     * @param accessToken
     * @return
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /* 토큰 인증 여부 확인 */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getPrincipal() instanceof String ? (String)authentication.getPrincipal() : authentication.getName();
        String password =  authentication.getPrincipal() instanceof String ? (String)authentication.getCredentials() : ((UserDetails)authentication.getPrincipal()).getPassword();

        UserDetails principal = userDetailsService.loadUserByUsername(userId);// UsernameNotFoundException (실패시 예외 발생)

        if(!principal.getPassword().equals(password)) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password, principal.getAuthorities());

        return authenticationToken;
    }
}
