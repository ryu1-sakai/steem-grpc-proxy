package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteCommentOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);

    DeleteCommentOperation.Value value = DeleteCommentOperation.Value.builder()
        .author(author)
        .permlink(permlink)
        .build();
    DeleteCommentOperation sut = DeleteCommentOperation.of(value);

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("author", author)
        .put("permlink", permlink)
        .build();

    assertThat(actual).containsExactly("delete_comment", expectedMap);
  }

  @Test
  public void toAppbase() {
    // set up
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);

    DeleteCommentOperation.Value value = DeleteCommentOperation.Value.builder()
        .author(author)
        .permlink(permlink)
        .build();
    DeleteCommentOperation sut = DeleteCommentOperation.of(value);

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("author", author)
        .put("permlink", permlink)
        .build();
    Map<String, Object> expected = ImmutableMap.of("type", "delete_comment", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }
}
