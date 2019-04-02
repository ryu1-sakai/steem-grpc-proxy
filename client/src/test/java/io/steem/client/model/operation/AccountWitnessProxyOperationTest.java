package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountWitnessProxyOperationTest {

  @Test
  public void toCondenserAndCondenser() {
    // set up
    String account = RandomStringUtils.randomAlphanumeric(8);
    String proxy = RandomStringUtils.randomAlphanumeric(8);

    AccountWitnessProxyOperation.Value value = AccountWitnessProxyOperation.Value.builder()
        .account(account)
        .proxy(proxy)
        .build();
    AccountWitnessProxyOperation sut = AccountWitnessProxyOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("account", account)
        .put("proxy", proxy)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "account_witness_proxy", "value", params);

    assertThat(actualCondenser).containsExactly("account_witness_proxy", params);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
