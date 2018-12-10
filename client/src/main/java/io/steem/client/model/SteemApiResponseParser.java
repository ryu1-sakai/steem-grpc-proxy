package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.steem.client.SteemClientException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SteemApiResponseParser<T> {

  protected interface ResultParser<T> extends Function<Object, T> {}

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final ResultParser<T> condenserResultParser;
  private final ResultParser<T> appbaseResultParser;

  protected SteemApiResponseParser(@Nullable ResultParser<T> condenserResultParser,
                                   @Nullable ResultParser<T> appbaseResultParser) {
    this.condenserResultParser = condenserResultParser;
    this.appbaseResultParser = appbaseResultParser;
  }

  public Optional<T> parseCondenserResponse(String response) {
    if (condenserResultParser == null) {
      throw new SteemClientException("Condenser API unavailable");
    }
    Map<String, Object> objectMap = toObjectMap(response);
    return Optional.ofNullable(objectMap.get("result")).map(condenserResultParser);
  }

  public Optional<T> parseAppbaseResponse(String response) {
    if (appbaseResultParser == null) {
      throw new SteemClientException("Appbase API unavailable");
    }
    Map<String, Object> objectMap = toObjectMap(response);
    return Optional.ofNullable(objectMap.get("result")).map(appbaseResultParser);
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
}
