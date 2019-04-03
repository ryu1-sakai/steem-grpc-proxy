package io.steem.client.model.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public final class ObjectMapUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private ObjectMapUtils() {}

  public static <T> Map<String, Object> toObjectMap(T value) {
    return OBJECT_MAPPER.convertValue(value, new TypeReference<Map<String, Object>>() {});
  }

  public static <T> List<Object> toObjectMapList(List<T> value) {
    return OBJECT_MAPPER.convertValue(value, new TypeReference<List<Object>>() {});
  }
}
