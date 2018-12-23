package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.SteemOperation;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

@Builder
@ToString
public class AccountUpdateOperation extends SteemOperation {

  private final String accountName;
  private final SteemAuthority owner;
  private final SteemAuthority active;
  private final SteemAuthority posting;
  private final String memoKey;
  private final String jsonMetadata;

  private AccountUpdateOperation(String accountName, SteemAuthority owner, SteemAuthority active,
                                 SteemAuthority posting, String memoKey, String jsonMetadata) {
    super("account_create");
    this.accountName = accountName;
    this.owner = owner;
    this.active = active;
    this.posting = posting;
    this.memoKey = memoKey;
    this.jsonMetadata = jsonMetadata;
  }

  @Override
  protected Map<String, Object> getValueMap() {
    ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
    Optional.ofNullable(accountName).ifPresent(n -> builder.put("new_account_name", n));
    Optional.ofNullable(owner).ifPresent(o -> builder.put("owner", o.compose()));
    Optional.ofNullable(active).ifPresent(a -> builder.put("active", a.compose()));
    Optional.ofNullable(posting).ifPresent(p -> builder.put("posting", p.compose()));
    Optional.ofNullable(memoKey).ifPresent(m -> builder.put("memo_key", m));
    Optional.ofNullable(jsonMetadata).ifPresent(j -> builder.put("json_metadata", j));
    return builder.build();
  }
}
