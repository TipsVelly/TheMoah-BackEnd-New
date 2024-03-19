package com.themoah.themoah.common.dto.response;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoah.themoah.common.constant.ResponseStatusEnum;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommonResponse<T> implements Serializable {
   private ResponseStatusEnum status;
   private String message;
   private T data;

   @Builder
   public CommonResponse(ResponseStatusEnum status, String message, T data) {
      this.status = status;
      this.message = message;
      this.data = data;
   }

   public static <T> CommonResponse<T> execute(ResponseStatusEnum status, String message, T data) {
      
      // 제네릭 빌더 패턴
      return CommonResponse.<T>builder()
               .status(status)
               .message(message)
               .data(data)
               .build();
   }

   public static <T> CommonResponse<T> onSuccess(String message, T data) {
      message = StringUtils.hasText(message) ? message : "처리에 성공하였습니다.";
      return execute(ResponseStatusEnum.SUCCESS, message, data);
   }

   public static <T> CommonResponse<T> onFailure(String message, T data) {
      message = StringUtils.hasText(message) ? message : "처리에 실패하였습니다.";
      return execute(ResponseStatusEnum.FAILURE, message, data);
   }

   public String convertToJson() throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(this);
   }

}
