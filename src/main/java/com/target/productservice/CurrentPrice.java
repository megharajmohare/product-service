package com.target.productservice;

public class CurrentPrice {

    private double value;
    private String currency_code;

    public CurrentPrice() {
    }

    public CurrentPrice(double value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
