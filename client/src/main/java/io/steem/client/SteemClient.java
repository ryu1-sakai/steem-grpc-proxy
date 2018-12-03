package io.steem.client;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpMessage;
import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import io.steem.client.model.GetMethodsRequest;
import io.steem.client.model.GetMethodsResponseParser;
import io.steem.client.model.SteemApiRequest;
import io.steem.client.model.SteemApiResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class SteemClient {

  private static final Logger logger = LoggerFactory.getLogger(SteemClient.class);

  private final HttpClient httpClient;

  private SteemClient(URI uri) {
    httpClient = HttpClient.of(uri);
  }

  public static SteemClient of(URI uri) {
    return new SteemClient(uri);
  }

  public static void main(String[] args) throws Exception {
    SteemClient client = SteemClient.of(new URI("https://testnet.steemitdev.com"));
    GetMethodsRequest request = GetMethodsRequest.create();
    GetMethodsResponseParser parser = GetMethodsResponseParser.create();
    Optional<List<String>> maybeMethods = client.call(request, parser);
    System.out.println(maybeMethods);
  }

  public <T> Optional<T> call(SteemApiRequest request, SteemApiResponseParser<T> parser) {
    AggregatedHttpMessage httpRequest =
        AggregatedHttpMessage.of(HttpMethod.POST, "/", MediaType.JSON_UTF_8, request.toAppbase());
    AggregatedHttpMessage response = call(httpRequest);
    if (response.status() != HttpStatus.OK) {
      String errorMessage =
          String.format("Error in calling Steem API: request<%s> response<%s>", request, response);
      logger.warn(errorMessage);
      throw new SteemClientException(errorMessage);
    }
    String content = response.content().toStringUtf8();
    return parser.parseAppbaseResponse(content);
  }

  private AggregatedHttpMessage call(AggregatedHttpMessage httpRequest) {
    try {
      return httpClient.execute(httpRequest).aggregate().join();
    } catch (Exception e) {
      logger.warn("Exception in calling Steem API: request<{}>", httpRequest, e);
      throw e;
    }
  }
}
