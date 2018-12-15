package io.steem.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class SteemOperation {

  private final String type;
  private final Map<String, Object> value;

  SteemOperation(String type, Map<String, Object> value) {
    this.type = type;
    this.value = value;
  }

  public List<Object> toCondenser() {
    return ImmutableList.of(type, value);
  }

  public Map<String, Object> toAppbase() {
    return ImmutableMap.of("type", type, "value", value);
  }
}
