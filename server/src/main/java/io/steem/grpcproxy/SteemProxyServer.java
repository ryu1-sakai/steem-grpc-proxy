package io.steem.grpcproxy;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.ServiceWithPathMappings;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;

public class SteemProxyServer {

    public static void main(String[] args) {
        ServiceWithPathMappings<HttpRequest, HttpResponse> service
                = new GrpcServiceBuilder()
                .addService(new SteemProxyService())
                .enableUnframedRequests(true)
                .build();
        Server server = new ServerBuilder().http(80).service(service).build();
        server.start().join();
    }
}
