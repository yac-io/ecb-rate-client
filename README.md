# ecb-rate-client
[![Build Status](https://travis-ci.org/yac-io/ecb-rate-client.svg?branch=master)](https://travis-ci.org/yac-io/ecb-rate-client)

This library is a simple wrapper around the [Daily exchange rate xml](https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml) publish by the European Central Bank.

It provides a simple features to get the rate between two currencies as long as they are listed in the aforementioned xml.

## Sample usage

### Getting the value of 1 EUR in USD
In this case, the library simply parse the corresponding rate in the ECB xml
```java
FxQuoteRequest fxQuoteRequest = FxQuoteRequest.builder().baseCurrency(Currency.getInstance("EUR"))
        .quoteCurrency(Currency.getInstance("USD")).build();

EcbRateClient ecbRateClient = EcbRateClient.getInstance();

try {
    FxQuote quote = ecbRateClient.getQuote(fxQuoteRequest);
    System.out.println("On " + quote.getQuoteDate().format(DateTimeFormatter.ISO_DATE) + " 1 " +
            fxQuoteRequest.getBaseCurrency().getDisplayName() + " was worth " + quote.getRate() + " " +
            fxQuoteRequest.getQuoteCurrency().getDisplayName());

} catch (UnsupportedCurrencyException e) {
    e.printStackTrace();
}
```   

### Getting the value of 1 USD in EUR
In this case, the library takes the multiplicative inverse of the EUR/USD rate from the ECB xml 

```java
FxQuoteRequest fxQuoteRequest = FxQuoteRequest.builder().baseCurrency(Currency.getInstance("USD"))
        .quoteCurrency(Currency.getInstance("EUR")).build();

EcbRateClient ecbRateClient = EcbRateClient.getInstance();

try {
    FxQuote quote = ecbRateClient.getQuote(fxQuoteRequest);
    System.out.println("On " + quote.getQuoteDate().format(DateTimeFormatter.ISO_DATE) + " 1 " +
            fxQuoteRequest.getBaseCurrency().getDisplayName() + " was worth " + quote.getRate() + " " +
            fxQuoteRequest.getQuoteCurrency().getDisplayName());

} catch (UnsupportedCurrencyException e) {
    e.printStackTrace();
}

```   


### Getting the value of 1 CHF in USD
In this case, the libray gets the EUR/CHF and EUR/USD rate and divide the EUR/USD rate by the EUR/CHF rate.

```java
FxQuoteRequest fxQuoteRequest = FxQuoteRequest.builder().baseCurrency(Currency.getInstance("CHF"))
        .quoteCurrency(Currency.getInstance("USD")).build();

EcbRateClient ecbRateClient = EcbRateClient.getInstance();

try {
    FxQuote quote = ecbRateClient.getQuote(fxQuoteRequest);
    System.out.println("On " + quote.getQuoteDate().format(DateTimeFormatter.ISO_DATE) + " 1 " +
            fxQuoteRequest.getBaseCurrency().getDisplayName() + " was worth " + quote.getRate() + " " +
            fxQuoteRequest.getQuoteCurrency().getDisplayName());

} catch (UnsupportedCurrencyException e) {
    e.printStackTrace();
}

```   
### Exception
The library will throw a ```UnsupportedCurrencyException``` if one of the currency in the currency is pair is not EUR and not listed in the ECB xml (for instance XPF is one of those currency at the time of writing )