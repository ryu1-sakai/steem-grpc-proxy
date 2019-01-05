package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.SteemTime;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.Map;

public class EscrowTransferOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String from;
    private final String to;
    private final String agent;
    private final long escrowId; // Originally uint32
    private final SteemAsset sbdAmount;
    private final SteemAsset steemAmount;
    private final SteemAsset fee;
    private final SteemTime ratificationDeadline;
    private final SteemTime escrowExpiration;
  }

  private final Value value;

  private EscrowTransferOperation(Value value) {
    super("escrow_transfer");
    this.value = value;
  }

  public static EscrowTransferOperation of(Value value) {
    return new EscrowTransferOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
