package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SteemCurrencyTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void serialize() {
    // set up
    float amount = RandomUtils.nextFloat();
    String unit = RandomStringUtils.randomAlphabetic(5).toUpperCase();
    SteemCurrency sut = new SteemCurrency(amount, unit);

    // exercise
    String actual = OBJECT_MAPPER.convertValue(sut, String.class);

    // verify
    assertThat(actual).isEqualTo(String.format("%f %s", amount, unit));
  }
}
