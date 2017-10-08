package io.yac.ecb.rate.client;

import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;

public interface EcbRateClient {

    FxQuote getQuote(FxQuoteRequest fxQuoteRequest) throws UnsupportedCurrencyException;

}
