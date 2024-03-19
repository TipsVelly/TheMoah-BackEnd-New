package com.themoah.themoah.common.config.format;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * NumberToStringSerializer 생성 : 숫자 값 치환
 * Float, Double, Long, Integer 등 숫자 타입인 경우 문자 형태로 반환, 100 -> "100"
 */

public class NumberToStringSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value == null) gen.writeString("");
        else gen.writeString(value.toString());
    }
}
