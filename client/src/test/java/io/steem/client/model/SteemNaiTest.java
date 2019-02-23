package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class SteemNaiTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Accessors(fluent = true)
  @Value(staticConstructor = "of")
  private static class SerializationTestSuite {
    private SteemNai steemNai;
    private String expected;
  }

  @DataPoints
  public static final List<SerializationTestSuite> SERIALIZATION_TEST_SUITES = ImmutableList.of(
      SerializationTestSuite.of(SteemNai.SBD, "@@000000013"),
      SerializationTestSuite.of(SteemNai.STEEM, "@@000000021"),
      SerializationTestSuite.of(SteemNai.VESTS, "@@000000037")
  );

  @Theory
  public void serialize(SerializationTestSuite testSuite) {
    assertThat(OBJECT_MAPPER.convertValue(testSuite.steemNai(), String.class))
        .isEqualTo(testSuite.expected());
  }
}
