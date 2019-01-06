package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EscrowApproveOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String from = RandomStringUtils.randomAlphanumeric(8);
    String to = RandomStringUtils.randomAlphanumeric(8);
    String agent = RandomStringUtils.randomAlphanumeric(8);
    String who = RandomStringUtils.randomAlphanumeric(8);
    long escrowId = RandomUtils.nextLong();
    boolean approve = RandomUtils.nextBoolean();

    EscrowApproveOperation.Value value = EscrowApproveOperation.Value.builder()
        .from(from)
        .to(to)
        .agent(agent)
        .who(who)
        .escrowId(escrowId)
        .approve(approve)
        .build();
    EscrowApproveOperation sut = EscrowApproveOperation.of(value);

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
        .put("approve", approve)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "escrow_approve", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("escrow_approve", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }

}
