package io.yac.ecb.rate.client;

import io.yac.ecb.rate.client.exception.InvalidEcbXmlException;
import io.yac.ecb.rate.client.exception.UnsupportedCurrencyException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class EcbXmlWrapper {

    private final Document document;

    public EcbXmlWrapper(InputStream inputStream) throws InvalidEcbXmlException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            document = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new InvalidEcbXmlException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public EcbXmlWrapper(String xmlContent) throws InvalidEcbXmlException {
        this(new ByteArrayInputStream(xmlContent.getBytes(Charset.forName("UTF-8"))));

    }

    public BigDecimal getRateForCurrency(String quoteCurrency) throws UnsupportedCurrencyException {
        try {
            String xpath = "//Cube[@currency='" + quoteCurrency + "']/@rate";
            if (!nodeExist(xpath)) {
                throw new UnsupportedCurrencyException(
                        String.format("The currency {%s} is not listed in the ECB rate file", quoteCurrency));
            }
            return BigDecimal.valueOf(evaluateNumberXpath(xpath));
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }

    public LocalDate getPublishDate() {
        try {
            return LocalDate.parse(evaluateStringXpath("//Cube/@time"), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }

    private String evaluateStringXpath(String xpathStr) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathStr);
        return (String) expr.evaluate(document, XPathConstants.STRING);

    }

    private boolean nodeExist(String xpathStr) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathStr);
        return expr.evaluate(document, XPathConstants.NODE) != null;
    }

    private Double evaluateNumberXpath(String xpathStr) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathStr);
        return (Double) expr.evaluate(document, XPathConstants.NUMBER);
    }
}
