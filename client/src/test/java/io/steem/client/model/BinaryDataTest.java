package io.steem.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.Value;
import lombok.experimental.Accessors;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class BinaryDataTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Accessors(fluent = true)
  @Value(staticConstructor = "of")
  private static class TestSuite {
    private final byte[] bytes;
    private final String string;
  }

  @DataPoints
  public static final List<TestSuite> TEST_SUITES = ImmutableList.of(
      TestSuite.of(new byte[]{}, ""),
      TestSuite.of(new byte[]{0x01}, "01"),
      TestSuite.of(new byte[]{0x00, 0x01, 0x02, (byte) 0xDF, (byte) 0xEF, (byte) 0xFF},
          "000102dfefff")
  );

  @Theory
  public void serialize(TestSuite testSuite) {
    // set up
    BinaryData sut = BinaryData.of(testSuite.bytes());

    // exercise
    String actual = OBJECT_MAPPER.convertValue(sut, String.class);

    // verify
    assertThat(actual).isEqualTo(testSuite.string());
  }
}
