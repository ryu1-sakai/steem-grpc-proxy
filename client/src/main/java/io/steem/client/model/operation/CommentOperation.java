package io.steem.client.model.operation;

import com.google.common.collect.ImmutableMap;
import io.steem.client.model.SteemOperation;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

@Builder
@ToString
public class CommentOperation extends SteemOperation {

  private final String parentAuthor;
  private final String parentPermlink;
  private final String author;
  private final String permlink;
  private final String title;
  private final String body;
  private final String jsonMetadata;

  private CommentOperation(String parentAuthor, String parentPermlink, String author,
                           String permlink, String title, String body, String jsonMetadata) {
    super("comment_operation");
    this.parentAuthor = parentAuthor;
    this.parentPermlink = parentPermlink;
    this.author = author;
    this.permlink = permlink;
    this.title = title;
    this.body = body;
    this.jsonMetadata = jsonMetadata;
  }

  @Override
  protected Map<String, Object> getValueMap() {
    ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
    Optional.ofNullable(parentAuthor).ifPresent(p -> builder.put("parent_author", p));
    Optional.ofNullable(parentPermlink).ifPresent(p -> builder.put("parent_permlink", p));
    Optional.ofNullable(author).ifPresent(a -> builder.put("author", a));
    Optional.ofNullable(permlink).ifPresent(p -> builder.put("permlink", p));
    Optional.ofNullable(title).ifPresent(t -> builder.put("title", t));
    Optional.ofNullable(body).ifPresent(b -> builder.put("body", b));
    Optional.ofNullable(jsonMetadata).ifPresent(j -> builder.put("json_metadata", j));
    return builder.build();
  }
}
