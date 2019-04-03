package io.steem.client.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class CommentBeneficiary {
  private final String account;
  private final SteemPercent weight;
}
