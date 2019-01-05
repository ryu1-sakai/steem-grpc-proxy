package io.steem.client.model.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemTime;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EscrowTransferOperationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void toCondenser() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    String agent = RandomStringUtils.randomAlphanumeric(8);
    long escrowId = RandomUtils.nextLong();
    SteemAsset sbdAmount = OperationTestUtils.randomSteemAsset();
    SteemAsset steemAmount = OperationTestUtils.randomSteemAsset();
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    SteemTime ratificationDeadline = OperationTestUtils.randomSteemTime();
    SteemTime escrowExpiration = OperationTestUtils.randomSteemTime();

    EscrowTransferOperation.Value value = EscrowTransferOperation.Value.builder()
        .from(from)
        .to(to)
        .agent(agent)
        .escrowId(escrowId)
        .sbdAmount(sbdAmount)
        .steemAmount(steemAmount)
        .fee(fee)
        .ratificationDeadline(ratificationDeadline)
        .escrowExpiration(escrowExpiration)
        .build();
    EscrowTransferOperation sut = EscrowTransferOperation.of(value);

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from", from)
        .put("to", to)
        .put("agent", agent)
        .put("escrow_id", escrowId)
        .put("sbd_amount", ObjectMapUtils.toObjectMap(sbdAmount))
        .put("steem_amount", ObjectMapUtils.toObjectMap(steemAmount))
        .put("fee", ObjectMapUtils.toObjectMap(fee))
        .put("ratification_deadline",
            OBJECT_MAPPER.convertValue(ratificationDeadline, String.class))
        .put("escrow_expiration", OBJECT_MAPPER.convertValue(escrowExpiration, String.class))
        .build();

    assertThat(actual).containsExactly("escrow_transfer", expectedMap);
  }

  @Test
  public void toAppbase() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    String agent = RandomStringUtils.randomAlphanumeric(8);
    long escrowId = RandomUtils.nextLong();
    SteemAsset sbdAmount = OperationTestUtils.randomSteemAsset();
    SteemAsset steemAmount = OperationTestUtils.randomSteemAsset();
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    SteemTime ratificationDeadline = OperationTestUtils.randomSteemTime();
    SteemTime escrowExpiration = OperationTestUtils.randomSteemTime();

    EscrowTransferOperation.Value value = EscrowTransferOperation.Value.builder()
        .from(from)
        .to(to)
        .agent(agent)
        .escrowId(escrowId)
        .sbdAmount(sbdAmount)
        .steemAmount(steemAmount)
        .fee(fee)
        .ratificationDeadline(ratificationDeadline)
        .escrowExpiration(escrowExpiration)
        .build();
    EscrowTransferOperation sut = EscrowTransferOperation.of(value);

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from", from)
        .put("to", to)
        .put("agent", agent)
        .put("escrow_id", escrowId)
        .put("sbd_amount", ObjectMapUtils.toObjectMap(sbdAmount))
        .put("steem_amount", ObjectMapUtils.toObjectMap(steemAmount))
        .put("fee", ObjectMapUtils.toObjectMap(fee))
        .put("ratification_deadline",
            OBJECT_MAPPER.convertValue(ratificationDeadline, String.class))
        .put("escrow_expiration", OBJECT_MAPPER.convertValue(escrowExpiration, String.class))
        .build();
    Map<String, Object> expected = ImmutableMap.of("type", "escrow_transfer", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }
}
