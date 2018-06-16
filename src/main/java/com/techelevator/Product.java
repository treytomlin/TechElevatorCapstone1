package com.techelevator;

import java.text.DecimalFormat;

public abstract class Product {
	private String name;
	private double price;
	private int numberOfItems = 5;
	
	
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
		
	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public double getPrice() {
		return price;
	}

//	public void setPrice(double price) {
//		this.price = price;
//	}
	
	public abstract String getSound();
		
	public String toString() {
		DecimalFormat myFormat = new DecimalFormat("#.00");
		String str = String.format(" %1$-20s %2$-10s %3$s", name, "$" + myFormat.format(price), this.getNumberOfItems());
		return str;
	}
	
	public int getNumberOfItems() {
		return numberOfItems;
	}
//	public void setNumberOfItems() {
//		this.numberOfItems = 5;
//	}

	public boolean isAvailableToPurchase() {
		if(this.numberOfItems >= 1) {
			return true;
		}
		return false;
	}
	
	public void purchaseItem() {
		numberOfItems -= 1;
		
	}

	
	
	

}
