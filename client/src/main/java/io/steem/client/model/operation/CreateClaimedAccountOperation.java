package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class CreateClaimedAccountOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String creator;
    private final String newAccountName;
    private final SteemAuthority owner;
    private final SteemAuthority active;
    private final SteemAuthority posting;
    private final String memoKey;
    private final String jsonMetadata;
    private final List<FutureExtensions> extensions;
  }

  private final Value value;

  private CreateClaimedAccountOperation(Value value) {
    super("create_claimed_account");
    this.value = value;
  }

  public static CreateClaimedAccountOperation of(Value value) {
    return new CreateClaimedAccountOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
