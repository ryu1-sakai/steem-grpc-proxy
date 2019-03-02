package io.steem.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Value
public class SteemProperties {

  private final SteemCurrency accountCreationFee;
  private final long accountSubsidyBudget;
  private final long accountSubsidyDecay;
  private final int maximumBlockSize;
  private final SteemCurrency sbdInterestRate;
  private final SbdExchangeRate sbdExchangeRate;
  private final String url;
  private final String newSigningKey;
}
