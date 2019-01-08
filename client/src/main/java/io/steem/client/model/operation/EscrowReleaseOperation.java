package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.Map;

public class EscrowReleaseOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String from;
    private final String to;
    private final String agent;
    private final String who;
    private final String receiver;
    private final long escrowId;
    private final SteemAsset sbdAmount;
    private final SteemAsset steemAmount;
  }

  private final EscrowReleaseOperation.Value value;

  private EscrowReleaseOperation(Value value) {
    super("escrow_release");
    this.value = value;
  }

  public static EscrowReleaseOperation of(Value value) {
    return new EscrowReleaseOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
