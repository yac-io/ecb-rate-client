package io.yac.ecb.rate.client;

import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;

import java.math.BigDecimal;
import java.util.Currency;

class RateCalculator {

    BigDecimal getExchangeRate(FxQuoteRequest request, EcbXmlWrapper xmlWrapper) throws UnsupportedCurrencyException {
        if (request.getBaseCurrency().equals(request.getQuoteCurrency())) {
            return BigDecimal.ONE;
        }

        if (Currency.getInstance("EUR").equals(request.getBaseCurrency())) {
            return xmlWrapper.getRateForCurrency(request.getQuoteCurrency().getCurrencyCode());
        } else if (Currency.getInstance("EUR").equals(request.getQuoteCurrency())) {
            return BigDecimal.ONE.divide(xmlWrapper.getRateForCurrency(request.getBaseCurrency().getCurrencyCode()), 4,
                    BigDecimal.ROUND_HALF_UP);
        } else {
            BigDecimal eurToBase = xmlWrapper.getRateForCurrency(request.getBaseCurrency().getCurrencyCode());
            BigDecimal eurToQuote = xmlWrapper.getRateForCurrency(request.getQuoteCurrency().getCurrencyCode());

            return eurToQuote.divide(eurToBase, 4, BigDecimal.ROUND_HALF_UP);


        }
    }

}
