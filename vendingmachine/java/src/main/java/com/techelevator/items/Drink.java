package com.techelevator.items;

import java.math.BigDecimal;

public class Drink extends Item {

    public Drink(String name, BigDecimal price) {
        super(name, price);
    }

    @Override
    public String getConsumptionMessage() {
        return "Glug Glug, Yum!";
    }

    @Override
    public String toString() {
        return name + " " + price;

    }
}
