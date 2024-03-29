package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.*;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.logging.Logger;

public class ZipCodeReader {

	private String fileName;

	public ZipCodeReader(String fileName) {
		this.fileName = fileName;
	}

	public Map<Integer, Integer> read() {

		HashMap<Integer, Integer> popPerZip = new HashMap<Integer, Integer>();

		try {
			FileReader popTxtFile = new FileReader(fileName);
			// whenever an input file is opened for reading, the program should "log it."
			Logger lg = Logger.getInstance();
			lg.log(lg.getTime() + " "  + fileName);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(popTxtFile);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] tokens = line.split(" ",-1);
				if (tokens.length != 2)
					continue; // check if both zipcode and pop data are present

				try {

					int zipCode = Integer.parseInt(tokens[0]);
					int population = Integer.parseInt(tokens[1]);

					popPerZip.put(zipCode, population);

				} catch (IndexOutOfBoundsException e) {
					// do nothing. skip the line.
				} catch (NumberFormatException e) {
					// do nothing. skip the line.
				}
			}
			
			return popPerZip;

		} catch (FileNotFoundException e) {
			System.out.println("Population file: \"" + fileName + "\" does not exist. Terminating Program...");
			System.exit(0);
		} catch (SecurityException e) {
			System.out.println("No permission to the Population file: \"" + fileName + "\". Terminating Program...");
			System.exit(0);
		}

		return null;
	}
	
	// main method below is for debugging/testing purpose
	public static void main(String[] args) {
		Map<Integer, Integer> popPerZip = new HashMap<Integer, Integer>();
		ZipCodeReader r = new ZipCodeReader("population.txt");
		
		popPerZip = r.read();
		
		ZipCode popZipMap = new ZipCode(popPerZip);
		
		int count = 0;
		for (int zip : popZipMap.getPopulPerZipCodeMap().keySet()) {
			System.out.println(zip + " " + popPerZip.get(zip));
			count++;
		}
		System.out.println(count);

	}
}
