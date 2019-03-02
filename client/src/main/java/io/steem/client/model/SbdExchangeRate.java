package io.steem.client.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class SbdExchangeRate {
  private final SteemCurrencySbd base;
  private final SteemCurrencySteem quote;
}
