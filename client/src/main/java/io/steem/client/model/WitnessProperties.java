package io.steem.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Value
public class WitnessProperties {

  private final SteemCurrencySteem accountCreationFee;
  private final int accountSubsidyBudget;
  private final long accountSubsidyDecay;
  private final int maxBlockSize;
  private final SteemCurrencySteem sbdInterestRate;
  private final SbdExchangeRate sbdExchangeRate;
  private final String url;
  private final String newSigningKey;
}
