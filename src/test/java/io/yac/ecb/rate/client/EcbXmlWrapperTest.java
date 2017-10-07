package io.yac.ecb.rate.client;

import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EcbXmlWrapperTest {

    private static final String SAMPLE_XML_FILE_NAME = "../resources/eurofxref-daily-sample.xml";

    private static InputStream readXmlFile() {
        return EcbXmlWrapper.class.getClassLoader().getResourceAsStream(SAMPLE_XML_FILE_NAME);
    }

    @Test
    @DisplayName("getRateForCurrency when a rate is listed for the quoteCurrency, it returns the corresponding rate")
    void testSuccessful() throws Exception {
        String quoteCurrency = "USD";
        BigDecimal expectedRate = BigDecimal.valueOf(1.1707);

        EcbXmlWrapper wrapper = new EcbXmlWrapper(readXmlFile());
        assertEquals(expectedRate, wrapper.getRateForCurrency(quoteCurrency));
    }

    @Test
    @DisplayName("getRateForCurrency throws UnsupportedCurrencyException when the currency is not in the xml")
    void testUnknownException() throws Exception {
        String unsupportedQuoteCurrency = "AAA";

        EcbXmlWrapper wrapper = new EcbXmlWrapper(readXmlFile());
        UnsupportedCurrencyException unsupportedCurrencyException = assertThrows(UnsupportedCurrencyException.class,
                () -> wrapper.getRateForCurrency(unsupportedQuoteCurrency));

        assertEquals("The currency {AAA} is not listed in the ECB rate file",
                unsupportedCurrencyException.getMessage());
    }

}