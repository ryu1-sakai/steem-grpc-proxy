package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Value(staticConstructor = "of")
public class SteemTime {

  // DateTimeFormatter isn't compatible with SteemTime when the year is larger tha 9999
  public static final Instant MAX_INSTANT = Instant.parse("+10000-01-01T00:00:00Z").minusSeconds(1);

  private static final DateTimeFormatter FORMATTER
      = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

  private final Instant instant;

  public static SteemTime from(String steemTimeString) {
    LocalDateTime localDateTime = FORMATTER.parse(steemTimeString, LocalDateTime::from);
    Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
    return new SteemTime(instant);
  }

  @JsonValue
  private String serialize() {
    return FORMATTER.format(instant.atOffset(ZoneOffset.UTC));
  }

  public Instant toInstant() {
    return instant;
  }

  @Override
  public String toString() {
    return serialize();
  }
}
