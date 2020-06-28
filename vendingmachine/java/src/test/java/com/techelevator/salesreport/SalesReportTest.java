package com.techelevator.salesreport;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class SalesReportTest {

	@Test
	public void testAddRecentSales1ToTotalSales5() {

		double totalSales = 5;
		SalesReport addToTotal = new SalesReport();
		addToTotal.updateTotalSales(new BigDecimal(5));
		double expected = SalesReport.getTotalSales();

		assertEquals(expected, totalSales, 0.05);
	}

}
