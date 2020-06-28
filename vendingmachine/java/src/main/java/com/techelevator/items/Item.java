package com.techelevator.items;

import java.math.BigDecimal;

public abstract class Item {
    protected String name;
    protected BigDecimal price;
    protected int quantity = 5;

    public Item(String name, BigDecimal price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean inStock() {
        return quantity > 0;
    }

    public void purchased() {
        quantity--;
    }

    public abstract String getConsumptionMessage();
}
