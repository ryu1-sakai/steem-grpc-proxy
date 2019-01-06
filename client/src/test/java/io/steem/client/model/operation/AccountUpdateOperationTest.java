package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountUpdateOperationTest {

  @Test
  public void toCondenserAndCondenser() {
    // set up
    String account = RandomStringUtils.randomAlphabetic(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);

    AccountUpdateOperation.Value value = AccountUpdateOperation.Value.builder()
        .account(account)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .build();
    AccountUpdateOperation sut = AccountUpdateOperation.of(value);

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("account", account)
        .put("owner", ObjectMapUtils.toObjectMap(owner))
        .put("active", ObjectMapUtils.toObjectMap(active))
        .put("posting", ObjectMapUtils.toObjectMap(posting))
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .build();
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "account_update", "value", params);

    assertThat(actualCondenser).containsExactly("account_update", params);
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }

  @Test
  public void toCondenserAndAppbase_empty() {
    // set up
    AccountUpdateOperation sut
        = AccountUpdateOperation.of(AccountUpdateOperation.Value.builder().build());

    // exercise
    List<Object> actualCondenser = sut.toCondenser();
    Map<String, Object> actualAppbase = sut.toAppbase();

    // verify
    Map<String, Object> expectedAppbase
        = ImmutableMap.of("type", "account_update", "value", Collections.emptyMap());

    assertThat(actualCondenser).containsExactly("account_update", Collections.emptyMap());
    assertThat(actualAppbase).isEqualTo(expectedAppbase);
  }
}
