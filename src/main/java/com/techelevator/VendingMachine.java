package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;


public class VendingMachine {
	double balance = 0;
	List<String> list = new ArrayList<String>();


public File getInputFile() {
	String path = "//Users/ttomlin/development/IntelliJ_Projects/Capstone1-VendingMachine/TechElevatorCapstone1/vendingmachine.csv";
			File inputFile = new File(path);

			if( !inputFile.exists() ) { // checks for the existence of a file
				System.out.println(path+" does not exist");
				System.exit(1); // Ends the program
				
			} else if (!inputFile.isFile() ) {
				System.out.println(path+" is not a file");
				System.exit(1); // Ends the program if no File detected
			}
			return inputFile;
		}

public Map<String, Product> getInventory(File inputFile) throws FileNotFoundException {
	Map<String, Product> inventoryMap = new LinkedHashMap<String, Product>();
	try(Scanner fileScanner = new Scanner(inputFile)){
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] value_split = line.split(Pattern.quote("|"));
			if(value_split[3].equals("Chip")) {
				Product product = new Chip(value_split[1], Double.parseDouble(value_split[2]));
				inventoryMap.put(value_split[0], product);
				
			} else if(value_split[3].equals("Candy")) {
				Product product = new Candy(value_split[1], Double.parseDouble(value_split[2]));
				inventoryMap.put(value_split[0], product);
				
			} else if(value_split[3].equals("Drink")) {
				Product product = new Drink(value_split[1], Double.parseDouble(value_split[2]));	
				inventoryMap.put(value_split[0], product);

			} else if(value_split[3].equals("Gum")) {
				Product product = new Gum(value_split[1], Double.parseDouble(value_split[2]));	
				inventoryMap.put(value_split[0], product);
			}
		}
	}
	return inventoryMap;
	}

public void feedMoney(double addMoney) {
	Set<Double> values = new HashSet<Double>(Arrays.asList(
		     new Double[] {1.00, 2.00, 5.00, 10.00}));
		if(values.contains(addMoney)) {
			balance += addMoney;
		} else {
			System.out.println("Invalid Currency Amount, Only $1s, $2s, $5s, and $10s\n ");
		} 
}
	public double getBalance() {
	return balance;
	}
	
	public void returnChange() {
		Double[] change = new Double[] {0.25, 0.10, 0.05};
		  String[] coinName = new String[] {"Quarter(s)", "Dime(s)", "Nickle(s)"};
		  for(int i = 0; i < change.length; i++) {
		    	  int counter;
		    	  counter = (int) (balance / change[i]);

		      balance -= (change[i] * counter);
		      System.out.println(counter + " " + coinName[i]);
		  }
	}
	
	public void logFile() throws IOException  {
		File outputFile = new File("//Users/ttomlin/development/IntelliJ_Projects/Capstone1-VendingMachine/TechElevatorCapstone1/log.txt");
		List<String> list = getList();
		try(FileWriter logWriter = new FileWriter(outputFile, true)){
			for(String str : list) {
				logWriter.write(str);
				logWriter.write("\n");
			}
		}
	}
	public List<String> log(String name, double beginningAmount, double endAmount) {
		
		LocalDateTime time = LocalDateTime.now();
		DecimalFormat myFormat = new DecimalFormat("#.00");
		String str = time + " " + name + " " + beginningAmount + " " + myFormat.format(endAmount);
		list.add(str);
		return list;
	}
	public List<String> getList(){
	return this.list;
	}
}
