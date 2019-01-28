package io.steem.client.model;

public class SteemCurrencySteem extends SteemCurrency {

  private SteemCurrencySteem(float amount) {
    super(amount, "STEEM");
  }

  public static SteemCurrencySteem of(float amount) {
    return new SteemCurrencySteem(amount);
  }
}
