package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
public class CommentOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String parentAuthor;
    private final String parentPermlink;
    private final String author;
    private final String permlink;
    private final String title;
    private final String body;
    private final String jsonMetadata;
  }

  private final Value value;

  private CommentOperation(Value value) {
    super("comment_operation");
    this.value = value;
  }

  public static CommentOperation of(Value value) {
    return new CommentOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
