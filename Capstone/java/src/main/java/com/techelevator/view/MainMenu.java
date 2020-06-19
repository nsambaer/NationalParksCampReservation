package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;

public class MainMenu extends Menu {
	public MainMenu(InputStream input, OutputStream output) {
		super(input, output);
	}

	@Override
	protected Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			if (userInput.equalsIgnoreCase("q")) {
				choice = "quit";
			} else {
				int selectedOption = Integer.valueOf(userInput);
				if (selectedOption > 0 && selectedOption <= options.length) {
					choice = options[selectedOption - 1];
				}
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	@Override
	protected void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		
		out.println( "Q) Quit");
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
}
