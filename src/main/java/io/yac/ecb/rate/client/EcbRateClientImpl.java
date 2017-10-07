package io.yac.ecb.rate.client;

public class EcbRateClientImpl implements EcbRateClient {

    private static final String ECB_XML_ENDPOINT = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Override
    public FxQuote getQuotation(FxQuoteRequest fxQuoteRequest) {
        return null;
    }
}
