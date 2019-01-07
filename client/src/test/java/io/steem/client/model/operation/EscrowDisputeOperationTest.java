package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EscrowDisputeOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    String agent = RandomStringUtils.randomAlphanumeric(8);
    String who = RandomStringUtils.randomAlphanumeric(8);
    long escrowId = RandomUtils.nextLong();

    EscrowDisputeOperation.Value value = EscrowDisputeOperation.Value.builder()
        .from(from)
        .to(to)
        .agent(agent)
        .who(who)
        .escrowId(escrowId)
        .build();
    EscrowDisputeOperation sut = EscrowDisputeOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from", from)
        .put("to", to)
        .put("agent", agent)
        .put("who", who)
        .put("escrow_id", escrowId)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "escrow_dispute", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("escrow_dispute", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
