package io.steem.client.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class SteemExchangeRate {
  private final SteemAsset base;
  private final SteemAsset quote;
}
