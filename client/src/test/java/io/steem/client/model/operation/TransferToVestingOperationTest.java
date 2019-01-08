package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferToVestingOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset amount = OperationTestUtils.randomSteemAsset();

    TransferToVestingOperation.Value value = TransferToVestingOperation.Value.builder()
        .from(from)
        .to(to)
        .amount(amount)
        .build();
    TransferToVestingOperation sut = TransferToVestingOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from", from)
        .put("to", to)
        .put("amount", ObjectMapUtils.toObjectMap(amount))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "transfer_to_vesting", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("transfer_to_vesting", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
