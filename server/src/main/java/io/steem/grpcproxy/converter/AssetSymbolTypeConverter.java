package io.steem.grpcproxy.converter;

import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import io.steem.grpc.AssetSymbol;

public final class AssetSymbolTypeConverter {
    private AssetSymbolTypeConverter() {}

    public static AssetSymbol toGrpc(AssetSymbolType steemjObj) {
        switch (steemjObj) {
            case VESTS:
                return AssetSymbol.VESTS;
            case STEEM:
                return AssetSymbol.STEEM;
            case SBD:
                return AssetSymbol.SBD;
            case STMD:
                return AssetSymbol.STMD;
            case TESTS:
                return AssetSymbol.TESTS;
            case TBD:
                return AssetSymbol.TBD;
            case TSTD:
                return AssetSymbol.TSTD;
            default:
                throw new IllegalArgumentException("Unknown AssetSymbolType: " + steemjObj);
        }
    }
}
