package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SteemNai {
  SBD("@@000000013"),
  STEEM("@@000000021"),
  VESTS("@@000000037");

  private final String protocolValue;

  SteemNai(String protocolValue) {
    this.protocolValue = protocolValue;
  }

  @JsonValue
  public String toProtocolValue() {
    return protocolValue;
  }
}
