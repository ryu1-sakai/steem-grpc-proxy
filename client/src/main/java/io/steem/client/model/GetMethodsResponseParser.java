package io.steem.client.model;

import com.google.common.collect.ImmutableList;
import io.steem.client.SteemClientException;

import java.util.List;

public class GetMethodsResponseParser extends SteemApiResponseParser<List<String>> {

  private GetMethodsResponseParser() {
    super(null, GetMethodsResponseParser::parseAppbaseResult);
  }

  public static GetMethodsResponseParser create() {
    return new GetMethodsResponseParser();
  }

  private static List<String> parseAppbaseResult(Object result) {
    if (!(result instanceof List)) {
      throw new SteemClientException(
          String.format("result must be List but is %s <%s>", result.getClass(), result));
    }
    List<?> resultItems = (List<?>) result;
    //noinspection UnstableApiUsage
    return resultItems.stream().map(item -> {
      if (!(item instanceof String)) {
        throw new SteemClientException(
            String.format("result must consist of String items but contains %s <%s> :result<%s>",
                item.getClass(), item, result));
      }
      return (String) item;
    }).collect(ImmutableList.toImmutableList());
  }
}
