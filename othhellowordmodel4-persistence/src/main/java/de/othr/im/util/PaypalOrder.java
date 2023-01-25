package de.othr.im.util;

public class PaypalOrder {

    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

    public PaypalOrder(double price, String description) {
        this.price = price;
        this.currency = "EUR";
        this.method = "PAYPAL";
        this.intent = "ORDER";
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