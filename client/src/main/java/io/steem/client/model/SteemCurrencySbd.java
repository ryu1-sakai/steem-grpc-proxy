package io.steem.client.model;

public class SteemCurrencySbd extends SteemCurrency {

  private SteemCurrencySbd(float amount) {
    super(amount, "SBD");
  }

  public static SteemCurrencySbd of(float amount) {
    return new SteemCurrencySbd(amount);
  }
}
