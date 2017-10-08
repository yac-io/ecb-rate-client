import io.yac.ecb.rate.client.EcbRateClient;
import io.yac.ecb.rate.client.FxQuote;
import io.yac.ecb.rate.client.FxQuoteRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.fail;

class IntegrationTest {

    @Test
    @DisplayName("Simple integration test to verify that the endpoint is correctly responding")
    void integrationTest() {
        try {
            FxQuoteRequest fxQuoteRequest = FxQuoteRequest.builder().baseCurrency(Currency.getInstance("EUR"))
                    .quoteCurrency(Currency.getInstance("USD")).build();

            EcbRateClient ecbRateClient = EcbRateClient.getInstance();

            FxQuote quote = ecbRateClient.getQuote(fxQuoteRequest);
            System.out.println("On " + quote.getQuoteDate().format(DateTimeFormatter.ISO_DATE) + " 1 " +
                    fxQuoteRequest.getBaseCurrency().getDisplayName() + " was worth " + quote.getRate() + " " +
                    fxQuoteRequest.getQuoteCurrency().getDisplayName());

        } catch (Throwable t) {
            fail(t);
        }

    }

    @Test
    @DisplayName("Integration test euro not involved")
    void integrationTestNoEur() {
        try {
            FxQuoteRequest fxQuoteRequest = FxQuoteRequest.builder().baseCurrency(Currency.getInstance("JPY"))
                    .quoteCurrency(Currency.getInstance("USD")).build();

            EcbRateClient ecbRateClient = EcbRateClient.getInstance();

            FxQuote quote = ecbRateClient.getQuote(fxQuoteRequest);
            System.out.println("On " + quote.getQuoteDate().format(DateTimeFormatter.ISO_DATE) + " 1 " +
                    fxQuoteRequest.getBaseCurrency().getDisplayName() + " was worth " + quote.getRate() + " " +
                    fxQuoteRequest.getQuoteCurrency().getDisplayName());

        } catch (Throwable t) {
            fail(t);
        }
    }

    @Test
    @DisplayName("Integration test euro quote currency")
    void integrationTestEurQuoteCurrency() {
        try {
            FxQuoteRequest fxQuoteRequest = FxQuoteRequest.builder().baseCurrency(Currency.getInstance("CHF"))
                    .quoteCurrency(Currency.getInstance("EUR")).build();

            EcbRateClient ecbRateClient = EcbRateClient.getInstance();

            FxQuote quote = ecbRateClient.getQuote(fxQuoteRequest);
            System.out.println("On " + quote.getQuoteDate().format(DateTimeFormatter.ISO_DATE) + " 1 " +
                    fxQuoteRequest.getBaseCurrency().getDisplayName() + " was worth " + quote.getRate() + " " +
                    fxQuoteRequest.getQuoteCurrency().getDisplayName());

        } catch (Throwable t) {
            fail(t);
        }

    }


}
