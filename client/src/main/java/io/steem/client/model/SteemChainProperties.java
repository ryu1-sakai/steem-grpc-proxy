package io.steem.client.model;

import lombok.Value;

@Value
public class SteemChainProperties {
  private SteemAsset accountCreationFee;
  private int maximumBlockSize;
  private SteemPercent sbdInterestRate;
}
