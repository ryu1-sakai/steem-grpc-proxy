package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class VoteOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String voter = RandomStringUtils.randomAlphanumeric(8);
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);
    short weight = (short) RandomUtils.nextInt(0, Short.MAX_VALUE + 1);

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
        .put("weight", weight)
        .build();
    Map<String, Object> expectedAppbase = ImmutableMap.of("type", "vote", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("vote", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
