package io.steem.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.SteemClientException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class SteemApiRequest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String JSON_RPC = "2.0";

  private final long requestId;

  protected SteemApiRequest(long requestId) {
    this.requestId = requestId;
  }

  public abstract boolean isCondenserAvailable();

  public abstract boolean isAppbaseAvailable();

  protected abstract String getCondenserMethod();

  protected abstract String getAppbaseMethod();

  protected abstract List<Object> getCondenserParams();

  protected abstract Map<String, Object> getAppbaseParams();

  public String toCondenser() {
    Map<String, Object> objectMap =
        ImmutableMap.<String, Object>builder()
            .put("jsonrpc", JSON_RPC)
            .put("method", getCondenserMethod())
            .put("params", getCondenserParams())
            .put("id", requestId)
            .build();
    try {
      return OBJECT_MAPPER.writeValueAsString(objectMap);
    } catch (JsonProcessingException e) {
      throw new SteemClientException("Error in generating JSON: " + objectMap, e);
    }
  }

  public String toAppbase() {
    ImmutableMap.Builder<String, Object> builder =
        ImmutableMap.<String, Object>builder()
            .put("jsonrpc", JSON_RPC)
            .put("method", getAppbaseMethod())
            .put("id", requestId);
    Map<String, Object> params = getAppbaseParams();
    if (!params.isEmpty()) {
      builder.put("params", params);
    }
    Map<String, Object> objectMap = builder.build();
    try {
      return OBJECT_MAPPER.writeValueAsString(objectMap);
    } catch (JsonProcessingException e) {
      throw new SteemClientException("Error in generating JSON: " + objectMap, e);
    }
  }
}
