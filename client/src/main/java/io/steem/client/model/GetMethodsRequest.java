package io.steem.client.model;

import java.util.Collections;
import java.util.Map;

public class GetMethodsRequest extends SteemApiRequest {

  private static class AppbaseComposer implements SteemApiRequest.AppbaseCompser {

    private static final AppbaseComposer INSTANCE = new AppbaseComposer();

    @Override
    public String getMethod() {
      return "jsonrpc.get_methods";
    }

    @Override
    public Map<String, Object> getParams() {
      return Collections.emptyMap();
    }
  }

  private GetMethodsRequest(long requestId) {
    super(null, AppbaseComposer.INSTANCE, requestId);
  }

  public static GetMethodsRequest create(long requestId) {
    return new GetMethodsRequest(requestId);
  }
}
