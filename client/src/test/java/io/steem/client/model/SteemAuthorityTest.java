package io.steem.client.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SteemAuthorityTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final JavaType MAP_TYPE
      = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, Object.class);

  @Test
  public void toObjectMap() {
    // set up
    long weightThreshold = RandomUtils.nextLong(0, Integer.MAX_VALUE * 2L) + 1; // uint32
    SortedMap<String, Integer> accountAuths = randomSortedMap();
    SortedMap<String, Integer> keyAuths = randomSortedMap();

    SteemAuthority sut = new SteemAuthority(weightThreshold, accountAuths, keyAuths);

    // exercise
    Map<String, Object> actual = OBJECT_MAPPER.convertValue(sut, MAP_TYPE);

    // verify
    @SuppressWarnings("UnstableApiUsage")
    List<List<Object>> expectedAccountAuths = accountAuths.entrySet().stream()
        .map(e -> ImmutableList.<Object>of(e.getKey(), e.getValue()))
        .collect(ImmutableList.toImmutableList());
    @SuppressWarnings("UnstableApiUsage")
    List<List<Object>> expectedKeyAuths = keyAuths.entrySet().stream()
        .map(e -> ImmutableList.<Object>of(e.getKey(), e.getValue()))
        .collect(ImmutableList.toImmutableList());
    Map<String, Object> expected = ImmutableMap.<String, Object>builder()
        .put("weight_threshold", weightThreshold)
        .put("account_auths", expectedAccountAuths)
        .put("key_auths", expectedKeyAuths)
        .build();

    assertThat(actual).isEqualTo(expected);
  }

  private static SortedMap<String, Integer> randomSortedMap() {
    //noinspection UnstableApiUsage
    return Stream
        .generate(() -> new AbstractMap.SimpleEntry<>(
            RandomStringUtils.randomAlphabetic(8), RandomUtils.nextInt()))
        .limit(8)
        .collect(ImmutableSortedMap.toImmutableSortedMap(
            Comparator.naturalOrder(), Map.Entry::getKey, Map.Entry::getValue));
  }
}
