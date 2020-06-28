package com.techelevator.salesreport;

import java.math.BigDecimal;
import java.util.HashMap;

public class SalesReport {

    private static HashMap<String, Integer> totalItemsSold = new HashMap<>();
    private static double totalSales;

    public static HashMap<String, Integer> getTotalItemsSold() {
        return totalItemsSold;
    }

    public static double getTotalSales() {
        return totalSales;
    }

    public void updateTotalSales(BigDecimal recentSales){
        totalSales += recentSales.doubleValue();
    }

    public void loadPastSalesInformation() {
        SalesReportLoader loader = new SalesReportLoader();
        totalItemsSold = loader.passMapToReport();
        totalSales = loader.pastTotalPastSalesToReport();
    }

    public void updateTotalItemsSold(String itemName) {
        if (totalItemsSold.containsKey(itemName)) {
            int individualItemTotalSales = totalItemsSold.get(itemName);
            totalItemsSold.put(itemName, individualItemTotalSales + 1);
        } else {
            totalItemsSold.put(itemName, 1);
        }
    }
}
