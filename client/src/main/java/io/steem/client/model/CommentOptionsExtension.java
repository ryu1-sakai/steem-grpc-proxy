package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CommentOptionsExtension {
  COMMENT_PAYOUT_BENEFICIARIES,
  ALLOWED_VOTE_ASSETS;

  @JsonValue
  public String toProtocol() {
    return name().toLowerCase();
  }
}
