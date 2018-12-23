package io.steem.client.model.operation;

import com.google.common.collect.ImmutableSortedMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemAuthority;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.SortedMap;

public final class OperationTestUtils {

  private OperationTestUtils() {}

  public static SteemAsset randomSteemAsset() {
    return new SteemAsset(RandomUtils.nextLong(), RandomUtils.nextInt(),
        RandomStringUtils.randomAlphabetic(8));
  }

  public static SteemAuthority randomSteemAuthority() {
    SortedMap<String, Integer> accountAuths = ImmutableSortedMap.of(
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt(),
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt());
    SortedMap<String, Integer> keyAuths = ImmutableSortedMap.of(
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt(),
        RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt());
    return new SteemAuthority(RandomUtils.nextLong(), accountAuths, keyAuths);
  }
}
