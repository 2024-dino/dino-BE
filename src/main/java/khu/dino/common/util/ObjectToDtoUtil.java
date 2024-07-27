package khu.dino.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToDtoUtil {
    public static Object  jsonStrToObj(String jsonStr, Class<?> classType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readerFor(classType).readValue(jsonStr);
    }
}
