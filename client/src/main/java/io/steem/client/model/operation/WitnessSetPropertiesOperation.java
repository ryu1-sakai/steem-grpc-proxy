package io.steem.client.model.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.steem.client.model.FutureExtensions;
import io.steem.client.model.SteemOperation;
import io.steem.client.model.SteemProperties;
import io.steem.client.model.util.ObjectMapUtils;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class WitnessSetPropertiesOperation extends SteemOperation {

  @Builder
  @lombok.Value
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class Value {
    private final String owner;
    private final SteemProperties props;
    private final List<FutureExtensions> extensions;
  }

  private final Value value;

  private WitnessSetPropertiesOperation(Value value) {
    super("witness_set_properties");
    this.value = value;
  }

  public static WitnessSetPropertiesOperation of(Value value) {
    return new WitnessSetPropertiesOperation(value);
  }

  @Override
  protected Map<String, Object> getValueMap() {
    return ObjectMapUtils.toObjectMap(value);
  }
}
