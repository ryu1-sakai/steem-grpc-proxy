package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ConvertOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String owner = RandomStringUtils.randomAlphanumeric(8);
    long requestId = RandomUtils.nextLong();
    SteemAsset amount = OperationTestUtils.randomSteemAsset();

    ConvertOperation.Value value = ConvertOperation.Value.builder()
        .owner(owner)
        .requestid(requestId)
        .amount(amount)
        .build();
    ConvertOperation sut = ConvertOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("owner", owner)
        .put("requestid", requestId)
        .put("amount", ObjectMapUtils.toObjectMap(amount))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "convert", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("convert", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}