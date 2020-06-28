package com.techelevator;

import com.techelevator.salesreport.SalesReport;
import com.techelevator.salesreport.SalesReportLogger;
import com.techelevator.vendingmachine.VendingMachine;
import com.techelevator.view.Menu;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALES_REPORT = "";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    private final VendingMachine VM = new VendingMachine();
    private final SalesReport SALES_REPORT = new SalesReport();
    private final Menu MENU;

    public VendingMachineCLI(Menu menu) {
        this.MENU = menu;
    }

    public void run() {

        VM.loadItems();
        SALES_REPORT.loadPastSalesInformation();

        while (true) {

            String choice = (String) MENU.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                VM.displayItems();

            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

                while (true) {

                    String purchaseChoice = (String) MENU.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

                    if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        MENU.displayFeedMoneyPrompt(VM);
                        System.out.println("\nCurrent Money Provided: " + VM.formattedGetBalance());
                    }

                    if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                        VM.displayItems();
                        VM.buyItem(MENU.displayPurchasePrompt(VM));
                    }

                    if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                        VM.returnChange();
                        break;
                    }
                }

            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("\nThank you!");
                break;
            } else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
                System.out.println("\nGenerating sales report.");
                SalesReportLogger salesReportLogger = new SalesReportLogger();
                salesReportLogger.logUpdatedSalesReport();
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
