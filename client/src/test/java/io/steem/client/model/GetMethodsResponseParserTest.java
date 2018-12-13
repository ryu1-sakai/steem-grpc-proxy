package io.steem.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.SteemClientException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetMethodsResponseParserTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void parseCondenserResponse() {
    // set up
    GetMethodsResponseParser sut = GetMethodsResponseParser.create();

    String responseJson
        = createResponseJson(ImmutableList.of(RandomStringUtils.randomAlphabetic(8)));

    // exercise & verify
    assertThatThrownBy(() -> sut.parseCondenserResponse(responseJson))
        .isInstanceOf(SteemClientException.class);
  }

  @Test
  public void parseAppbaseResponse() {
    // set up
    GetMethodsResponseParser sut = GetMethodsResponseParser.create();

    //noinspection UnstableApiUsage
    List<String> expectedMethods = Stream.generate(() -> RandomStringUtils.randomAlphabetic(8))
        .limit(8).collect(ImmutableList.toImmutableList());
    String responseJson = createResponseJson(expectedMethods);

    // exercise
    Optional<List<String>> actual = sut.parseAppbaseResponse(responseJson);

    // verify
    assertThat(actual).hasValue(expectedMethods);
  }

  private static String createResponseJson(List<String> methods) {
    try {
      Map<String, Object> responseObjectMap = ImmutableMap.of("result", methods);
      return OBJECT_MAPPER.writeValueAsString(responseObjectMap);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
