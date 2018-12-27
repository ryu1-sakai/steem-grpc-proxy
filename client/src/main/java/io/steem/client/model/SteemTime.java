package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.annotations.VisibleForTesting;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SteemTime {

  private static final DateTimeFormatter FORMATTER
      = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

  private final Instant instant;

  @VisibleForTesting
  SteemTime(Instant instant) {
    this.instant = instant;
  }

  @JsonCreator
  public static SteemTime create(String steemTimeString) {
    LocalDateTime localDateTime = FORMATTER.parse(steemTimeString, LocalDateTime::from);
    Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
    return new SteemTime(instant);
  }

  @JsonFormat
  public String format() {
    return FORMATTER.format(instant.atOffset(ZoneOffset.UTC));
  }

  public Instant toInstant() {
    return instant;
  }

  @Override
  public String toString() {
    return format();
  }
}
