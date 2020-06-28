package com.techelevator.items;

import java.math.BigDecimal;

public class Chip extends Item {


    public Chip(String name, BigDecimal price) {
        super(name, price);
    }

    @Override
    public String getConsumptionMessage() {
        return "Crunch Crunch, Yum!";
    }

    @Override
    public String toString() {
        return name + " " + price;

    }
}
