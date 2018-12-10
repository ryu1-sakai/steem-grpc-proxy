package io.steem.client.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class SteemApiResponseParserTest {

  @Test
  public void parseCondenserResponse() {
    // set up
    @SuppressWarnings("unchecked")
    SteemApiResponseParser.ResultParser<String> condenserParser
        = mock(SteemApiResponseParser.ResultParser.class);
    SteemApiResponseParser<String> sut = new SteemApiResponseParser<>(condenserParser, null);

    String expected = RandomStringUtils.randomAlphanumeric(8);
    given(condenserParser.apply(any())).willReturn(expected);

    String resultJson = RandomStringUtils.randomAlphanumeric(8);
    String responseJson = String.format("{\"result\": \"%s\"}", resultJson);

    // exercise
    Optional<String> actual = sut.parseCondenserResponse(responseJson);

    // verify
    assertThat(actual).hasValue(expected);

    then(condenserParser).should().apply(resultJson);
  }

  @Test
  public void parseCondenserResponse_empty() {
    // set up
    @SuppressWarnings("unchecked")
    SteemApiResponseParser.ResultParser<String> condenserParser
        = mock(SteemApiResponseParser.ResultParser.class);
    SteemApiResponseParser<String> sut = new SteemApiResponseParser<>(condenserParser, null);

    given(condenserParser.apply(any())).willReturn(null);

    String resultJson = RandomStringUtils.randomAlphanumeric(8);
    String responseJson = String.format("{\"result\": \"%s\"}", resultJson);

    // exercise
    Optional<String> actual = sut.parseCondenserResponse(responseJson);

    // verify
    assertThat(actual).isEmpty();

    then(condenserParser).should().apply(resultJson);
  }

  @Test
  public void parseAppbaseResponse() {
    // set up
    @SuppressWarnings("unchecked")
    SteemApiResponseParser.ResultParser<String> appbaseParser
        = mock(SteemApiResponseParser.ResultParser.class);
    SteemApiResponseParser<String> sut = new SteemApiResponseParser<>(null, appbaseParser);

    String expected = RandomStringUtils.randomAlphanumeric(8);
    given(appbaseParser.apply(any())).willReturn(expected);

    String resultJson = RandomStringUtils.randomAlphanumeric(8);
    String responseJson = String.format("{\"result\": \"%s\"}", resultJson);

    // exercise
    Optional<String> actual = sut.parseAppbaseResponse(responseJson);

    // verify
    assertThat(actual).hasValue(expected);

    then(appbaseParser).should().apply(resultJson);
  }

  @Test
  public void parseAppbaseResponse_empty() {
    // set up
    @SuppressWarnings("unchecked")
    SteemApiResponseParser.ResultParser<String> appbaseParser
        = mock(SteemApiResponseParser.ResultParser.class);
    SteemApiResponseParser<String> sut = new SteemApiResponseParser<>(null, appbaseParser);

    given(appbaseParser.apply(any())).willReturn(null);

    String resultJson = RandomStringUtils.randomAlphanumeric(8);
    String responseJson = String.format("{\"result\": \"%s\"}", resultJson);

    // exercise
    Optional<String> actual = sut.parseAppbaseResponse(responseJson);

    // verify
    assertThat(actual).isEmpty();

    then(appbaseParser).should().apply(resultJson);
  }
}
