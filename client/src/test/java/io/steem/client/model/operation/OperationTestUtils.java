package io.steem.client.model.operation;

import com.google.common.collect.ImmutableSortedMap;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemAuthority;
import io.steem.client.model.SteemChainProperties;
import io.steem.client.model.SteemNai;
import io.steem.client.model.SteemPercent;
import io.steem.client.model.SteemTime;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Instant;
import java.util.SortedMap;

public final class OperationTestUtils {

  private OperationTestUtils() {}

  public static SteemAsset randomSteemAsset() {
    return new SteemAsset(RandomUtils.nextLong(), RandomUtils.nextInt(),
        randomEnum(SteemNai.class));
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

  public static SteemTime randomSteemTime() {
    long seconds = RandomUtils.nextLong(0, SteemTime.MAX_INSTANT.getEpochSecond() + 1);
    return SteemTime.of(Instant.ofEpochSecond(seconds));
  }

  public static SteemChainProperties randomSteemChainProperties() {
    SteemAsset accountCreationFee = randomSteemAsset();
    int maximumBlockSize = RandomUtils.nextInt();
    SteemPercent sbdInterestRate = SteemPercent.of(RandomUtils.nextInt(0, 100),
        RandomUtils.nextInt(0, 100));
    return new SteemChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);
  }

  public static SteemPercent randomSteemPercent() {
    int protocolValue = RandomUtils.nextInt(0, SteemPercent.MAX_PROTOCOL_VALUE + 1);
    return SteemPercent.of(protocolValue / 100, protocolValue % 100);
  }

  private static <E> E randomEnum(Class<E> enumClass) {
    E[] enumValues = enumClass.getEnumConstants();
    return enumValues[RandomUtils.nextInt(0, enumValues.length)];
  }
}
