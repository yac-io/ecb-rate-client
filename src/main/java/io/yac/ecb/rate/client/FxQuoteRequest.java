package io.yac.ecb.rate.client;

import java.io.Serializable;
import java.util.Currency;

/**
 * Represent a Fx rate conversion request between a base and a quote currency.
 * To know how much 1 EUR is worth in USD, EUR is the base currency and USD is the quote currency
 *
 * @version 1.0
 * @since 1.0
 */
public class FxQuoteRequest implements Serializable {


    private final Currency baseCurrency;

    private final Currency quoteCurrency;


    FxQuoteRequest(Currency baseCurrency, Currency quoteCurrency) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }

    /**
     * @return a {@link FxQuoteRequest.Builder} to build the {@link FxQuoteRequest}
     * @since 1.0
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return the base currency
     * @since 1.0
     */
    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * @return the quote currency
     * @since 1.0
     */
    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    void validate() {
        if (baseCurrency == null) {
            throw new IllegalArgumentException("FxQuoteRequest.baseCurrency must not be null");
        }

        if (quoteCurrency == null) {
            throw new IllegalArgumentException("FxQuoteRequest.quoteCurrency must not be null");
        }

    }

    /**
     * Builder class to create an instance of a {@link FxQuoteRequest}
     */
    public static class Builder {
        private Currency baseCurrency;
        private Currency quoteCurrency;

        /**
         * @param baseCurrency the base currency (the currency in which you have the amount)
         * @return this builder instance
         * @since 1.0
         */
        public Builder baseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        /**
         * @param quoteCurrency the quote currency (the currency in which you want to convert your amount)
         * @return this builder instance
         * @since 1.0
         */
        public Builder quoteCurrency(Currency quoteCurrency) {
            this.quoteCurrency = quoteCurrency;
            return this;
        }

        /**
         * @return an instance of {@link FxQuoteRequest} for this {@link #baseCurrency} and {@link #quoteCurrency}
         * @since 1.0
         */
        public FxQuoteRequest build() {
            return new FxQuoteRequest(baseCurrency, quoteCurrency);
        }
    }
}
