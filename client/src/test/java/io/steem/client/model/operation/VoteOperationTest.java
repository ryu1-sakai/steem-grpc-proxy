package io.steem.client.model.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemPercent;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class VoteOperationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String voter = RandomStringUtils.randomAlphanumeric(8);
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);
    SteemPercent weight = OperationTestUtils.randomSteemPercent();

    VoteOperation.Value value = VoteOperation.Value.builder()
        .voter(voter)
        .author(author)
        .permlink(permlink)
        .weight(weight)
        .build();
    VoteOperation sut = VoteOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("voter", voter)
        .put("author", author)
        .put("permlink", permlink)
        .put("weight", OBJECT_MAPPER.convertValue(weight, Integer.class))
        .build();
    Map<String, Object> expectedAppbase = ImmutableMap.of("type", "vote", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("vote", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
