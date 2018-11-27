package io.steem.grpcproxy.converter;

import io.steem.grpc.Asset;
import io.steem.grpc.AssetSymbol;

public final class AssetConverter {
    private AssetConverter() {}

    public static Asset toGrpc(eu.bittrade.libs.steemj.base.models.Asset steemjObj) {
        long account = steemjObj.getAmount();
        AssetSymbol symbol = AssetSymbolTypeConverter.toGrpc(steemjObj.getSymbol());
        int precision = steemjObj.getPrecision();
        return Asset.newBuilder()
                .setAmmount(account)
                .setSymbol(symbol)
                .setPrecision(precision)
                .build();
    }
}
