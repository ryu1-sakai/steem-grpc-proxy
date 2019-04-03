package io.steem.client.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class VotableAssetInfoV1 {
  private final long maxAcceptedPayout;
  private final boolean allowCurationRewards;
}
