package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String parentAuthor = RandomStringUtils.randomAlphanumeric(8);
    String parentPermlink = RandomStringUtils.randomAlphanumeric(8);
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);
    String title = RandomStringUtils.randomAlphanumeric(8);
    String body = RandomStringUtils.randomAlphanumeric(8);
    String jsonMetadata = RandomStringUtils.randomAlphanumeric(8);

    CommentOperation sut = CommentOperation.builder()
        .parentAuthor(parentAuthor)
        .parentPermlink(parentPermlink)
        .author(author)
        .permlink(permlink)
        .title(title)
        .body(body)
        .jsonMetadata(jsonMetadata)
        .build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("parent_author", parentAuthor)
        .put("parent_permlink", parentPermlink)
        .put("author", author)
        .put("permlink", permlink)
        .put("title", title)
        .put("body", body)
        .put("json_metadata", jsonMetadata)
        .build();

    assertThat(actual).containsExactly("comment_operation", expectedMap);
  }

  @Test
  public void toAppbase() {
    // set up
    String parentAuthor = RandomStringUtils.randomAlphanumeric(8);
    String parentPermlink = RandomStringUtils.randomAlphanumeric(8);
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);
    String title = RandomStringUtils.randomAlphanumeric(8);
    String body = RandomStringUtils.randomAlphanumeric(8);
    String jsonMetadata = RandomStringUtils.randomAlphanumeric(8);

    CommentOperation sut = CommentOperation.builder()
        .parentAuthor(parentAuthor)
        .parentPermlink(parentPermlink)
        .author(author)
        .permlink(permlink)
        .title(title)
        .body(body)
        .jsonMetadata(jsonMetadata)
        .build();

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("parent_author", parentAuthor)
        .put("parent_permlink", parentPermlink)
        .put("author", author)
        .put("permlink", permlink)
        .put("title", title)
        .put("body", body)
        .put("json_metadata", jsonMetadata)
        .build();
    Map<String, Object> expected
        = ImmutableMap.of("type", "comment_operation", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }
}
