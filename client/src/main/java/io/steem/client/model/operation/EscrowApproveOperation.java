package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.Map;

public class EscrowApproveOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String from;
    private final String to;
    private final String agent;
    private final String who;
    private final long escrowId;
    private final boolean approve;
  }

  private final Value value;

  private EscrowApproveOperation(Value value) {
    super("escrow_approve");
    this.value = value;
  }

  public static EscrowApproveOperation of(Value value) {
    return new EscrowApproveOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
