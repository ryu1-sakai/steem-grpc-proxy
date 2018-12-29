package io.steem.client.model.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SteemTimeSerializer extends JsonSerializer<Instant> {

  private static final DateTimeFormatter FORMATTER
      = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(FORMATTER.format(value.atOffset(ZoneOffset.UTC)));
  }
}
