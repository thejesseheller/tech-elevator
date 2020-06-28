package com.techelevator.vendingmachine;
import com.techelevator.items.Item;
import com.techelevator.salesreport.SalesReport;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.math.BigDecimal;

public class VendingMachine {

    private final Logger LOG = new Logger();
    private final  SalesReport SALES_REPORT = new SalesReport();
    private LinkedHashMap<String, Item> items = new LinkedHashMap<>();
    private BigDecimal balance = new BigDecimal(0);

    public void displayItems() {

        System.out.println();
        System.out.println(String.format("%-5s | %-20s | %s | %6s", "BUTTON", "NAME", "PRICE ", "QUANTITY"));
        System.out.println("**************************************************");
        for (String button : items.keySet()) {
            if (items.get(button).inStock()) {
                String itemName = items.get(button).getName();
                BigDecimal itemPrice = items.get(button).getPrice();
                int quantity = items.get(button).getQuantity();
                System.out.println(String.format("%-6s | %-20s | $%5s | %4s", button, itemName, itemPrice, quantity));
            }
        }
        System.out.println("**************************************************");
    }

    public void buyItem(String button) {
        Item item = items.get(button);

        if (item == null) {
            System.out.println("Invalid code. Please try again.");
        } else if (item.inStock() && balance.compareTo(item.getPrice()) >= 0) {
            System.out.println("\n" + item.getName() + " | $" + item.getPrice() +
                    " spent | $" + balance.subtract(item.getPrice()) + " remaining | " + item.getConsumptionMessage());

            item.purchased();
            BigDecimal balanceBeforePurchase = getBalance();
            balance = balance.subtract(item.getPrice());
            SALES_REPORT.updateTotalItemsSold(item.getName());
            SALES_REPORT.updateTotalSales(item.getPrice());

            LOG.buyItemLogEntry(item.getName(), button, balanceBeforePurchase, balance);

        } else if (item.inStock()) {
            System.out.println("Please insert additional funds.");
        } else {
            System.out.println("SOLD OUT.");
        }
    }

    public void feedMoney(BigDecimal dollars) {
        balance = balance.add(dollars);
        LOG.feedMoneyLogEntry(dollars, balance);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String formattedGetBalance() {
        return NumberFormat.getCurrencyInstance().format(getBalance());
    }

    public void returnChange() {

        System.out.println();
        StringBuilder returnedChange = new StringBuilder("Dispensing your change below: ");

        double[] coinValues = new double[]{
                0.25, 0.10, 0.05
        };

        String[] coinNames = new String[] {
                "Quarter", "Dime", "Nickel"
        };

        BigDecimal balanceBeforeChangeGiven = getBalance();

        for (int i = 0; i < coinValues.length; i++) {
            int count = 0;
            count = ((int) (balance.doubleValue() / coinValues[i]));
            balance = balance.subtract(BigDecimal.valueOf(coinValues[i] * count));

            if (count > 1){
                returnedChange.append(count).append(" ").append(coinNames[i]).append("s ");
            } else if (count > 0) {
                returnedChange.append(count).append(" ").append(coinNames[i]).append(" ");
            }
        }

        System.out.println(returnedChange);
        LOG.returnChangeLogEntry(balanceBeforeChangeGiven, finishTransaction());
    }

    public void loadItems() {
        Loader loader = new Loader();
        items = loader.passMapForVendingMachine();
    }

    public LinkedHashMap<String, Item> getItems() {
        return items;
    }

    public BigDecimal finishTransaction() {
        balance = new BigDecimal(0);
        return balance;
    }
}
