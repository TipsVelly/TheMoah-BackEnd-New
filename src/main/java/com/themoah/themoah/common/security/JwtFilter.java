package com.themoah.themoah.common.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.themoah.themoah.common.util.ResponseMessage;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    // 실제 필터링 로직은 doFilterInternal에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

            try {
                // 1. Request Header에서 토큰을 추출
                String jwt = resolveToken(request);

                // 2. validateToken으로 토큰 유효성 검사
                // 정상 토큰이면 해당 토큰으로 Authentication을 가져와서 SecurityContext에 저장
                if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                    
                    // 토큰에서 인증되지 않은 권한을 추출
                    Authentication authentication = tokenProvider.getAuthentication(jwt);

                    // 해당 권한을 체크한 후 인증권한으로 전환
                    Authentication verifiedAuthentication = tokenProvider.authenticate(authentication);

                    // securityContext에 권한을 저장
                    SecurityContextHolder.getContext().setAuthentication(verifiedAuthentication);
                }

                filterChain.doFilter(request, response);
                
            } catch (ExpiredJwtException e) { // 토큰 만료시 발생하는 에러
                log.error("JWT 토큰에 권한이 만료되었습니다.");
                ResponseMessage.sendErrorMessage(response, "JWT 토큰에 권한이 만료되었습니다.", e);
            } catch(JwtException e) {
                log.error("JWT 토큰에 에러가 발생하였습니다.");
                ResponseMessage.sendErrorMessage(response, "JWT 토큰에 에러가 발생하였습니다.", e);
            } catch (AuthenticationException e) {
                log.error("권한 정보 인증을 실패하였습니다.");
                ResponseMessage.sendErrorMessage(response, "권한 정보 인증을 실패하였습니다.", e);
            } catch(RuntimeException e) {
                log.error("RuntimeException 발생하였습니다.");
                ResponseMessage.sendErrorMessage(response, "RuntimeException 발생하였습니다.", e);
            }
    }

    /**
     * rqeust header에서 token만 추출
     * @param request
     * @return
     */

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))  {
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }
}
