package io.steem.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Value
public class SteemAuthority {

  private long weightThreshold; // Originally unsigned int 32
  @JsonIgnore
  private SortedMap<String, Integer> accountAuths; // account -> weight
  @JsonIgnore
  private SortedMap<String, Integer> keyAuths; // key -> weight

  @JsonProperty("account_auths")
  public List<List<Object>> accountAuths() {
    return toNestedList(accountAuths);
  }

  @JsonProperty("key_auths")
  public List<List<Object>> keyAuths() {
    return toNestedList(keyAuths);
  }

  private static List<List<Object>> toNestedList(SortedMap<String, Integer> sortedMap) {
    //noinspection UnstableApiUsage
    return sortedMap.entrySet()
        .stream()
        .map(e -> ImmutableList.<Object>of(e.getKey(), e.getValue()))
        .collect(ImmutableList.toImmutableList());
  }
}
