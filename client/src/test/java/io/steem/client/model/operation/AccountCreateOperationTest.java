package io.steem.client.model.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.SortedMap;

public class AccountCreateOperationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void toCondenser() {
    // set up
    SteemAsset fee = randomSteemAsset();
    String creator = RandomStringUtils.randomAlphabetic(8);
    String newAccountName = RandomStringUtils.randomAlphabetic(8);
    SteemAuthority owner = randomSteemAuthority();
    SteemAuthority active = randomSteemAuthority();
    SteemAuthority posting = randomSteemAuthority();
    String memoKey = RandomStringUtils.randomAlphabetic(8);
    String jsonMetadata = RandomStringUtils.randomAlphabetic(8);

    AccountCreateOperation sut = AccountCreateOperation.builder()
        .fee(fee)
        .creator(creator)
        .newAccountName(newAccountName)
        .owner(owner)
        .active(active)
        .posting(posting)
        .memoKey(memoKey)
        .jsonMetadata(jsonMetadata)
        .build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    List<Object> expected = ImmutableList.builder()
        .add(ObjectMapUtils.toObjectMap(fee))
        .add(creator)

  }

  private static SteemAsset randomSteemAsset() {
    return new SteemAsset(RandomUtils.nextLong(), RandomUtils.nextInt(),
        RandomStringUtils.randomAlphabetic(8));
  }

  private static SteemAuthority randomSteemAuthority() {
    SortedMap<String, Integer> accountAuths = ImmutableSortedMap.of(
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt(),
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt());
    SortedMap<String, Integer> keyAuths = ImmutableSortedMap.of(
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt(),
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt());
    return new SteemAuthority(RandomUtils.nextLong(), accountAuths, keyAuths);
  }
}
