package io.steem.client.model;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.steem.client.SteemClientException;

import java.io.IOException;
import java.util.List;

public class GetMethodsResponseParser extends SteemApiResponseParser<List<String>> {

  private GetMethodsResponseParser() {}

  public static GetMethodsResponseParser create() {
    return new GetMethodsResponseParser();
  }

  @Override
  public List<String> parseCondenserResult(String result) {
    throw new SteemClientException("Condenser API unavailable");
  }

  @Override
  public List<String> parseAppbaseResult(String result) {
    try {
      TypeFactory typeFactory = OBJECT_MAPPER.getTypeFactory();
      CollectionType listType = typeFactory.constructCollectionType(List.class, String.class);
      return OBJECT_MAPPER.readValue(result, listType);
    } catch (IOException e) {
      throw new SteemClientException("Error in parsing JSON: " + result, e);
    }
  }
}
