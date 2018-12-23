package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemAuthority;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountUpdateOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String accountName = RandomStringUtils.randomAlphabetic(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);

    AccountUpdateOperation sut = AccountUpdateOperation.builder()
        .accountName(accountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("new_account_name", accountName)
        .put("owner", owner.compose())
        .put("active", active.compose())
        .put("posting", posting.compose())
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .build();

    assertThat(actual).containsExactly("account_create", params);
  }

  @Test
  public void toCondenser_empty() {
    // set up
    AccountUpdateOperation sut = AccountUpdateOperation.builder().build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    assertThat(actual).containsExactly("account_create", Collections.emptyMap());
  }

  @Test
  public void toAppbase() {
    // set up
    String accountName = RandomStringUtils.randomAlphabetic(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);

    AccountUpdateOperation sut = AccountUpdateOperation.builder()
        .accountName(accountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .build();

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> params = ImmutableMap.<String, Object>builder()
        .put("new_account_name", accountName)
        .put("owner", owner.compose())
        .put("active", active.compose())
        .put("posting", posting.compose())
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .build();
    Map<String, Object> expected
        = ImmutableMap.of("type", "account_create", "value", params);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void toAppbase_empty() {
    // set up
    AccountUpdateOperation sut = AccountUpdateOperation.builder().build();

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> expected
        = ImmutableMap.of("type", "account_create", "value", Collections.emptyMap());

    assertThat(actual).isEqualTo(expected);
  }
}
