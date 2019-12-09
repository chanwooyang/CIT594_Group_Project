package edu.upenn.cit594.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.bind.ParseConversionEvent;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ZipCodeReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.*;

public class UserInterface {
	
	protected ZipCodeProcessor zcProc;
	protected PropertyProcessor propProc;
	protected ParkingViolationProcessor pvProc;
	private final int NUM_OF_ACTIONS = 6;
	
	public UserInterface (ZipCodeProcessor zcProc, PropertyProcessor propProc, ParkingViolationProcessor pvProc) {
		this.zcProc = zcProc;
		this.propProc = propProc;
		this.pvProc = pvProc;
	}
	
	public void promptUser () {
		System.out.println("It is assumed your inputs are correct. Thank you.");
		System.out.println();

		// optional additional functionality: add a help menu which explains each option

		System.out.println("0: exit program");
		System.out.println("1: show total population in all ZIP codes");
		System.out.println("2: total parking fines per capita for each ZIP code");
		System.out.println("3: average property market value in user-specified ZIP code");
		System.out.println("4: average total livable area in properties in user-specified ZIP code");
		System.out.println("5: total property mark value per capita in user-specified ZIP code");
		System.out.println("6: average parking fine per average market value in all ZIP codes with data");

		while (true) {
			System.out.println();
			System.out.println("Please specify which action to be performed from the list of options above.");
			
			Scanner userInput = new Scanner(System.in);

			// get action number
			int actionNumber;
			while (true) {
				while (!userInput.hasNextInt()) {
					System.out.println("You have entered a non-integer value. Program terminated.");
					System.exit(0);
				}
				actionNumber = userInput.nextInt();
				if (actionNumber >= 0 && actionNumber <= NUM_OF_ACTIONS) {
					break;
				}
				else {
					System.out.println("Not an appropriate integer between 0 and " + NUM_OF_ACTIONS + " Program terminated.");
					System.exit(0);
				}
			}

			Logger lg = Logger.getInstance();
			lg.log(lg.getTime() + " "  + actionNumber);

			if (actionNumber == 0) {
				System.exit(0);
			}
			if (actionNumber == 1) {
				System.out.println(zcProc.getTotalPopulationAllZip());
				continue;
			}
			
//			ParkingViolationProcessor p = new ParkingViolationProcessor("csv", "parking.csv");
//			List<ParkingViolation> pl = new ArrayList<ParkingViolation>();
//			ZipCodeProcessor zp = new ZipCodeProcessor("population.txt");
//			Map<Integer, Integer> zl = new HashMap<Integer, Integer>();
//			Map<Integer, Double> fpc = new HashMap<>();
//			zl = zp.process();
//			pl = p.getParkingViolationList();
//			fpc = p.getTotalFinesPerCapita(zl);
//			for (Integer zip : fpc.keySet()) {
//				System.out.format(zip + " %.4f\n", fpc.get(zip));
//			}
			
			if (actionNumber == 2) {
				HashMap<Integer, Integer> zipCodePopMap = (HashMap<Integer, Integer>) zcProc.process();
				TreeMap<Integer, Double> finesTM = (TreeMap<Integer, Double>) pvProc.getTotalFinesPerCapita(zipCodePopMap);
				for (Integer zip : finesTM.keySet()) {
					System.out.format(zip + " %.4f\n", finesTM.get(zip));
				}
				continue;
			}
			
			// all remaining actions need a ZIP code
			System.out.println("Please enter a ZIP code:");
			if (!userInput.hasNextInt()) {
				// print 0 if not a valid ZIP code
				System.out.println("0");
				continue;
			}
			int userZipCode = userInput.nextInt();
			lg.log(lg.getTime() + " "  + userZipCode);
			
			if (actionNumber == 3) {
				System.out.println(propProc.getAvMarketValue(userZipCode));
				continue;
			}
			if (actionNumber == 4) {
				System.out.println(propProc.getAvTotalLivableArea(userZipCode));
				continue;
			}
			if (actionNumber == 5) {
				System.out.println(propProc.getTotalMarketValuePerCapita(userZipCode, zcProc));
				continue;
			}
			if (actionNumber == 6) {
				HashMap<Integer, Integer> zipCodePopMap = (HashMap<Integer, Integer>) zcProc.process();
				TreeMap<Integer, Double> ratio6TM = (TreeMap<Integer, Double>) pvProc.getTotalFineToAvgMarketValuePerZip(zipCodePopMap, propProc);
				for (Integer zip : ratio6TM.keySet()) {
					System.out.format(zip + " %.4f\n", ratio6TM.get(zip));
				}
				continue;
			}
			userInput.close();

		}
	}
}
 