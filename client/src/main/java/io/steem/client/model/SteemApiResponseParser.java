package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.steem.client.SteemClientException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class SteemApiResponseParser<T> {

  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public Optional<T> parseCondenserResponse(String response) {
    Map<String, Object> objectMap = toObjectMap(response);
    return Optional.ofNullable(objectMap.get("result")).map(this::parseCondenserResult);
  }

  public Optional<T> parseAppbaseResponse(String response) {
    Map<String, Object> objectMap = toObjectMap(response);
    return Optional.ofNullable(objectMap.get("result")).map(this::parseAppbaseResult);
  }

  private static Map<String, Object> toObjectMap(String json) {
    try {
      MapType mapType = OBJECT_MAPPER.getTypeFactory()
          .constructMapType(HashMap.class, String.class, Object.class);
      return OBJECT_MAPPER.readValue(json, mapType);
    } catch (IOException e) {
      throw new SteemClientException("Error in parsing JSON: " + json, e);
    }
  }

  protected abstract T parseCondenserResult(Object result);

  protected abstract T parseAppbaseResult(Object result);
}
