package io.steem.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.SteemClientException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class SteemApiRequest {

  protected interface Composer<T> {
    String getMethod();
    T getParams();
  }

  protected interface CondenserComposer extends Composer<List<Object>> {}

  protected interface AppbaseCompser extends Composer <Map<String, Object>> {}

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String JSON_RPC = "2.0";

  private final Composer<List<Object>> condenserComposer;
  private final Composer<Map<String, Object>> appbaseComposer;
  private final long requestId;

  protected SteemApiRequest(@Nullable CondenserComposer condenserComposer,
                            @Nullable AppbaseCompser appbaseComposer,
                            long requestId) {
    this.condenserComposer = condenserComposer;
    this.appbaseComposer = appbaseComposer;
    this.requestId = requestId;
  }

  public boolean isCondenserAvailable() {
    return condenserComposer != null;
  }

  public boolean isAppbaseAvailable() {
    return appbaseComposer != null;
  }

  public String toCondenser() {
    if (condenserComposer == null) {
      throw new SteemClientException("Condenser API unavailable");
    }
    ImmutableMap.Builder<String, Object> builder =
        ImmutableMap.<String, Object>builder()
            .put("jsonrpc", JSON_RPC)
            .put("method", condenserComposer.getMethod())
            .put("id", requestId);
    List<Object> params = condenserComposer.getParams();
    if (params != null) {
      builder.put("params", params);
    }
    Map<String, Object> objectMap = builder.build();
    try {
      return OBJECT_MAPPER.writeValueAsString(objectMap);
    } catch (JsonProcessingException e) {
      throw new SteemClientException("Error in generating JSON: " + objectMap, e);
    }
  }

  public String toAppbase() {
    if (appbaseComposer == null) {
      throw new SteemClientException("Appbase API unavailable");
    }
    ImmutableMap.Builder<String, Object> builder =
        ImmutableMap.<String, Object>builder()
            .put("jsonrpc", JSON_RPC)
            .put("method", appbaseComposer.getMethod())
            .put("id", requestId);
    Map<String, Object> params = appbaseComposer.getParams();
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
