package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;

@ToString
public class AccountUpdateOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String account;
    private final SteemAuthority owner;
    private final SteemAuthority active;
    private final SteemAuthority posting;
    private final String memoKey;
    private final String jsonMetadata;
  }

  private final AccountUpdateOperation.Value value;

  private AccountUpdateOperation(AccountUpdateOperation.Value value) {
    super("account_update");
    this.value = value;
  }

  public static AccountUpdateOperation of(AccountUpdateOperation.Value value) {
    return new AccountUpdateOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
