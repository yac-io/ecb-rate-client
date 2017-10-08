package io.yac.ecb.rate.client;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import io.yac.ecb.rate.client.exception.InvalidEcbXmlException;
import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;

import java.io.IOException;

public class EcbRateClientImpl implements EcbRateClient {

    private static final String ECB_XML_ENDPOINT = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final HttpRequestFactory requestFactory;

    private final RateCalculator rateCalculator;

    public EcbRateClientImpl() {
        requestFactory = HTTP_TRANSPORT.createRequestFactory();
        rateCalculator = new RateCalculator();
    }


    @Override
    public FxQuote getQuote(FxQuoteRequest fxQuoteRequest) throws UnsupportedCurrencyException {
        fxQuoteRequest.validate();

        try {
            final HttpRequest httpRequest = requestFactory.buildGetRequest(new GenericUrl(ECB_XML_ENDPOINT));
            final String responseString = httpRequest.execute().parseAsString();

            final EcbXmlWrapper ecbXmlWrapper = new EcbXmlWrapper(responseString);

            return FxQuote.builder().quoteDate(ecbXmlWrapper.getPublishDate())
                    .baseCurrency(fxQuoteRequest.getBaseCurrency()).quoteCurrency(fxQuoteRequest.getQuoteCurrency())
                    .rate(rateCalculator.getExchangeRate(fxQuoteRequest, ecbXmlWrapper))
                    .build();

        } catch (IOException | InvalidEcbXmlException e) {
            throw new IllegalStateException(e);
        }

    }

}
