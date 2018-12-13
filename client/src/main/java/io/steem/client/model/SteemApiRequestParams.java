package io.steem.client.model;

import java.util.List;
import java.util.Map;

public interface SteemApiRequestParams {

  List<Object> forCondenser();

  Map<String, Object> forAppbase();
}
