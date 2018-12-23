package io.steem.client.model;

import lombok.Value;

@Value
public class SteemPercent {

  private int integer;
  private int hundredths;

  public int toProtocolValue() {
    return integer * 100 + hundredths;
  }
}
