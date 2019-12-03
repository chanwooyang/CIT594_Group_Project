package edu.upenn.cit594.ui;

import java.util.Scanner;

import edu.upenn.cit594.logging.Logger;

public class UserInterface {
	
	protected PopulationProcessor popProc;
	protected PropertyProcessor propProc;
	protected ParkingViolationProcessor pvProc;
	private final int NUM_OF_ACTIONS = 6;
	
	public UserInterface (PopulationProcessor popProc, PropertyProcessor propProc, 
			ParkingViolationProcessor pvProc) {
		this.popProc = popProc;
		this.propProc = propProc;
		this.pvProc = pvProc;
	}
	
	public void promptUser () {
		System.out.println("It is assumed your inputs are correct. Thank you.");
		System.out.println("Please specify which action to be performed from the list of options below.");
		// optional additional functionality: add a help menu which explains each option
		
		System.out.println("0: exit program");
		System.out.println("1: show total population in all ZIP codes");
		System.out.println("2: total parking fines per capita for each ZIP code");
		System.out.println("3: average property market value in user-specified ZIP code");
		System.out.println("4: average total livable area in properties in user-specified ZIP code");
		System.out.println("5: total property mark value per capita in user-specified ZIP code");
		System.out.println("6: average parking fine per average market value in all ZIP codes with data");
		
		Scanner userInput = new Scanner(System.in);
		int actionNumber = -100;
		while (true) {
			while (!userInput.hasNextInt()) {
				System.out.println("You have entered a non-integer value. Please enter a number between 0 - 6");
				userInput.next();
			}
			actionNumber = userInput.nextInt();
			if (actionNumber >= 0 && actionNumber < NUM_OF_ACTIONS) {
				break;
			}
			else {
				System.out.println("Not an appropriate integer. Please enter an integer between 0 - 6");
				userInput.next();
			}
		}	
		
		
		Logger lg = Logger.getInstance();
		lg.log(lg.getTime() + " "  + actionNumber);
		
		if (actionNumber == 0) {
			System.exit(0);
		}
		if (actionNumber == 1) { popProc.totalPopulation(); }
		if (actionNumber == 2) { pvProc.totalFinesPerCap(); }
		if (actionNumber > 2 && actionNumber < 7 ) {
			System.out.println("Please enter a ZIP code");
			String userZipCode = userInput.nextLine();
		}
		
		if (actionNumber == 3) { propProc.averageMarketValue(userZipCode); }
		if (actionNumber == 4) { propProc.averageTotalLivableArea(userZipCode); }
		if (actionNumber == 5) { propProc.marketValuePerCap(userZipCode); }
		if (actionNumber == 6) { pvProc.averageFinePerMarketValue(userZipCode); }
		userInput.close();
		
	}
	
}
 