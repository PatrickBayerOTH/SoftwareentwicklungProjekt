package de.othr.im.util;

/*
DTO Class representing an Order sent to paypal
Written by Tobias Mooshofer
 */
public class PaypalOrder {

    //the "total" of the order
    private double price;

    //the currency ISO code
    private String currency;

    //CREDIT or PAYPAL
    private String method;

    //SALE, AUTHORIZE or ORDER
    private String intent;

    //the orders' items
    private String description;

    /*
    Constructor
    only needs a price and a description as input
    App specific statics:
    currency = EUR, method = PAYPAL, intent = SALE
     */
    public PaypalOrder(double price, String description) {
        this.price = price;
        this.currency = "EUR";
        this.method = "PAYPAL";
        this.intent = "SALE";
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}