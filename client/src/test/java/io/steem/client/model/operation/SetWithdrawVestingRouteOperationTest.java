package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SetWithdrawVestingRouteOperationTest {

  @Test
  public void toCondenserAndAppbase() {
    // set up
    String fromAccount = RandomStringUtils.randomAlphanumeric(8);
    String toAccount = RandomStringUtils.randomAlphanumeric(8);
    int percent = RandomUtils.nextInt();
    boolean autoVest = RandomUtils.nextBoolean();

    SetWithdrawVestingRouteOperation.Value value = SetWithdrawVestingRouteOperation.Value.builder()
        .fromAccount(fromAccount)
        .toAccount(toAccount)
        .percent(percent)
        .autoVest(autoVest)
        .build();
    SetWithdrawVestingRouteOperation sut = SetWithdrawVestingRouteOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("from_account", fromAccount)
        .put("to_account", toAccount)
        .put("percent" ,percent)
        .put("auto_vest", autoVest)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "set_withdraw_vesting_route", "value", expectedMap);

    assertThat(actualCondenser).containsExactly("set_withdraw_vesting_route", expectedMap);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
