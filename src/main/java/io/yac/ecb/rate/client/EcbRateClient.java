package io.yac.ecb.rate.client;

import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;

public interface EcbRateClient {

    EcbRateClient INSTANCE = new EcbRateClientImpl();

    /**
     * @return a singleton Instance of {@link EcbRateClientImpl}
     */
    static EcbRateClient getInstance() {
        return INSTANCE;
    }

    /**
     * @param fxQuoteRequest the request
     * @return the quote corresponding to the request as per the values listed in the ECB xml
     * @throws UnsupportedCurrencyException when of the currency in the currency pair is neither EUR nor present in the ECB xml
     */
    FxQuote getQuote(FxQuoteRequest fxQuoteRequest) throws UnsupportedCurrencyException;
}
