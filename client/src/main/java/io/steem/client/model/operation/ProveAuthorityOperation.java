package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.Map;

public class ProveAuthorityOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String challenged;
    private final boolean require_owner;
  }

  private final Value value;

  private ProveAuthorityOperation(Value value) {
    super("prove_authority");
    this.value = value;
  }

  public static ProveAuthorityOperation of(Value value) {
    return new ProveAuthorityOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
