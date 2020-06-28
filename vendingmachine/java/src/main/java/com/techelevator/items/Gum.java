package com.techelevator.items;

import java.math.BigDecimal;

public class Gum extends Item {

    public Gum(String name, BigDecimal price) {
        super(name, price);
    }

    @Override
    public String getConsumptionMessage() {
        return "Chew Chew, Yum!";
    }

    @Override
    public String toString() {
        return name + " " + price;

    }
}
