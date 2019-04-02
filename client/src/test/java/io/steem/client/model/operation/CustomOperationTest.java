package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import io.steem.client.model.BinaryData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    List<String> requiredAuths = ImmutableList.of(RandomStringUtils.randomAlphanumeric(8),
        RandomStringUtils.randomAlphanumeric(8));
    long id = RandomUtils.nextLong();
    byte[] data = RandomUtils.nextBytes(32);

    CustomOperation.Value value = CustomOperation.Value.builder()
        .requiredAuths(requiredAuths)
        .id(id)
        .data(BinaryData.of(data))
        .build();
    CustomOperation sut = CustomOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    @SuppressWarnings("UnstableApiUsage")
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("required_auths", requiredAuths)
        .put("id", id)
        .put("data", HashCode.fromBytes(data).toString())
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "custom", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("custom", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);

  }
}
