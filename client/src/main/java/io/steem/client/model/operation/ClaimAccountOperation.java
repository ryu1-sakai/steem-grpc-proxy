package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;
import lombok.Builder;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
@ToString
public class ClaimAccountOperation extends SteemOperation {

  private final String creator;
  private final SteemAsset fee;
  private final List<FutureExtensions> extensions;

  private ClaimAccountOperation(String creator, SteemAsset fee, List<FutureExtensions> extensions) {
    super("claim_account");
    this.creator = creator;
    this.fee = fee;
    this.extensions = extensions;
  }

  @Override
  protected Map<String, Object> getValueMap() {
    ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
    Optional.ofNullable(creator).ifPresent(c -> builder.put("creator", c));
    Optional.ofNullable(fee).ifPresent(f -> builder.put("fee", f));
    builder.put("extensions", extensions != null ? extensions : Collections.emptyList());
    return builder.build();
  }
}
