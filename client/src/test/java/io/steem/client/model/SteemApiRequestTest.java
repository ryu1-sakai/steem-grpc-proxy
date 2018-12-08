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

public class SteemApiRequestTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final MapType MAP_TYPE = OBJECT_MAPPER.getTypeFactory()
      .constructMapType(HashMap.class, String.class, Object.class);

  private static class SteemApiRequestMock extends SteemApiRequest {

    private final String condenserMethod;
    private final String appbaseMethod;
    private final List<Object> condenserParams;
    private final Map<String, Object> appbaseParams;

    public SteemApiRequestMock(long requestId, String condenserMethod, String appbaseMethod,
                               List<Object> condenserParams, Map<String, Object> appbaseParams) {
      super(requestId);
      this.condenserMethod = condenserMethod;
      this.appbaseMethod = appbaseMethod;
      this.condenserParams = condenserParams;
      this.appbaseParams = appbaseParams;
    }

    @Override
    public boolean isCondenserAvailable() {
      return true;
    }

    @Override
    public boolean isAppbaseAvailable() {
      return true;
    }

    @Override
    protected String getCondenserMethod() {
      return condenserMethod;
    }

    @Override
    protected String getAppbaseMethod() {
      return appbaseMethod;
    }

    @Override
    protected List<Object> getCondenserParams() {
      return condenserParams;
    }

    @Override
    protected Map<String, Object> getAppbaseParams() {
      return appbaseParams;
    }
  }

  @Test
  public void toCondenser() throws Exception {
    // set up
    long requestId = RandomUtils.nextLong();
    String method = RandomStringUtils.randomAlphabetic(8);
    List<Object> params = Stream.generate(() -> RandomStringUtils.randomAlphabetic(8))
        .limit(8).collect(ImmutableList.toImmutableList());
    SteemApiRequest sut = new SteemApiRequestMock(requestId, method, null, params, null);

    // exercise
    String actual = sut.toCondenser();

    // verify
    Map<String, Object> actualObjectMap = OBJECT_MAPPER.readValue(actual, MAP_TYPE);
    Map<String, Object> expectedObjectMap = ImmutableMap.<String, Object>builder()
        .put("jsonrpc", "2.0")
        .put("method", method)
        .put("params", params)
        .put("id", requestId)
        .build();
    assertThat(actualObjectMap).isEqualTo(expectedObjectMap);
  }

  @Test
  public void toAppbase() throws Exception {
    // set up
    long requestId = RandomUtils.nextLong();
    String method = RandomStringUtils.randomAlphabetic(8);
    Map<String, Object> params = Stream.generate(() -> {
      String key = RandomStringUtils.randomAlphabetic(8);
      String value = RandomStringUtils.randomAlphabetic(8);
      return new AbstractMap.SimpleEntry<>(key, value);
    }).limit(8).collect(ImmutableMap.toImmutableMap(AbstractMap.SimpleEntry::getKey,
        AbstractMap.SimpleEntry::getValue));
    SteemApiRequest sut = new SteemApiRequestMock(requestId, null, method, null, params);

    // exercise
    String actual = sut.toAppbase();

    // verify
    Map<String, Object> actualObjectMap = OBJECT_MAPPER.readValue(actual, MAP_TYPE);
    Map<String, Object> expectedObjectMap = ImmutableMap.<String, Object>builder()
        .put("jsonrpc", "2.0")
        .put("method", method)
        .put("params", params)
        .put("id", requestId)
        .build();
    assertThat(actualObjectMap).isEqualTo(expectedObjectMap);
  }
}
