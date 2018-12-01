package io.steem.grpcproxy;

import com.google.common.collect.ImmutableList;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.ServiceWithPathMappings;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URI;
import java.util.List;

public class SteemProxyServer {

  public static CommunicationHandler createCommunicationHandler() {
    try {
      // TODO: Refactor SteemJ's CommunicationHandler to accept configuration
      URI testNetUrl = URI.create("https://api.steemit.com");
      List<Pair<URI, Boolean>> endpoints = ImmutableList.of(Pair.of(testNetUrl, false));
      SteemJConfig.getInstance().setEndpointURIs(endpoints);
      return new CommunicationHandler();
    } catch (Exception e) {
      throw new RuntimeException(e); // TODO: Error handling
    }
  }

  public static Server create() {
    ServiceWithPathMappings<HttpRequest, HttpResponse> service =
        new GrpcServiceBuilder()
            .addService(new SteemProxyService(createCommunicationHandler()))
            .supportedSerializationFormats(
                GrpcSerializationFormats.PROTO, GrpcSerializationFormats.JSON)
            .enableUnframedRequests(true)
            .build();
    return new ServerBuilder().http(80).service(service).build();
  }

  public static void main(String[] args) {
    create().start().join();
  }
}
