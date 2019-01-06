package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ClaimAccountOperationTest {

  @Test
  public void toCondenserAndCondenser() {
    // set up
    String creator = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    List<FutureExtensions> extensions = ImmutableList.of(new FutureExtensions());

    ClaimAccountOperation.Value value = ClaimAccountOperation.Value.builder()
        .creator(creator)
        .fee(fee)
        .extensions(extensions)
        .build();
    ClaimAccountOperation sut = ClaimAccountOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    @SuppressWarnings("UnstableApiUsage")
    List<Map<String, Object>> extensionMaps = extensions.stream()
        .map(ObjectMapUtils::toObjectMap)
        .collect(ImmutableList.toImmutableList());
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("creator", creator)
        .put("fee", ObjectMapUtils.toObjectMap(fee))
        .put("extensions", extensionMaps)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "claim_account", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("claim_account", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
