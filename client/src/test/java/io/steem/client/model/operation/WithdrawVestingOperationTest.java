package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WithdrawVestingOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String account = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset vestingShares = OperationTestUtils.randomSteemAsset();

    WithdrawVestingOperation.Value value = WithdrawVestingOperation.Value.builder()
        .account(account)
        .vestingShares(vestingShares)
        .build();
    WithdrawVestingOperation sut = WithdrawVestingOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("account", account)
        .put("vesting_shares", ObjectMapUtils.toObjectMap(vestingShares))
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "withdraw_vesting", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("withdraw_vesting", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
