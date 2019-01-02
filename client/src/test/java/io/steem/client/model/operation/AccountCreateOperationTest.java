package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountCreateOperationTest {

  @Test
  public void toCondenser() {
    // set up
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    String creator = RandomStringUtils.randomAlphabetic(8);
    String newAccountName = RandomStringUtils.randomAlphabetic(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);

    AccountCreateOperation.Value value = AccountCreateOperation.Value.builder()
        .fee(fee)
        .creator(creator)
        .newAccountName(newAccountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .build();
    AccountCreateOperation sut = AccountCreateOperation.of(value);

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("fee", ObjectMapUtils.toObjectMap(fee))
        .put("creator", creator)
        .put("new_account_name", newAccountName)
        .put("owner", ObjectMapUtils.toObjectMap(owner))
        .put("active", ObjectMapUtils.toObjectMap(active))
        .put("posting", ObjectMapUtils.toObjectMap(posting))
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .build();

    assertThat(actual).containsExactly("account_create", params);
  }

  @Test
  public void toAppbase() {
    // set up
    SteemAsset fee = OperationTestUtils.randomSteemAsset();
    String creator = RandomStringUtils.randomAlphabetic(8);
    String newAccountName = RandomStringUtils.randomAlphabetic(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);

    AccountCreateOperation.Value value = AccountCreateOperation.Value.builder()
        .fee(fee)
        .creator(creator)
        .newAccountName(newAccountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .build();
    AccountCreateOperation sut = AccountCreateOperation.of(value);

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("fee", ObjectMapUtils.toObjectMap(fee))
        .put("creator", creator)
        .put("new_account_name", newAccountName)
        .put("owner", ObjectMapUtils.toObjectMap(owner))
        .put("active", ObjectMapUtils.toObjectMap(active))
        .put("posting", ObjectMapUtils.toObjectMap(posting))
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .build();
    Map<String, Object> expected = ImmutableMap.of("type", "account_create", "value", params);

    assertThat(actual).isEqualTo(expected);
  }
}
