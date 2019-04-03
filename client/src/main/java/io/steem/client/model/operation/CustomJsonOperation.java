package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class CustomJsonOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final List<String> requiredAuths;
    private final List<String> requiredPostingAuths;
    private final long id;
    private final String json;
  }

  private final Value value;

  private CustomJsonOperation(Value value) {
    super("custom_json");
    this.value = value;
  }

  public static CustomJsonOperation of(Value value) {
    return new CustomJsonOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
