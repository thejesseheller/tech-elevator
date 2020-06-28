package com.techelevator.vendingmachine;

import com.techelevator.items.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Loader {

    private static final File INPUT_FILE = new File("vendingmachine.csv");
    private static final LinkedHashMap<String, Item> PRODUCTS = new LinkedHashMap<>();

    public String[] parseInputFile() {

        StringBuilder lineFromFile = new StringBuilder();

        try (Scanner inputFileReader = new Scanner(INPUT_FILE)) {
            while (inputFileReader.hasNextLine()) {
                lineFromFile.append(inputFileReader.nextLine()).append("\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lineFromFile.toString().split("\n");
    }

    // Machine buttons are keys, items are values
    public void putItemsInProductsMap(String[] inputFileAsArray) {

        for (String line : inputFileAsArray) {

            String[] itemInformation = line.split("[|]");
            String button = itemInformation[0];
            String itemName = itemInformation[1];
            BigDecimal price = new BigDecimal(itemInformation[2]);
            String itemType = itemInformation[3];

            if (itemType.equals("Chip")) {
                Item chip = new Chip(itemName, price);
                PRODUCTS.put(button, chip);
            } else if (itemType.equals("Candy")) {
                Item candy = new Candy(itemName, price);
                PRODUCTS.put(button, candy);
            } else if (itemType.equals("Drink")) {
                Item drink = new Drink(itemName, price);
                PRODUCTS.put(button, drink);
            } else if (itemType.equals("Gum")) {
                Item gum = new Gum(itemName, price);
                PRODUCTS.put(button, gum);
            }
        }
    }

    public LinkedHashMap<String, Item> passMapForVendingMachine(){
        putItemsInProductsMap(parseInputFile());
        return PRODUCTS;
    }
}