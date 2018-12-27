package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Verify;

public class SteemPercent {

  public static final int MAX_PROTOCOL_VALUE = (int) Math.pow(2, 16) - 1; // uint16_t
  public static final int MAX_INTEGER_PART = MAX_PROTOCOL_VALUE / 100;

  private final int protocolValue;

  private SteemPercent(int integerPart, int hundredths) {
    Verify.verify(0 <= integerPart && integerPart <= MAX_INTEGER_PART,
        String.format("integerPart must be [0, %d) but is %d", MAX_INTEGER_PART, integerPart));
    Verify.verify(0 <= hundredths && hundredths < 100,
        "hundredths must be [0, 100) but is " + hundredths);
    int protocolValue = integerPart * 100 + hundredths;
    Verify.verify(protocolValue <= MAX_PROTOCOL_VALUE,
        String.format("integerPart * 100 + hundredth <= %d but is %d",
            MAX_INTEGER_PART, protocolValue));
    this.protocolValue = protocolValue;
  }

  public static SteemPercent of(int integerPart, int hundredths) {
    return new SteemPercent(integerPart, hundredths);
  }

  @JsonFormat
  public int toProtocolValue() {
    return protocolValue;
  }
}
