package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
public class ClaimAccountOperation extends SteemOperation {
  
  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String creator;
    private final SteemAsset fee;
    private final List<FutureExtensions> extensions;
  }

  private final Value value;

  private ClaimAccountOperation(Value value) {
    super("claim_account");
    this.value = value;
  }

  public static ClaimAccountOperation of(Value value) {
    return new ClaimAccountOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
