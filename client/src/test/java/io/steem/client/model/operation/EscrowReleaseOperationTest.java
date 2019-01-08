package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EscrowReleaseOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    String agent = RandomStringUtils.randomAlphanumeric(8);
    String who = RandomStringUtils.randomAlphanumeric(8);
    String receiver = RandomStringUtils.randomAlphanumeric(8);
    long escrowId = RandomUtils.nextLong();
    SteemAsset sbdAmount = OperationTestUtils.randomSteemAsset();
    SteemAsset steemAmount = OperationTestUtils.randomSteemAsset();

    EscrowReleaseOperation.Value value = EscrowReleaseOperation.Value.builder()
        .from(from)
        .to(to)
        .agent(agent)
        .who(who)
        .receiver(receiver)
        .escrowId(escrowId)
        .sbdAmount(sbdAmount)
        .steemAmount(steemAmount)
        .build();
    EscrowReleaseOperation sut = EscrowReleaseOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from", from)
        .put("to", to)
        .put("agent", agent)
        .put("who", who)
        .put("receiver", receiver)
        .put("escrow_id", escrowId)
        .put("sbd_amount", ObjectMapUtils.toObjectMap(sbdAmount))
        .put("steem_amount", ObjectMapUtils.toObjectMap(steemAmount))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "escrow_release", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("escrow_release", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
