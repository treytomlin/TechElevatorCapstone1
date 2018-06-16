package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													   MAIN_MENU_OPTION_PURCHASE };
	private static final String SUB_MENU_FEED_MONEY = "Feed Money";
	private static final String SUB_MENU_SELECT_PRODUCT = "Select Product";
	private static final String SUB_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] SUB_MENU_OPTIONS = {SUB_MENU_FEED_MONEY, SUB_MENU_SELECT_PRODUCT, SUB_MENU_FINISH_TRANSACTION};
	
	private Menu menu;
	List<Product> purchasedObjects = new ArrayList<Product>();
	
	public VendingMachineCLI(Menu menu) {

		this.menu = menu;
	}

	
	public void run() throws IOException {
		    VendingMachine vendingMachine = new VendingMachine();
			File file = vendingMachine.getInputFile();
			Map<String, Product> inventoryMap = vendingMachine.getInventory(file);
			
		while(true) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, vendingMachine.getBalance());
			
			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				while(true) {
				String[] productArray = new String[inventoryMap.size()];
				int ctr = 0;
				Set<Map.Entry<String, Product>> entrySet = inventoryMap.entrySet();
				for (Entry<String, Product> entry: entrySet) {
					String key = entry.getKey();
					Product value = entry.getValue();
					 productArray[ctr] = key + " " + value.toString();
					 ctr++;
				}
				menu.displayMenuOptionsForItems(productArray);
				break;
				}
			} if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				
				while(true) {
				String choice2 = (String)menu.getChoiceFromOptions(SUB_MENU_OPTIONS, vendingMachine.getBalance());
				if(choice2.toUpperCase() == "R") {
					break;
				}
				 if(choice2.equals(SUB_MENU_FEED_MONEY)) {
					while(true) {
						try {
							System.out.println("Enter amount you would like to feed or (R)eturn to previous menu --> ");

							Scanner in = new Scanner(System.in);
							String input = in.nextLine();

							if(input.toUpperCase().equals("R")) {
								break;
							} else {
								double amountEntered = Double.parseDouble(input);
								vendingMachine.feedMoney(amountEntered);
								System.out.println("Current balance $" + vendingMachine.getBalance());
							}
							} catch (NumberFormatException e) {
								System.out.println("Machine only accepts: $1's, $2's, $5's, & $10's "); // : needs escaped!
							}
					}
						
				} else if(choice2.equals(SUB_MENU_SELECT_PRODUCT)) {
					while(true) {
						System.out.println("Please enter the item you would like to purchase or (R)eturn to previous menu --> ");

						Scanner in = new Scanner(System.in);
						String input = in.nextLine();

						if(input.toUpperCase().equals("R")) {
							break;

						} else if(inventoryMap.containsKey(input.toUpperCase())) {
							if(inventoryMap.get(input).isAvailableToPurchase() && vendingMachine.balance >= inventoryMap.get(input).getPrice()) {
								inventoryMap.get(input).purchaseItem();
								purchasedObjects.add(inventoryMap.get(input));
								vendingMachine.balance -= inventoryMap.get(input).getPrice();
								vendingMachine.log(inventoryMap.get(input).getName(), (vendingMachine.balance + inventoryMap.get(input).getPrice()), vendingMachine.balance);
								System.out.println("Purchased!");

							} else if(!inventoryMap.get(input).isAvailableToPurchase()) {
								System.out.println("SOLD OUT!");
								break;
							} else {
								System.out.println("Insufficient Funds, Please make a deposit!");
								break;
							}

						} else {
							System.out.println("That is not a valid option, try again");
							break;
						}
					}

				} else if(choice2.equals(SUB_MENU_FINISH_TRANSACTION)){
					vendingMachine.returnChange();
					vendingMachine.logFile();
					vendingMachine.balance = 0;
					System.out.println("Final balance: $" + vendingMachine.getBalance());
					System.out.println("");

					for(Product product: purchasedObjects) {
						String sound = product.getSound();
						System.out.println(sound);
					}
					break;
				}
				
			}
				
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		try {
			cli.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
