package io.steem.client.model;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class SteemTimeTest {

  @Test
  public void parse() {
    // set up
    Instant expected = randomInstant();
    OffsetDateTime time = expected.atOffset(ZoneOffset.UTC);
    String steemTimeString = String.format("%d-%02d-%02dT%02d:%02d:%02d",
        time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
        time.getHour(), time.getMinute(), time.getSecond());

    // exercise
    SteemTime actual = SteemTime.parse(steemTimeString);

    // verify
    assertThat(actual.toInstant()).isEqualTo(expected);
  }

  @Test
  public void compose() {
    // set up
    Instant instant = randomInstant();
    SteemTime sut = new SteemTime(instant);

    // exercise
    String actual = sut.compose();

    // verify
    OffsetDateTime time = instant.atOffset(ZoneOffset.UTC);
    String expected = String.format("%d-%02d-%02dT%02d:%02d:%02d",
        time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
        time.getHour(), time.getMinute(), time.getSecond());

    assertThat(actual).isEqualTo(expected);
  }

  private static Instant randomInstant() {
    long maxEpochSecond = Instant.parse("2100-01-01T00:00:00Z").toEpochMilli() / 1000;
    return Instant.ofEpochSecond(RandomUtils.nextLong(0, maxEpochSecond));
  }
}
