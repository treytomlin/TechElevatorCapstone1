package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;





public class VendingMachineTest {
	
	VendingMachine vendingMachine;
	
	@Before
	public void SetUp() {
		vendingMachine = new VendingMachine();
	}
	
	@Test
	public void add_money_adds_correct_amount_to_balance() {
		vendingMachine.feedMoney(20);
		Assert.assertEquals(20, vendingMachine.getBalance(), 0.001);
	}

}
