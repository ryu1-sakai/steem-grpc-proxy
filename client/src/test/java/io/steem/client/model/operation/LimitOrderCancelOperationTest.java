package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LimitOrderCancelOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String owner = RandomStringUtils.randomAlphanumeric(8);
    long oderid = RandomUtils.nextLong();

    LimitOrderCancelOperation.Value value = LimitOrderCancelOperation.Value.builder()
        .owner(owner)
        .oderid(oderid)
        .build();
    LimitOrderCancelOperation sut = LimitOrderCancelOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("owner", owner)
        .put("oderid", oderid)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "limit_order_cancel", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("limit_order_cancel", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
