package io.steem.grpcproxy;

import com.linecorp.armeria.internal.shaded.guava.collect.ImmutableList;
import eu.bittrade.libs.steemj.apis.login.LoginApi;
import eu.bittrade.libs.steemj.apis.market.history.MarketHistoryApi;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import io.grpc.stub.StreamObserver;
import io.steem.grpc.*;
import io.steem.grpcproxy.converter.MarketTradeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    @Override
    public void getRecentTrades(GetRecentTradesRequest request,
                                StreamObserver<GetRecentTradesResponse> responseObserver) {
        try {
            int limit = Math.min(request.getLimit(), Short.MAX_VALUE);
            List<eu.bittrade.libs.steemj.apis.market.history.model.MarketTrade> steemjTrades
                    =  MarketHistoryApi.getRecentTrades(communicationHandler, (short) limit);
            List<MarketTrade> trades = steemjTrades.stream().map(MarketTradeConverter::toGrpc)
                    .collect(ImmutableList.toImmutableList());
            GetRecentTradesResponse response
                    = GetRecentTradesResponse.newBuilder().addAllMarketTrades(trades).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.warn("Failed to call GetRecentTraces API", e);
            responseObserver.onError(e);
        }
    }
}
