package io.steem.grpcproxy;

import eu.bittrade.libs.steemj.apis.login.LoginApi;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import io.grpc.stub.StreamObserver;
import io.steem.grpc.LoginRequest;
import io.steem.grpc.LoginResponse;
import io.steem.grpc.SteemServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SteemProxyService extends SteemServiceGrpc.SteemServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SteemProxyService.class);

    private final CommunicationHandler communicationHandler;

    public SteemProxyService(CommunicationHandler communicationHandler) {
        this.communicationHandler = communicationHandler;
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            AccountName accountName = new AccountName(request.getAccountName());
            boolean succeeded
                    = LoginApi.login(communicationHandler, accountName, request.getPassword());
            responseObserver.onNext(LoginResponse.newBuilder().setSucceeded(succeeded).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.warn("Failed to call Login API", e);
            responseObserver.onError(e);
        }
    }
}
