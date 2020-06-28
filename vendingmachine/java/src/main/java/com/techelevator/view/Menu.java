package com.techelevator.view;

import com.techelevator.items.Item;
import com.techelevator.vendingmachine.VendingMachine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println("\n*** " + userInput + " is not a valid option ***\n");
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < 3; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print("\nPlease choose an option >>> ");
        out.flush();
    }


    public void displayFeedMoneyPrompt(VendingMachine vm) {

        System.out.println("\nMachine accepts $1, $2, $5, or $10 bills. Hit '0' when finished to return to previous menu.");

        while (true) {
            System.out.print("\nPlease deposit funds ('0' exits) >>> ");
            String input = in.nextLine();

            if (input.isEmpty()) {
                break;
            }

            int money = 0;

            try {
                money = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("\nNot a number. Returning to previous menu.");
            }

            if (money == 0) {
                break;
            } else if (money == 1 || money == 2 || money == 5 || money == 10) {
                vm.feedMoney(new BigDecimal(money));
            } else if (money >= 20) {
                System.out.println("Denomination too large. This machine only accepts $1, $2, $5, or $10 bills.");
            } else {
                System.out.println("Invalid denomination. Machine only accepts $1, $2, $5, or $10 bills.");
            }
			System.out.println("\nCurrent Money Provided: " + vm.formattedGetBalance());
        }
    }

    public String displayPurchasePrompt(VendingMachine vm) {
        System.out.print("\nPlease enter code >>> ");
        String input = in.nextLine();
        LinkedHashMap<String, Item> items = vm.getItems();

        if (items.containsKey(input)) {
            return input;
        } else {
            return "\nProduct code does not exist. Returning to previous menu.";
        }
    }
}
