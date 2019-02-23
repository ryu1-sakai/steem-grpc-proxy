package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.io.IOException;

public class SteemCurrency {
  private final float amount;
  private final String unit;

  public SteemCurrency(float amount, String unit) {
    this.amount = amount;
    this.unit = unit;
  }

  @JsonValue
  public String serialize() {
    return String.format("%f %s", amount, unit);
  }
}
