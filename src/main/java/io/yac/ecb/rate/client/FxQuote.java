package io.yac.ecb.rate.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

/**
 * Represent a currency pair with the Foreign Exchange rate between the two currencies as published by the European Central Bank
 * and the date on which the rate was published.
 *
 * @version 1.0
 * @since 1.0
 */
public class FxQuote implements Serializable {

    private final Currency baseCurrency;

    private final Currency quoteCurrency;

    private final LocalDate quoteDate;

    private final BigDecimal rate;

    private FxQuote(Currency baseCurrency, Currency quoteCurrency, LocalDate quoteDate, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.quoteDate = quoteDate;
        this.rate = rate;
    }

    /**
     * @return return a {@link FxQuote.Builder} to create an instance of {@link FxQuote}
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

    /**
     * @return the date on which the rate was quoted
     * @since 1.0
     */
    public LocalDate getQuoteDate() {
        return quoteDate;
    }

    /**
     * @return the exchange rate quoted by the ECB such as 1 baseCurrency = rate quoteCurrency
     * @see #getBaseCurrency()
     * @see #getQuoteCurrency()
     * @since 1.0
     */
    public BigDecimal getRate() {
        return rate;
    }


    @Override public String toString() {
        return "FxQuote{" +
                "baseCurrency=" + baseCurrency +
                ", quoteCurrency=" + quoteCurrency +
                ", quoteDate=" + quoteDate +
                ", rate=" + rate +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FxQuote fxQuote = (FxQuote) o;
        return Objects.equals(baseCurrency, fxQuote.baseCurrency) &&
                Objects.equals(quoteCurrency, fxQuote.quoteCurrency) &&
                Objects.equals(quoteDate, fxQuote.quoteDate) &&
                Objects.equals(rate, fxQuote.rate);
    }

    @Override public int hashCode() {
        return Objects.hash(baseCurrency, quoteCurrency, quoteDate, rate);
    }

    /**
     * Builder class to create an instance of {@link FxQuote}
     *
     * @version 1.0
     * @since 1.0
     */
    public static class Builder {
        private Currency baseCurrency;
        private Currency quoteCurrency;
        private LocalDate quoteDate;
        private BigDecimal rate;

        /**
         * @param baseCurrency the baseCurrency in the currency pair
         * @return this builder instance
         * @since 1.0
         */
        public Builder baseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        /**
         * @param quoteCurrency the quoteCurrency in the currency pair
         * @return this builder instance
         * @since 1.0
         */
        public Builder quoteCurrency(Currency quoteCurrency) {
            this.quoteCurrency = quoteCurrency;
            return this;
        }

        /**
         * @param quoteDate the quotation date for this rate
         * @return this builder instance
         * @since 1.0
         */
        public Builder quoteDate(LocalDate quoteDate) {
            this.quoteDate = quoteDate;
            return this;
        }

        /**
         * @param rate the exchange rate quoted by the ECB
         * @return this builder instance
         * @since 1.0
         */
        public Builder rate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        /**
         * @return an {@link FxQuote} corresponding to the object build using this {@link FxQuote.Builder} instance
         * @since 1.0
         */
        public FxQuote build() {
            return new FxQuote(baseCurrency, quoteCurrency, quoteDate, rate);
        }
    }
}
