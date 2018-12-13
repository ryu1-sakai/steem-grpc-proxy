package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.steem.client.SteemClientException;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetMethodsRequestTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final MapType MAP_TYPE = OBJECT_MAPPER.getTypeFactory()
      .constructMapType(HashMap.class, String.class, Object.class);

  @Test
  public void toCondenser() {
    // set up
    long requestId = RandomUtils.nextLong();
    GetMethodsRequest request = GetMethodsRequest.create(requestId);

    // exercise & verify
    assertThatThrownBy(() -> request.toCondenser()).isInstanceOf(SteemClientException.class);
  }

  @Test
  public void toAppbase() throws Exception {
    // set up
    long requestId = RandomUtils.nextLong();
    GetMethodsRequest request = GetMethodsRequest.create(requestId);

    // exercise
    String actual = request.toAppbase();

    // verify
    Map<String, Object> actualObjectMap = OBJECT_MAPPER.readValue(actual, MAP_TYPE);
    assertThat(actualObjectMap).containsEntry("method", "jsonrpc.get_methods");
    assertThat(actualObjectMap).containsEntry("params", Collections.emptyMap());
  }
}
