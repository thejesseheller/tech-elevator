package com.techelevator.vendingmachine;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements Closeable {

    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private final LocalDateTime NOW = LocalDateTime.now();
    private PrintWriter writer;

    public Logger() {

        File logFile = new File("log.txt");
        if (logFile.exists()) {
            try {
                writer = new PrintWriter(new FileWriter(logFile, true));
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } else {
            try {
                writer = new PrintWriter(logFile);
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
        }
    }

    public void buyItemLogEntry(String itemName, String button, BigDecimal balanceBeforePurchase, BigDecimal balanceAfterPurchase) {
        write(">" + DTF.format(NOW) + " " + itemName + " " + button + " " + currencyFormat(balanceBeforePurchase) + " " + currencyFormat(balanceAfterPurchase));
    }

    public void feedMoneyLogEntry(BigDecimal dollars, BigDecimal balance) {
        write(">" + DTF.format(NOW) + " FEED MONEY: " + currencyFormat(dollars) + " " + currencyFormat(balance));
    }

    public void returnChangeLogEntry(BigDecimal balanceBeforeChangeGiven, BigDecimal balanceAfterChangeGiven) {
        write(">" + DTF.format(NOW) + " GIVE CHANGE: " + currencyFormat(balanceBeforeChangeGiven) + " " + currencyFormat(balanceAfterChangeGiven));
    }

    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }

}
