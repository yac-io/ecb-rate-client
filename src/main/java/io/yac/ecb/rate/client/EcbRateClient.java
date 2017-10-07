package io.yac.ecb.rate.client;

public interface EcbRateClient {

    FxQuote getQuotation(FxQuoteRequest fxQuoteRequest);

}
