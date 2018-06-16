package com.techelevator;

public class Drink extends Product{
	public Drink(String name, double price) {
		super(name, price);
	}


	public String getSound() {
		return "Glug Glug, Yum!";
	}

	

}
