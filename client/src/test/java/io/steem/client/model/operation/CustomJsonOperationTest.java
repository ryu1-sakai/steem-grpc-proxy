package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomJsonOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    List<String> requiredAuths = ImmutableList.of(RandomStringUtils.randomAlphanumeric(8),
        RandomStringUtils.randomAlphanumeric(8));
    List<String> requiredPostingAuths = ImmutableList.of(RandomStringUtils.randomAlphanumeric(8),
        RandomStringUtils.randomAlphanumeric(8));
    long id = RandomUtils.nextLong();
    String json = RandomStringUtils.randomAlphabetic(32);

    CustomJsonOperation.Value value = CustomJsonOperation.Value.builder()
        .requiredAuths(requiredAuths)
        .requiredPostingAuths(requiredPostingAuths)
        .id(id)
        .json(json)
        .build();
    CustomJsonOperation sut = CustomJsonOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    @SuppressWarnings("UnstableApiUsage")
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("required_auths", requiredAuths)
        .put("required_posting_auths", requiredPostingAuths)
        .put("id", id)
        .put("json", json)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "custom_json", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("custom_json", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);

  }
}
