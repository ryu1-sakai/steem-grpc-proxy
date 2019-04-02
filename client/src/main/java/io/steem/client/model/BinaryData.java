package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.hash.HashCode;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class BinaryData {

  private final byte[] bytes;

  private BinaryData(byte[] bytes) {
    this.bytes = Arrays.copyOf(bytes, bytes.length);
  }

  public static BinaryData of(byte[] bytes) {
    return new BinaryData(bytes);
  }

  @JsonValue
  public String toProtocolValue() {
    if (ArrayUtils.isEmpty(bytes)) {
      return "";
    }
    @SuppressWarnings("UnstableApiUsage")
    String hex = HashCode.fromBytes(bytes).toString();
    return hex;
  }
}
