package io.yac.ecb.rate.client;

import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RateCalculatorTest {

    @Test
    @DisplayName("Rate is one if both currencies are the same")
    void testNoConversion() throws Exception {
        FxQuoteRequest request = new FxQuoteRequest(Currency.getInstance("EUR"), Currency.getInstance("EUR"));
        RateCalculator rateCalculator = new RateCalculator();

        assertEquals(BigDecimal.ONE, rateCalculator.getExchangeRate(request, null));
    }

    @Test
    @DisplayName(
            "When base currency is EUR, it returns the rate corresponding to the quote currency from the ecb xml wrapper")
    void testBaseCurrencyEur() throws Exception {
        FxQuoteRequest request = new FxQuoteRequest(Currency.getInstance("EUR"), Currency.getInstance("USD"));
        RateCalculator rateCalculator = new RateCalculator();

        EcbXmlWrapper mockEcbXmlWrapper = mock(EcbXmlWrapper.class);
        when(mockEcbXmlWrapper.getRateForCurrency("USD")).thenReturn(BigDecimal.valueOf(1.34));
        assertEquals(BigDecimal.valueOf(1.34), rateCalculator.getExchangeRate(request, mockEcbXmlWrapper));
    }


    @Test
    @DisplayName(
            "It throws unsupported currency exception when quote currency is not found in the ecb xml")
    void testQuoteCurrencyUnsupported() throws Exception {
        FxQuoteRequest request = new FxQuoteRequest(Currency.getInstance("EUR"), Currency.getInstance("JPY"));
        RateCalculator rateCalculator = new RateCalculator();

        EcbXmlWrapper mockEcbXmlWrapper = mock(EcbXmlWrapper.class);
        when(mockEcbXmlWrapper.getRateForCurrency("JPY")).thenThrow(UnsupportedCurrencyException.class);

        assertThrows(UnsupportedCurrencyException.class,
                () -> rateCalculator.getExchangeRate(request, mockEcbXmlWrapper));
    }

    @Test
    @DisplayName(
            "It throws unsupported currency exception when base currency is not found in the ecb xml and is not EUR")
    void testBaseCurrencyUnsupported() throws Exception {
        FxQuoteRequest request = new FxQuoteRequest(Currency.getInstance("JPY"), Currency.getInstance("CHF"));
        RateCalculator rateCalculator = new RateCalculator();

        EcbXmlWrapper mockEcbXmlWrapper = mock(EcbXmlWrapper.class);
        when(mockEcbXmlWrapper.getRateForCurrency("JPY")).thenThrow(UnsupportedCurrencyException.class);

        assertThrows(UnsupportedCurrencyException.class,
                () -> rateCalculator.getExchangeRate(request, mockEcbXmlWrapper));
    }

    @Test
    @DisplayName(
            "It compute exchange rates when base currency is not Eur")
    void testBaseNotEur() throws Exception {
        FxQuoteRequest request = new FxQuoteRequest(Currency.getInstance("USD"), Currency.getInstance("CHF"));
        RateCalculator rateCalculator = new RateCalculator();

        EcbXmlWrapper mockEcbXmlWrapper = mock(EcbXmlWrapper.class);
        when(mockEcbXmlWrapper.getRateForCurrency("USD")).thenReturn(BigDecimal.valueOf(1.5));
        when(mockEcbXmlWrapper.getRateForCurrency("CHF")).thenReturn(BigDecimal.valueOf(3));

        assertEquals(BigDecimal.valueOf(20000, 4), rateCalculator.getExchangeRate(request, mockEcbXmlWrapper));
    }

    @Test
    @DisplayName(
            "It handles EUR as quote currency")
    void testQuoteEur() throws Exception {
        FxQuoteRequest request = new FxQuoteRequest(Currency.getInstance("USD"), Currency.getInstance("EUR"));
        RateCalculator rateCalculator = new RateCalculator();

        EcbXmlWrapper mockEcbXmlWrapper = mock(EcbXmlWrapper.class);
        when(mockEcbXmlWrapper.getRateForCurrency("USD")).thenReturn(BigDecimal.valueOf(1.5));

        assertEquals(BigDecimal.valueOf(0.6667), rateCalculator.getExchangeRate(request, mockEcbXmlWrapper));
    }

}