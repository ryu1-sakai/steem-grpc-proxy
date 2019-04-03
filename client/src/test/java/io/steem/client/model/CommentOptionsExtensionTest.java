package io.steem.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.steem.client.model.operation.OperationTestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentOptionsExtensionTest {

  @Test
  public void beneficiaries_toProtocol() {
    // set up
    SteemPercent percentAbc = OperationTestUtils.randomSteemPercent();
    SteemPercent percentOpq = OperationTestUtils.randomSteemPercent();
    SteemPercent percentXyz = OperationTestUtils.randomSteemPercent();
    List<CommentBeneficiary> commentBeneficiaries = ImmutableList.of(
        CommentBeneficiary.of("xyz", percentXyz),
        CommentBeneficiary.of("abc", percentAbc),
        CommentBeneficiary.of("opq", percentOpq)
    );

    // exercise
    CommentOptionsExtension sut = CommentOptionsExtension.beneficiaries(commentBeneficiaries);
    List<Object> actual = sut.toProtocol();

    // verify
    List<CommentBeneficiary> sortedCommentBeneficiaries = ImmutableList.of(
        CommentBeneficiary.of("abc", percentAbc),
        CommentBeneficiary.of("opq", percentOpq),
        CommentBeneficiary.of("xyz", percentXyz)
    );
    assertThat(actual).containsExactly(
        CommentOptionsExtension.ExtensionId.BENEFICIARIES.toProtocol(),
        sortedCommentBeneficiaries);
  }

  @Test
  public void allowedVoteAssets() {
    // set up
    VotableAssetInfoV1 votableAssetInfoSteem
        = VotableAssetInfoV1.of(RandomUtils.nextLong(), RandomUtils.nextBoolean());
    VotableAssetInfoV1 votableAssetInfoSbd
        = VotableAssetInfoV1.of(RandomUtils.nextLong(), RandomUtils.nextBoolean());
    Map<String, VotableAssetInfoV1> votableAssetInfoByAssetSymbol = ImmutableMap.of(
        "STEEM", votableAssetInfoSteem,
        "SBD", votableAssetInfoSbd
    );

    // exercise
    CommentOptionsExtension sut
        = CommentOptionsExtension.allowedVoteAssets(votableAssetInfoByAssetSymbol);
    List<Object> actual = sut.toProtocol();

    // verify
    assertThat(actual).containsExactly(
        CommentOptionsExtension.ExtensionId.ALLOWED_VOTE_ASSETS.toProtocol(),
        votableAssetInfoByAssetSymbol
    );
  }
}
