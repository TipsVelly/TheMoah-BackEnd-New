package com.themoah.themoah.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.themoah.themoah.common.dto.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;

public class ResponseMessage {
    public static HttpServletResponse sendErrorMessage(HttpServletResponse response, String message ,RuntimeException e) throws JsonProcessingException, IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(
            CommonResponse.onFailure(message, Map.of("redirect_back", true))
            .convertToJson()
        );

        return response;
    }
}
