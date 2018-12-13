package io.steem.client.model;

import io.steem.client.SteemClientException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GetMethodApiRequestParams implements SteemApiRequestParams {

  @Override
  public List<Object> forCondenser() {
    throw new SteemClientException("Condenser API unavailable");
  }

  @Override
  public Map<String, Object> forAppbase() {
    return Collections.emptyMap();
  }
}
