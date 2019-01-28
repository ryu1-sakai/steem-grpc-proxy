package io.steem.client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.io.IOException;

@Getter
@JsonSerialize(using = SteemCurrency.Serializer.class)
public class SteemCurrency {
  private final float amount;
  private final String unit;

  public static class Serializer extends JsonSerializer<SteemCurrency> {

    @Override
    public void serialize(SteemCurrency currency, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeString(String.format("%f %s", currency.getAmount(), currency.getUnit()));
    }
  }

  public SteemCurrency(float amount, String unit) {
    this.amount = amount;
    this.unit = unit;
  }
}
