package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemChainProperties;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WitnessUpdateOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String owner = RandomStringUtils.randomAlphanumeric(8);
    String url = RandomStringUtils.randomAlphanumeric(8);
    String blockSigningKey = RandomStringUtils.randomAlphanumeric(8);
    SteemChainProperties props = OperationTestUtils.randomSteemChainProperties();
    SteemAsset fee = OperationTestUtils.randomSteemAsset();

    WitnessUpdateOperation.Value value = WitnessUpdateOperation.Value.builder()
        .owner(owner)
        .url(url)
        .blockSigningKey(blockSigningKey)
        .props(props)
        .fee(fee)
        .build();
    WitnessUpdateOperation sut = WitnessUpdateOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("owner", owner)
        .put("url", url)
        .put("block_signing_key", blockSigningKey)
        .put("props", ObjectMapUtils.toObjectMap(props))
        .put("fee", ObjectMapUtils.toObjectMap(fee))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "witness_update", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("witness_update", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
