package io.steem.client.model;

public enum CommentOptionsExtension {
  COMMENT_PAYOUT_BENEFICIARIES,
  ALLOWED_VOTE_ASSETS;

  public String toProtocol() {
    return name().toLowerCase();
  }
}
