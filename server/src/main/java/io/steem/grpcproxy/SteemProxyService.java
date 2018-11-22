package io.steem.grpcproxy;

import io.grpc.stub.StreamObserver;
import io.steem.grpc.LoginRequest;
import io.steem.grpc.LoginResponse;
import io.steem.grpc.SteemServiceGrpc;

public class SteemProxyService extends SteemServiceGrpc.SteemServiceImplBase {

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        responseObserver.onNext(LoginResponse.newBuilder().setSucceeded(false).build());
        responseObserver.onCompleted();
    }
}
