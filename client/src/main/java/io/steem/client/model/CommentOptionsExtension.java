package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class CommentOptionsExtension {

  public enum ExtensionId {
    BENEFICIARIES(0),
    ALLOWED_VOTE_ASSETS(1);

    private final int number;

    ExtensionId(int number) {
      this.number = number;
    }

    @JsonValue
    public int toProtocol() {
      return number;
    }
  }

  private final ExtensionId id;
  private final Object value;

  private CommentOptionsExtension(ExtensionId id, Object value) {
    this.id = id;
    this.value = value;
  }

  public static CommentOptionsExtension beneficiaries(List<CommentBeneficiary> beneficiaries) {
    SortedSet<CommentBeneficiary> sorted = ImmutableSortedSet
        .orderedBy(Comparator.comparing(CommentBeneficiary::getAccount))
        .addAll(beneficiaries)
        .build();
    return new CommentOptionsExtension(ExtensionId.BENEFICIARIES, ImmutableList.copyOf(sorted));
  }

  public static CommentOptionsExtension allowedVoteAssets(
      Map<String, VotableAssetInfoV1> votableAssetInfoByAssetSymbol) {
    return new CommentOptionsExtension(
        ExtensionId.ALLOWED_VOTE_ASSETS, votableAssetInfoByAssetSymbol);
  }

  @JsonValue
  public List<Object> toProtocol() {
    return ImmutableList.of(id.number, value);
  }
}
