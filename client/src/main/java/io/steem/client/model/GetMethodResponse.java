package io.steem.client.model;

import java.util.List;

public class GetMethodResponse {

  private final List<String> methods;

  private GetMethodResponse(List<String> methods) {
    this.methods = methods;
  }

  public static GetMethodResponse of(List<String> methods) {
    return new GetMethodResponse(methods);
  }

  public List<String> getMethods() {
    return methods;
  }
}
