package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.Map;

public class AccountCreateOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final SteemAsset fee;
    private final String creator;
    private final String newAccountName;
    private final SteemAuthority owner;
    private final SteemAuthority active;
    private final SteemAuthority posting;
    private final String memoKey;
    private final String jsonMetadata;
  }

  private final Value value;

  private AccountCreateOperation(Value value) {
    super("account_create");
    this.value = value;
  }

  public static AccountCreateOperation of(Value value) {
    return new AccountCreateOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
