package io.steem.client.model;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SteemOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String type = RandomStringUtils.randomAlphabetic(8);
    Map<String, Object> value = randomMap();

    SteemOperation sut = new SteemOperation(type, value);

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    assertThat(actual).containsExactly(type, value);
  }

  @Test
  public void toAppbase() {
    // set up
    String type = RandomStringUtils.randomAlphabetic(8);
    Map<String, Object> value = randomMap();

    SteemOperation sut = new SteemOperation(type, value);

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    assertThat(actual).containsExactly(entry("type", type), entry("value", value));
  }

  private static Map<String, Object> randomMap() {
    //noinspection UnstableApiUsage
    return Stream
        .generate(() -> new AbstractMap.SimpleEntry<String, Object>(
            RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8)))
        .limit(8)
        .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private static Map.Entry<String, Object> entry(String key, Object value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }
}
