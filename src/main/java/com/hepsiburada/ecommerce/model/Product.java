package com.hepsiburada.ecommerce.model;

public class Product {

    private String code;
    private Long stock;
    private Double defaultPrice;
    private Double price;

    public Product(String code, Long stock, Double price) {
        this.code = code;
        this.stock = stock;
        this.price = price;
        defaultPrice = price;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
