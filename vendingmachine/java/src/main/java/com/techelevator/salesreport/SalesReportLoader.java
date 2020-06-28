package com.techelevator.salesreport;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class SalesReportLoader {

    private File salesReportFile = new File("salesreport.txt");
    private LinkedHashMap<String, Integer> pastSalesInformation = new LinkedHashMap<>();
    private double pastTotalSales;

    private String[] parseInputFile() {

        StringBuilder lineFromFile = new StringBuilder();

        try (Scanner inputFileReader = new Scanner(salesReportFile)) {
            while (inputFileReader.hasNextLine()) {
                lineFromFile.append(inputFileReader.nextLine()).append("\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lineFromFile.toString().split("\n");
    }

    private void createMapFromItemsInSalesReport(String[] salesReportAsArray) {

        for (String line : salesReportAsArray) {

            if (line.startsWith("*") || line.startsWith("DATE") || line.startsWith("TOTAL") || line.isEmpty()) {
                continue;
            }
            String[] itemSalesInformation = line.split("[|]");
            String productName = itemSalesInformation[0];
            int totalNumberSold = Integer.parseInt(itemSalesInformation[1]);
            pastSalesInformation.put(productName, totalNumberSold);
        }
    }

    public LinkedHashMap<String, Integer> passMapToReport() {
        createMapFromItemsInSalesReport(parseInputFile());
        return pastSalesInformation;
    }

    private void getPastTotalSalesFromArray(String[] salesReportAsArray) {

        for (String line : salesReportAsArray) {
            if (line.startsWith("TOTAL")) {
                String[] pastTotalSales = line.split("[$]");
                this.pastTotalSales = Double.parseDouble(pastTotalSales[1]);
            }
        }
    }

    public double pastTotalPastSalesToReport() {
        getPastTotalSalesFromArray(parseInputFile());
        return pastTotalSales;
    }

}
