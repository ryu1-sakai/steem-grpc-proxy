package io.steem.grpcproxy.converter;

import io.steem.grpc.Asset;
import io.steem.grpc.MarketTrade;

public final class MarketTradeConverter {
  private MarketTradeConverter() {}

  public static MarketTrade toGrpc(
      eu.bittrade.libs.steemj.apis.market.history.model.MarketTrade steemjObj) {
    long timestamp = steemjObj.getDate().getDateTimeAsTimestamp();
    Asset currentPays = AssetConverter.toGrpc(steemjObj.getCurrentPays());
    Asset openPays = AssetConverter.toGrpc(steemjObj.getOpenPays());
    return MarketTrade.newBuilder()
        .setTimestamp(timestamp)
        .setCurrentPays(currentPays)
        .setOpenPays(openPays)
        .build();
  }
}
