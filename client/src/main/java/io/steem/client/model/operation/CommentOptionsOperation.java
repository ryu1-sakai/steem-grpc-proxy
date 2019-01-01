package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.CommentOptionsExtension;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.SteemPercent;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@ToString
public class CommentOptionsOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String author;
    private final String permlink;
    private final SteemAsset maxExpectedPayout;
    private final SteemPercent percentSteemDollers;
    private final boolean allowVotes;
    private final boolean allowCurationRewards;
    private final Set<CommentOptionsExtension> extensions;

    public static class ValueBuilder {
      private boolean allowVotes = true; // default is true
      private boolean allowCurationRewards = true; // default is true
      private Set<CommentOptionsExtension> extensions = Collections.emptySet();
    }
  }

  private final Value value;

  private CommentOptionsOperation(Value value) {
    super("comment_options");
    this.value = value;
  }

  public static CommentOptionsOperation of(Value value) {
    return new CommentOptionsOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
