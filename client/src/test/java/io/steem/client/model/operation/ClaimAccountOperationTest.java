package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemAsset;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ClaimAccountOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String creator = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    List<FutureExtensions> extensions = ImmutableList.of(new FutureExtensions());

    ClaimAccountOperation sut
        = ClaimAccountOperation.builder().creator(creator).fee(fee).extensions(extensions).build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("creator", creator)
        .put("fee", fee)
        .put("extensions", extensions)
        .build();

    assertThat(actual).containsExactly("claim_account", expectedMap);
  }

  @Test
  public void toAppbase() {
    // set up
    String creator = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    List<FutureExtensions> extensions = ImmutableList.of(new FutureExtensions());

    ClaimAccountOperation sut
        = ClaimAccountOperation.builder().creator(creator).fee(fee).extensions(extensions).build();

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("creator", creator)
        .put("fee", fee)
        .put("extensions", extensions)
        .build();
    Map<String, Object> expected = ImmutableMap.of("type", "claim_account", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }
}
