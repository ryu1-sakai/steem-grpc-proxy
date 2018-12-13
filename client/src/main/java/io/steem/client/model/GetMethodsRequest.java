package io.steem.client.model;

public class GetMethodsRequest extends SteemApiRequest {

  private static final GetMethodApiRequestParams PARAMS = new GetMethodApiRequestParams();

  private GetMethodsRequest(long requestId) {
    super(null, "jsonrpc.get_methods", PARAMS, requestId);
  }

  public static GetMethodsRequest create(long requestId) {
    return new GetMethodsRequest(requestId);
  }
}
