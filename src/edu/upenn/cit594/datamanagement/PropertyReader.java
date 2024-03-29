package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.*;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logger;

public class PropertyReader {

	private String fileName;

	// constructor
	public PropertyReader(String fileName) {
		this.fileName = fileName;
	}

	public List<Property> read() {

		ArrayList<Property> propertyList = new ArrayList<Property>();

		FileReader propertyFile;
		try {
			propertyFile = new FileReader(fileName);
			// whenever an input file is opened for reading, the program should "log it."
			Logger lg = Logger.getInstance();
			lg.log(lg.getTime() + " "  + fileName);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(propertyFile);

			String titles = sc.nextLine();
			String[] titleTokens = titles.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

			// find market_value, total_livable_area, zip_code indices
			int mvIndex = 0, tlaIndex = 0, zcIndex = 0;
			for (int i = 0; i < titleTokens.length; i++) {
				if (titleTokens[i].equals("market_value")) {
					mvIndex = i;
				} else if (titleTokens[i].equals("total_livable_area")) {
					tlaIndex = i;
				} else if (titleTokens[i].equals("zip_code")) {
					zcIndex = i;
				}
			}

			while (sc.hasNextLine()) {

				String line = sc.nextLine();
				String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				try {
					// Check if any of 3 data fields is missing
					if (tokens[mvIndex].isEmpty() || tokens[tlaIndex].isEmpty()
							|| tokens[zcIndex].isEmpty()) {
						continue;
					}

					double marketValue = Double.parseDouble(tokens[mvIndex]);
					double totalLivableArea = Double.parseDouble(tokens[tlaIndex]);
					int zipCode = Integer.parseInt(tokens[zcIndex].substring(0, 5));

					Property property = new Property(zipCode, marketValue, totalLivableArea);

					propertyList.add(property);

				} catch (IndexOutOfBoundsException e) {
					// do nothing. skip the line.
				} catch (NumberFormatException e) {
					// do nothing. skip the line.
				}

			}
			return propertyList;

		} catch (FileNotFoundException e) {
			System.out.println("Property file: \"" + fileName + "\" does not exist. Terminating Program...");
			System.exit(0);
		} catch (SecurityException e) {
			System.out.println("No permission to the Property file: \"" + fileName + "\". Terminating Program...");
			System.exit(0);
		}

		return null;
	}

	// main method below is for debugging/testing purpose
//	public static void main(String[] args) {
//		List<Property> list = new ArrayList<Property>();
//		PropertyReader r = new PropertyReader("properties.csv");
//		list = r.read();
//
//		for (Property property : list) {
//			System.out.println(property.getZipCode() + " " + property.getMarketValue() + " " + property.getTotalLivableArea());
//		}
//	}

}
