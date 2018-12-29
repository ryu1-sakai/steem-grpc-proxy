package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.VerifyException;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;

@RunWith(Theories.class)
public class SteemPercentTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @DataPoints
  public static final List<Integer> INTEGERS = ImmutableList.of(
      Integer.MIN_VALUE, -1,
      0, 1, 99, 100,
      101, SteemPercent.MAX_INTEGER_PART, SteemPercent.MAX_INTEGER_PART + 1);

  @Theory
  public void serialize(int integerPart, int hundredths) {
    // set up
    assumeThat(integerPart)
        .isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(SteemPercent.MAX_INTEGER_PART);
    assumeThat(hundredths).isGreaterThanOrEqualTo(0).isLessThan(100);
    assumeThat(integerPart * 100 + hundredths).isLessThanOrEqualTo(SteemPercent.MAX_PROTOCOL_VALUE);

    // exercise
    SteemPercent sut = SteemPercent.of(integerPart, hundredths);
    int actual = OBJECT_MAPPER.convertValue(sut, int.class);

    // verify
    assertThat(actual).isEqualTo(integerPart * 100 + hundredths);
  }

  @Theory
  public void of_invalidIntegerPart(int integerPart) {
    assumeThat(integerPart).matches(i -> i < 0 || i > SteemPercent.MAX_INTEGER_PART);

    assertThatThrownBy(() -> SteemPercent.of(integerPart, 0)).isInstanceOf(VerifyException.class);
  }

  @Theory
  public void of_invalidHundredths(int hundredths) {
    assumeThat(hundredths).matches(i -> i < 0 || i >= 100);

    assertThatThrownBy(() -> SteemPercent.of(0, hundredths)).isInstanceOf(VerifyException.class);
  }

  @Test
  public void of_overflow() {
    int integerPart = SteemPercent.MAX_PROTOCOL_VALUE / 100;
    int hundredths = SteemPercent.MAX_PROTOCOL_VALUE % 100 + 1;
    assumeThat(hundredths).isLessThan(100);

    assertThatThrownBy(() -> SteemPercent.of(integerPart, hundredths))
        .isInstanceOf(VerifyException.class);
  }
}
