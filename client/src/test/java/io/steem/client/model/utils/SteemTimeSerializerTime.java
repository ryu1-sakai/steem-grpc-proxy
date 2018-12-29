package io.steem.client.model.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import io.steem.client.model.util.SteemTimeSerializer;
import lombok.Value;
import org.apache.commons.lang3.RandomUtils;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class SteemTimeSerializerTime {

  @Value
  private static class AnObject {
    @JsonSerialize(using = SteemTimeSerializer.class)
    private Instant instantField;
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final long MAX_SECONDS = Instant.parse("9999-12-31T23:59:59Z").getEpochSecond();

  @DataPoints
  public static final List<Instant> INSTANTS = ImmutableList.of(
      Instant.ofEpochSecond(0),
      Instant.ofEpochSecond(RandomUtils.nextLong(1, MAX_SECONDS)),
      Instant.ofEpochSecond(MAX_SECONDS));

  @Theory
  public void serialize(Instant instant) throws Exception {
    // set up
    AnObject anObject = new AnObject(instant);

    // exercise
    String actual = OBJECT_MAPPER.writeValueAsString(anObject);

    // verify
    OffsetDateTime time = instant.atOffset(ZoneOffset.UTC);
    String expected = String.format("{\"instantField\":\"%d-%02d-%02dT%02d:%02d:%02d\"}",
        time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
        time.getHour(), time.getMinute(), time.getSecond());

    assertThat(actual).isEqualTo(expected);
  }

}
