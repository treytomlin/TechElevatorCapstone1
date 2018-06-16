package com.techelevator.view;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;




	public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {

		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	public Object getChoiceFromOptions(Object[] options, double balance) {

		Object choice = null;
		while(choice == null) {
			
			displayMenuOptions(options, balance);
			
			choice = getChoiceFromUserInput(options);
		
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {

		Object choice = null;
		String userInput = in.nextLine();
		
		
		try {
			if(userInput.toUpperCase().equals(("R"))) {
				choice = "R";
				return choice;
			}
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			} 
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}

		return choice;

	}



	private void displayMenuOptions(Object[] options, double balance) {
		
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		DecimalFormat myFormat = new DecimalFormat("#.00");
		out.println("\nCurrent Money Provided: $" + myFormat.format(balance));
		out.print("\nPlease choose an option --> ");
		
		out.flush();
	}
	public void displayMenuOptionsForItems(Object[] options) {
		out.println();
		String str = String.format("%1$-7s %2$-13s %3$7s %4$10s", "Slot", "Name", "Price", "Quantity");
		out.println(str);
		for(int i = 0; i < options.length; i++) {
			out.println(options[i]);
			
		}
		
		out.flush();

	}

}
