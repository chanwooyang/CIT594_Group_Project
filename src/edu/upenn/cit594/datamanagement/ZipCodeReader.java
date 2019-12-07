package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.*;

import edu.upenn.cit594.data.*;

public class ZipCodeReader {

	private String fileName;

	public ZipCodeReader(String fileName) {
		this.fileName = fileName;
	}

	public Map<Integer, Integer> read() {

		HashMap<Integer, Integer> popPerZip = new HashMap<Integer, Integer>();

		try {
			FileReader popTxtFile = new FileReader(fileName);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(popTxtFile);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] tokens = line.split(" ",-1);
				if (tokens.length != 2)
					continue; // check if both zipcode and pop data are present

				try {

					int zipCode = Integer.parseInt(tokens[0].strip());
					int population = Integer.parseInt(tokens[1].strip());

					popPerZip.put(zipCode, population);

				} catch (IndexOutOfBoundsException e) {
					// do nothing. skip the line.
				} catch (NumberFormatException e) {
					// do nothing. skip the line.
				}
			}
			
			return popPerZip;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
