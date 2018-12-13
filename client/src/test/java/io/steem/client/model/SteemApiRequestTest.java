package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SteemApiRequestTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final MapType MAP_TYPE = OBJECT_MAPPER.getTypeFactory()
      .constructMapType(HashMap.class, String.class, Object.class);

  @Test
  public void toCondenser() throws Exception {
    // set up
    String method = RandomStringUtils.randomAlphabetic(8);

    SteemApiRequestParams params = mock(SteemApiRequestParams.class);
    @SuppressWarnings("UnstableApiUsage")
    List<Object> paramList = Stream.generate(() -> RandomStringUtils.randomAlphabetic(8))
        .limit(8).collect(ImmutableList.toImmutableList());
    given(params.forCondenser()).willReturn(paramList);

    long requestId = RandomUtils.nextLong();

    SteemApiRequest sut = new SteemApiRequest(method, null, params, requestId);

    // exercise
    String actual = sut.toCondenser();

    // verify
    Map<String, Object> actualObjectMap = OBJECT_MAPPER.readValue(actual, MAP_TYPE);
    Map<String, Object> expectedObjectMap = ImmutableMap.<String, Object>builder()
        .put("jsonrpc", "2.0")
        .put("method", method)
        .put("params", paramList)
        .put("id", requestId)
        .build();
    assertThat(actualObjectMap).isEqualTo(expectedObjectMap);
  }

  @Test
  public void toAppbase() throws Exception {
    // set up
    String method = RandomStringUtils.randomAlphabetic(8);

    SteemApiRequestParams params = mock(SteemApiRequestParams.class);
    @SuppressWarnings("UnstableApiUsage")
    Map<String, Object> paramMap = Stream.generate(() -> {
      String key = RandomStringUtils.randomAlphabetic(8);
      String value = RandomStringUtils.randomAlphabetic(8);
      return new AbstractMap.SimpleEntry<>(key, value);
    }).limit(8).collect(ImmutableMap.toImmutableMap(AbstractMap.SimpleEntry::getKey,
        AbstractMap.SimpleEntry::getValue));
    given(params.forAppbase()).willReturn(paramMap);

    long requestId = RandomUtils.nextLong();

    SteemApiRequest sut = new SteemApiRequest(null, method, params, requestId);

    // exercise
    String actual = sut.toAppbase();

    // verify
    Map<String, Object> actualObjectMap = OBJECT_MAPPER.readValue(actual, MAP_TYPE);
    Map<String, Object> expectedObjectMap = ImmutableMap.<String, Object>builder()
        .put("jsonrpc", "2.0")
        .put("method", method)
        .put("params", paramMap)
        .put("id", requestId)
        .build();
    assertThat(actualObjectMap).isEqualTo(expectedObjectMap);
  }
}
