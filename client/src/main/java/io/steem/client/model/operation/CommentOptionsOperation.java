package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.CommentOptionsExtension;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.SteemPercent;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Builder
@ToString
public class CommentOptionsOperation extends SteemOperation {

  public static class CommentOptionsOperationBuilder {
    private boolean allowVotes = true; // default is true
    private boolean allowCurationRewards = true; // default is true
  }

  private final String author;
  private final String permlink;
  private final SteemAsset maxExpectedPayout;
  private final SteemPercent percentSteemDollers;
  private final boolean allowVotes;
  private final boolean allowCurationRewards;
  private final Set<CommentOptionsExtension> extensions;

  private CommentOptionsOperation(String author, String permlink, SteemAsset maxExpectedPayout,
                                  SteemPercent percentSteemDollers, boolean allowVotes,
                                  boolean allowCurationRewards,
                                  Set<CommentOptionsExtension> extensions) {
    super("comment_options");
    this.author = author;
    this.permlink = permlink;
    this.maxExpectedPayout = maxExpectedPayout;
    this.percentSteemDollers = percentSteemDollers;
    this.allowVotes = allowVotes;
    this.allowCurationRewards = allowCurationRewards;
    this.extensions = extensions != null ? extensions : Collections.emptySet();
  }

  @Override
  protected Map<String, Object> getValueMap() {
    ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
    Optional.ofNullable(author).ifPresent(a -> builder.put("author", a));
    Optional.ofNullable(permlink).ifPresent(p -> builder.put("permlink", p));
    Optional.ofNullable(maxExpectedPayout)
        .ifPresent(m -> builder.put("max_expected_payout", ObjectMapUtils.toObjectMap(m)));
    Optional.ofNullable(percentSteemDollers)
        .ifPresent(p -> builder.put("percent_steem_dollers", p.toProtocolValue()));
    builder.put("allow_votes", allowVotes);
    builder.put("allow_curation_rewards", allowCurationRewards);
    @SuppressWarnings("UnstableApiUsage")
    List<String> composedExtensions = extensions.stream()
        .map(CommentOptionsExtension::toProtocol).collect(ImmutableList.toImmutableList());
    builder.put("extensions", composedExtensions);
    return builder.build();
  }
}
