package com.themoah.themoah.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoah.themoah.common.config.annotations.Message;
import com.themoah.themoah.common.dto.response.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice(basePackages = "com.themoah.themoah")
@RequiredArgsConstructor
@Slf4j
public class CommonResponseConverter implements ResponseBodyAdvice {
    private final ObjectMapper objectMapper;
    
    private String msg = "";

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
       return true ;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

            // status setting 
            Object exceptionHandlerAnnotation = returnType.getMethodAnnotation(ExceptionHandler.class);

            // if The StringHttpMessageConverter is referred, convert Object to String with ObjectMapper.
            // (exceptionHandler 여부 체크 --> 상태값(status) 변경
            if(selectedConverterType == StringHttpMessageConverter.class) {
                try {
                    if(exceptionHandlerAnnotation != null) {
                        return objectMapper.writeValueAsString(CommonResponse.onFailure(msg, body));
                    } else {
                        return objectMapper.writeValueAsString(CommonResponse.onSuccess(msg, body));
                    }
                } catch (JsonProcessingException e) {
                    if(exceptionHandlerAnnotation != null) {
                        return convertCommonResponseToJSONObject(CommonResponse.onFailure(msg, body)).toJSONString();
                    } else {
                        return convertCommonResponseToJSONObject(CommonResponse.onSuccess(msg, body)).toJSONString();
                    }
                }
            }

            // returnType이 JSONObject일 때, 그대로 반환
            if(body instanceof JSONObject) {
                return body;
            }

            // status setting 
            if(exceptionHandlerAnnotation != null) {
                return CommonResponse.onFailure(msg, body);
            } else {
                return CommonResponse.onSuccess(msg, body);
            }
    }

    private JSONObject convertCommonResponseToJSONObject(CommonResponse commonResponse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",commonResponse.getStatus());
        jsonObject.put("message", commonResponse.getMessage());
        jsonObject.put("data", commonResponse.getData());

        return jsonObject;
    }
}