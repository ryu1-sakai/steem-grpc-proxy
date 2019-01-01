package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class SteemTimeTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void from() {
    // set up
    Instant expected = randomInstant();
    OffsetDateTime time = expected.atOffset(ZoneOffset.UTC);
    String steemTimeString = String.format("%d-%02d-%02dT%02d:%02d:%02d",
        time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
        time.getHour(), time.getMinute(), time.getSecond());

    // exercise
    SteemTime actual = SteemTime.from(steemTimeString);

    // verify
    assertThat(actual.toInstant()).isEqualTo(expected);
  }

  @Test
  public void serialize() {
    // set up
    Instant instant = randomInstant();
    SteemTime sut = SteemTime.of(instant);

    // exercise
    String actual = OBJECT_MAPPER.convertValue(sut, String.class);

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
