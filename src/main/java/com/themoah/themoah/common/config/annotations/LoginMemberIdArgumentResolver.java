package com.themoah.themoah.common.config.annotations;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.themoah.themoah.common.security.TokenProvider;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
       return parameter.hasParameterAnnotation(LoginMemberId.class) 
            && parameter.getParameterType().equals(String.class);
       
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization").split("Bearer ")[1];
        return tokenProvider.extractUserId(accessToken);
    }
    
}
