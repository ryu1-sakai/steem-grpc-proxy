package io.steem.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.SteemClientException;

import javax.annotation.Nullable;
import java.util.Map;

public class SteemApiRequest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String JSON_RPC = "2.0";

  private final String condenserMeghod;
  private final String appbaseMethod;
  private final SteemApiRequestParams params;
  private final long requestId;

  protected SteemApiRequest(@Nullable String condenserMethod, @Nullable String appbaseMethod,
                            SteemApiRequestParams params, long requestId) {
    this.condenserMeghod = condenserMethod;
    this.appbaseMethod = appbaseMethod;
    this.params = params;
    this.requestId = requestId;
  }

  public String toCondenser() {
    if (condenserMeghod == null) {
      throw new SteemClientException("Condenser API unavailable");
    }
    ImmutableMap.Builder<String, Object> builder =
        ImmutableMap.<String, Object>builder()
            .put("jsonrpc", JSON_RPC)
            .put("method", condenserMeghod)
            .put("id", requestId);
    if (params.forCondenser() != null) {
      builder.put("params", params.forCondenser());
    }
    Map<String, Object> objectMap = builder.build();
    try {
      return OBJECT_MAPPER.writeValueAsString(objectMap);
    } catch (JsonProcessingException e) {
      throw new SteemClientException("Error in generating JSON: " + objectMap, e);
    }
  }

  public String toAppbase() {
    if (appbaseMethod == null) {
      throw new SteemClientException("Appbase API unavailable");
    }
    ImmutableMap.Builder<String, Object> builder =
        ImmutableMap.<String, Object>builder()
            .put("jsonrpc", JSON_RPC)
            .put("method", appbaseMethod)
            .put("id", requestId);
    if (params.forAppbase() != null) {
      builder.put("params", params.forAppbase());
    }
    Map<String, Object> objectMap = builder.build();
    try {
      return OBJECT_MAPPER.writeValueAsString(objectMap);
    } catch (JsonProcessingException e) {
      throw new SteemClientException("Error in generating JSON: " + objectMap, e);
    }
  }
}
