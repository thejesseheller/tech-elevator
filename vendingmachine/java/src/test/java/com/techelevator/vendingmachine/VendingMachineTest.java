package com.techelevator.vendingmachine;

import static org.junit.Assert.*;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {

	private VendingMachine vm;

	@Before
	public void setup() {

		vm = new VendingMachine();
		vm.loadItems();
	}

	@Test
	public void testFeedMoneyInput1Adds1ToBalance() {

		BigDecimal expected = BigDecimal.valueOf(1);
		vm.feedMoney(new BigDecimal(1));
		BigDecimal result = vm.getBalance();

		assertEquals(expected, result);
	}

	@Test
	public void testFeedMoneyInput5Adds5ToBalance() {

		BigDecimal expected = BigDecimal.valueOf(5);
		vm.feedMoney(new BigDecimal(5));
		BigDecimal result = vm.getBalance();

		assertEquals(expected, result);

	}

	@Test
	public void testFeedMoneyInputOneOfEachDenominationAdds18ToBalance() {
		BigDecimal expected = BigDecimal.valueOf(18);
		vm.feedMoney(new BigDecimal(1));
		vm.feedMoney(new BigDecimal(2));
		vm.feedMoney(new BigDecimal(5));
		vm.feedMoney(new BigDecimal(10));
		BigDecimal result = vm.getBalance();

		assertEquals(expected, result);
	}

	@Test
	public void testBuyItemSufficientFundsItemInStock () {
		vm.feedMoney(new BigDecimal(5));
		vm.buyItem("D4");
		int expected = 4;

		assertEquals(4, vm.getItems().get("D4").getQuantity());
	}

	@Test
	public void testBuyItemInsufficientFundsItemInStock() {
		vm.feedMoney(new BigDecimal(1));
		vm.buyItem("A1");
		int expected = 5;

		assertEquals(5, vm.getItems().get("A1").getQuantity());
	}

	@Test
	public void testBuyItemThatIsSoldOut() {
		vm.feedMoney(new BigDecimal(10));
		vm.buyItem("D4");
		vm.buyItem("D4");
		vm.buyItem("D4");
		vm.buyItem("D4");
		vm.buyItem("D4");

		assertFalse(vm.getItems().get("D4").inStock());
	}

	@Test	
	public void testFinishTransactionResetsBalanceToZero() {
		BigDecimal expected = BigDecimal.valueOf(0);
		vm.finishTransaction();
		BigDecimal result = vm.getBalance();

		assertEquals(expected, result);

	}

}
