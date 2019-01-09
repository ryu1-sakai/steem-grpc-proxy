package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.Map;

public class SetWithdrawVestingRouteOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String fromAccount;
    private final String toAccount;
    private final int percent;
    private final boolean autoVest;
  }

  private final SetWithdrawVestingRouteOperation.Value value;

  private SetWithdrawVestingRouteOperation(Value value) {
    super("set_withdraw_vesting_route");
    this.value = value;
  }

  public static SetWithdrawVestingRouteOperation of(Value value) {
    return new SetWithdrawVestingRouteOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
