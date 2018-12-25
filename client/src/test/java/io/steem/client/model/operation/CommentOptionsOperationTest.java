package io.steem.client.model.operation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.steem.client.model.CommentOptionsExtension;
import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemPercent;
import io.steem.client.model.util.ObjectMapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentOptionsOperationTest {

  @Test
  public void toCondenser() {
    // set up
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset maxExpectedPayout = OperationTestUtils.randomSteemAsset();
    SteemPercent percentSteemDollers
        = SteemPercent.of(RandomUtils.nextInt(0, 100), RandomUtils.nextInt(0, 100));
    boolean allowVotes = RandomUtils.nextBoolean();
    boolean allowCurationRewards = RandomUtils.nextBoolean();
    Set<CommentOptionsExtension> extensions
        = ImmutableSet.of(CommentOptionsExtension.ALLOWED_VOTE_ASSETS,
        CommentOptionsExtension.COMMENT_PAYOUT_BENEFICIARIES);

    CommentOptionsOperation sut = CommentOptionsOperation.builder()
        .author(author)
        .permlink(permlink)
        .maxExpectedPayout(maxExpectedPayout)
        .percentSteemDollers(percentSteemDollers)
        .allowVotes(allowVotes)
        .allowCurationRewards(allowCurationRewards)
        .extensions(extensions)
        .build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    List<String> expectedExtensions = ImmutableList.of(
        CommentOptionsExtension.ALLOWED_VOTE_ASSETS.toProtocol(),
        CommentOptionsExtension.COMMENT_PAYOUT_BENEFICIARIES.toProtocol());
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("author", author)
        .put("permlink", permlink)
        .put("max_expected_payout", ObjectMapUtils.toObjectMap(maxExpectedPayout))
        .put("percent_steem_dollers", percentSteemDollers.toProtocolValue())
        .put("allow_votes", allowVotes)
        .put("allow_curation_rewards", allowCurationRewards)
        .put("extensions", expectedExtensions)
        .build();

    assertThat(actual).containsExactly("comment_options", expectedMap);
  }

  @Test
  public void toCondenser_empty() {
    // set up
    CommentOptionsOperation sut = CommentOptionsOperation.builder().build();

    // exercise
    List<Object> actual = sut.toCondenser();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("allow_votes", true) // default value
        .put("allow_curation_rewards", true) // default value
        .put("extensions", Collections.emptyList())
        .build();

    assertThat(actual).containsExactly("comment_options", expectedMap);
  }

  @Test
  public void toAppbase() {
    // set up
    String author = RandomStringUtils.randomAlphanumeric(8);
    String permlink = RandomStringUtils.randomAlphanumeric(8);
    SteemAsset maxExpectedPayout = OperationTestUtils.randomSteemAsset();
    SteemPercent percentSteemDollers
        = SteemPercent.of(RandomUtils.nextInt(0, 100), RandomUtils.nextInt(0, 100));
    boolean allowVotes = RandomUtils.nextBoolean();
    boolean allowCurationRewards = RandomUtils.nextBoolean();
    Set<CommentOptionsExtension> extensions
        = ImmutableSet.of(CommentOptionsExtension.ALLOWED_VOTE_ASSETS,
        CommentOptionsExtension.COMMENT_PAYOUT_BENEFICIARIES);

    CommentOptionsOperation sut = CommentOptionsOperation.builder()
        .author(author)
        .permlink(permlink)
        .maxExpectedPayout(maxExpectedPayout)
        .percentSteemDollers(percentSteemDollers)
        .allowVotes(allowVotes)
        .allowCurationRewards(allowCurationRewards)
        .extensions(extensions)
        .build();

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    List<String> expectedExtensions = ImmutableList.of(
        CommentOptionsExtension.ALLOWED_VOTE_ASSETS.toProtocol(),
        CommentOptionsExtension.COMMENT_PAYOUT_BENEFICIARIES.toProtocol());
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("author", author)
        .put("permlink", permlink)
        .put("max_expected_payout", ObjectMapUtils.toObjectMap(maxExpectedPayout))
        .put("percent_steem_dollers", percentSteemDollers.toProtocolValue())
        .put("allow_votes", allowVotes)
        .put("allow_curation_rewards", allowCurationRewards)
        .put("extensions", expectedExtensions)
        .build();
    Map<String, Object> expected = ImmutableMap.of("type", "comment_options", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void toAppbase_empty() {
    // set up
    CommentOptionsOperation sut = CommentOptionsOperation.builder().build();

    // exercise
    Map<String, Object> actual = sut.toAppbase();

    // verify
    Map<String, Object> expectedMap = ImmutableMap.<String, Object>builder()
        .put("allow_votes", true) // default value
        .put("allow_curation_rewards", true) // default value
        .put("extensions", Collections.emptyList())
        .build();
    Map<String, Object> expected = ImmutableMap.of("type", "comment_options", "value", expectedMap);

    assertThat(actual).isEqualTo(expected);
  }
}
