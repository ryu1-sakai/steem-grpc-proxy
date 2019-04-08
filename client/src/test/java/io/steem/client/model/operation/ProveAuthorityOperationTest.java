package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProveAuthorityOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String challenged = RandomStringUtils.randomAlphanumeric(8);
    boolean require_owner = RandomUtils.nextBoolean();

    ProveAuthorityOperation.Value value = ProveAuthorityOperation.Value.builder()
        .challenged(challenged)
        .require_owner(require_owner)
        .build();
    ProveAuthorityOperation sut = ProveAuthorityOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("challenged", challenged)
        .put("require_owner", require_owner)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "prove_authority", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("prove_authority", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }

}
