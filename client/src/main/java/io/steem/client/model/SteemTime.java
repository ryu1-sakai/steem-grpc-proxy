package io.steem.client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.annotations.VisibleForTesting;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@JsonSerialize(using = SteemTime.Serializer.class)
public class SteemTime {

  public static class Serializer extends JsonSerializer<SteemTime> {

    @Override
    public void serialize(SteemTime value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeString(value.serialize());
    }
  }

  private static final DateTimeFormatter FORMATTER
      = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

  private final Instant instant;

  @VisibleForTesting
  private SteemTime(Instant instant) {
    this.instant = instant;
  }

  public static SteemTime of(Instant instant) {
    return new SteemTime(instant);
  }

  public static SteemTime from(String steemTimeString) {
    LocalDateTime localDateTime = FORMATTER.parse(steemTimeString, LocalDateTime::from);
    Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
    return new SteemTime(instant);
  }

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
