package io.steem.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Value
public class SteemAuthority {

  private long weightThreshold; // Originally unsigned int 32
  private SortedMap<String, Integer> accountAuths; // account -> weight
  private SortedMap<String, Integer> keyAuths; // key -> weight

  public Map<String, Object> compose() {
    return ImmutableMap.<String, Object>builder()
        .put("weight_threshold", weightThreshold)
        .put("account_auths", toNestedList(accountAuths))
        .put("key_auths", toNestedList(keyAuths))
        .build();
  }

  private static List<List<Object>> toNestedList(SortedMap<String, Integer> sortedMap) {
    //noinspection UnstableApiUsage
    return sortedMap.entrySet()
        .stream()
        .map(e -> ImmutableList.<Object>of(e.getKey(), e.getValue()))
        .collect(ImmutableList.toImmutableList());
  }
}
