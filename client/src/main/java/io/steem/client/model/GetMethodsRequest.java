package io.steem.client.model;

import io.steem.client.SteemClientException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GetMethodsRequest extends SteemApiRequest {

  private GetMethodsRequest() {}

  public static GetMethodsRequest create() {
    return new GetMethodsRequest();
  }

  @Override
  protected boolean isCondenserAvailable() {
    return false;
  }

  @Override
  protected boolean isAppbaseAvailable() {
    return true;
  }

  @Override
  protected String getCondenserMethod() {
    throw new SteemClientException("Condenser API unavailable");
  }

  @Override
  protected String getAppbaseMethod() {
    return "jsonrpc.get_methods";
  }

  @Override
  protected List<Object> getCondenserParams() {
    throw new SteemClientException("Condenser API unavailable");
  }

  @Override
  protected Map<String, Object> getAppbaseParams() {
    return Collections.emptyMap();
  }
}
