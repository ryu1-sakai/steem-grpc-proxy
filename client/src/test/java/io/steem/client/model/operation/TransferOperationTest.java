package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset amount = OperationTestUtils.randomSteemAsset();
    String memo = RandomStringUtils.randomAlphanumeric(8);

    TransferOperation.Value value = TransferOperation.Value.builder()
        .from(from)
        .to(to)
        .amount(amount)
        .memo(memo)
        .build();
    TransferOperation sut = TransferOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from", from)
        .put("to", to)
        .put("amount", ObjectMapUtils.toObjectMap(amount))
        .put("memo", memo)
        .build();
    Map<String, Object> expectedAppbase = ImmutableMap.of("type", "transfer", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("transfer", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
