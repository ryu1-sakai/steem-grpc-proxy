package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateClaimedAccountOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String creator = RandomStringUtils.randomAlphanumeric(8);
    String newAccountName = RandomStringUtils.randomAlphanumeric(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);
    List<FutureExtensions> extensions = ImmutableList.of(new FutureExtensions());

    CreateClaimedAccountOperation.Value value = CreateClaimedAccountOperation.Value.builder()
        .creator(creator)
        .newAccountName(newAccountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .extensions(extensions)
        .build();
    CreateClaimedAccountOperation sut = CreateClaimedAccountOperation.of(value);

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    @SuppressWarnings("UnstableApiUsage")
    List<Map<String, Object>> extensionMaps = extensions.stream()
        .map(ObjectMapUtils::toObjectMap)
        .collect(ImmutableList.toImmutableList());
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("creator", creator)
        .put("new_account_name", newAccountName)
        .put("owner", ObjectMapUtils.toObjectMap(owner))
        .put("active", ObjectMapUtils.toObjectMap(active))
        .put("posting", ObjectMapUtils.toObjectMap(posting))
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .put("extensions", extensionMaps)
        .build();

    assertThat(actual).containsExactly("create_claimed_account", expectedMap);
  }

  @Test
  public void toAppbase() {
    // set up
    String creator = RandomStringUtils.randomAlphanumeric(8);
    String newAccountName = RandomStringUtils.randomAlphanumeric(8);
    SteemAuthority owner = OperationTestUtils.randomSteemAuthority();
    SteemAuthority active = OperationTestUtils.randomSteemAuthority();
    SteemAuthority posting = OperationTestUtils.randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);
    List<FutureExtensions> extensions = ImmutableList.of(new FutureExtensions());

    CreateClaimedAccountOperation.Value value = CreateClaimedAccountOperation.Value.builder()
        .creator(creator)
        .newAccountName(newAccountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .extensions(extensions)
        .build();
    CreateClaimedAccountOperation sut = CreateClaimedAccountOperation.of(value);

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    @SuppressWarnings("UnstableApiUsage")
    List<Map<String, Object>> extensionMaps = extensions.stream()
        .map(ObjectMapUtils::toObjectMap)
        .collect(ImmutableList.toImmutableList());
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("creator", creator)
        .put("new_account_name", newAccountName)
        .put("owner", ObjectMapUtils.toObjectMap(owner))
        .put("active", ObjectMapUtils.toObjectMap(active))
        .put("posting", ObjectMapUtils.toObjectMap(posting))
        .put("memo_key", memoKey)
        .put("json_metadata", jsonMetadata)
        .put("extensions", extensionMaps)
        .build();
    Map<String, Object> expected
        = ImmutableMap.of("type", "create_claimed_account", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }
}
