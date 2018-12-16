package io.steem.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public abstract class SteemOperation {

  private final String type;

  SteemOperation(String type) {
    this.type = type;
  }

  public List<Object> toCondenser() {
    return ImmutableList.of(type, getValueMap());
  }

  public Map<String, Object> toAppbase() {
    return ImmutableMap.of("type", type, "value", getValueMap());
  }

  protected abstract Map<String, Object> getValueMap();
}
