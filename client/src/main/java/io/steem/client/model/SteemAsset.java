package io.steem.client.model;

import lombok.Value;

@Value
public class SteemAsset {
  private long amount;
  private int precision;
  private SteemNai nai;
}
