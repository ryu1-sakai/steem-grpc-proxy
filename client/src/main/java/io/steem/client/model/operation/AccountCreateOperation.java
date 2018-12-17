package io.steem.client.model.operation;

import io.steem.client.model.SteemAsset;
import io.steem.client.model.SteemOperation;

import java.util.Map;

public class AccountCreateOperation extends SteemOperation {

  private final SteemAsset fee;
  // TODO: Other fields

  protected AccountCreateOperation(SteemAsset fee) {
    super("account_create");
    this.fee = fee;
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return null; // TODO
  }
}
