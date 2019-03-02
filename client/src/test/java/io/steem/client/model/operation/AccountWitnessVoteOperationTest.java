package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountWitnessVoteOperationTest {

  @Test
  public void toCondenserAndCondenser() {
    // set up
    String account = RandomStringUtils.randomAlphanumeric(8);
    String witness = RandomStringUtils.randomAlphanumeric(8);
    boolean approve = RandomUtils.nextBoolean();

    AccountWitnessVoteOperation.Value value = AccountWitnessVoteOperation.Value.builder()
        .account(account)
        .witness(witness)
        .approve(approve)
        .build();
    AccountWitnessVoteOperation sut = AccountWitnessVoteOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("account", account)
        .put("witness", witness)
        .put("approve", approve)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "account_witness_vote", "value", params);

    assertThat(actualCondenser).containsExactly("account_witness_vote", params);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
