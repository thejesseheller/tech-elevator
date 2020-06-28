package com.techelevator.salesreport;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SalesReportLogger implements Closeable{

    private PrintWriter writer;

    public SalesReportLogger() {
        String path = new SimpleDateFormat("MM.dd.yy hh.mm'_salesreport.txt'").format(new Date());
        File salesReportFile = new File(path);

        if (salesReportFile.exists()) {
            try {
                writer = new PrintWriter(new FileWriter(salesReportFile, true));
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } else {
            try {
                writer = new PrintWriter(salesReportFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void write(String logMessage) {

        try {
            writer.println(logMessage);
            writer.flush();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ;
        }
    }

    public void logUpdatedSalesReport() {

        for (Map.Entry<String, Integer> e : SalesReport.getTotalItemsSold().entrySet()) {
            String productName = e.getKey();
            int numberSold = e.getValue();
            String salesInformation = productName + "|" + numberSold;
            write(salesInformation);
        }
        write("\nTOTAL SALES: " + NumberFormat.getCurrencyInstance().format(SalesReport.getTotalSales()));
        writer.close();

    }


}
