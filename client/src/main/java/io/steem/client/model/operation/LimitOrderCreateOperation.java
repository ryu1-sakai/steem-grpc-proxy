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

public class LimitOrderCreateOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String owner;
    private final long oderid;
    private final SteemAsset amountToSell;
    private final SteemAsset minToReceive;
    private final boolean fillOrKill;
    private final SteemTime expiration;
  }

  private final Value value;

  private LimitOrderCreateOperation(Value value) {
    super("limit_order_create");
    this.value = value;
  }

  public static LimitOrderCreateOperation of(Value value) {
    return new LimitOrderCreateOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
