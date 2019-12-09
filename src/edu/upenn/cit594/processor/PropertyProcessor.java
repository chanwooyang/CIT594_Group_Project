package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.PropertyReader;

public class PropertyProcessor {
	
	protected PropertyReader propReader;
	protected ArrayList<Property> allPropertiesAL;
//	protected HashMap<Integer, ArrayList<Property>> allPropertiesHM;
	private HashMap<Integer, Integer> resultsMV = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> resultsArea = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> resultsMVperCap = new HashMap<Integer, Integer>();
	
	public ArrayList<Property> getAllPropertiesAL() {
		return allPropertiesAL;
	}

	public PropertyProcessor (PropertyReader propReader) {
		this.propReader = propReader;
		allPropertiesAL = (ArrayList<Property>) propReader.read();
	}
	
	public int getAvMarketValue(int zipCode) {
		if (resultsMV.containsKey(zipCode)) {
			return resultsMV.get(zipCode);
		}
		else {
			int answer = action3(zipCode);
			resultsMV.put(zipCode, answer);
			return answer;
		}
	}
	
	public int getAvTotalLivableArea(int zipCode) {
		if (resultsArea.containsKey(zipCode)) {
			return resultsArea.get(zipCode);
		}
		else {
			int answer = action4(zipCode);
			resultsArea.put(zipCode, answer);
			return answer;
		}
	}
	
	private int action3 (int zipCode) {
		return actionHelper(zipCode, new AggregatorMarketValue());
	}
	
	private int action4 (int zipCode) {
		return actionHelper(zipCode, new AggregatorArea());
	}
	
	private int actionHelper (int zipCode, AggregatorProperty attributeToSum) {
		if (isValidZipCode(zipCode)) {
			int attributeTotal = 0;
			int count = 0;
			for (Property prop : allPropertiesAL) {
				if (prop.getZipCode() == zipCode) {
					attributeTotal += attributeToSum.getValue(prop);
					count++;
				}
			}
			if (count == 0) {
				return 0;
			}
			else {
				return attributeTotal / count ;
			}
		}
		else {return 0;}
	}
	
	private boolean isValidZipCode(int zipCode) {
		// if zip code is not valid (does not contain 5 digits) or is not listed in the input files.
		if (zipCode > 99999 || zipCode < 10000) {
			
			return false;
		}
		else {
			return true;
		}
	}
	
	public int getTotalMarketValuePerCapita(int zipCode, ZipCodeProcessor zcProc) {
		if (resultsMVperCap.containsKey(zipCode)) {
			return resultsMVperCap.get(zipCode);
		}
		else {
			int answer5 = action5(zipCode, zcProc);
			resultsMVperCap.put(zipCode, answer5);
			return answer5;
		}
	}

	// total market value for all properties in the ZIP Code,
	// divided by the population of the ZIP Code.
	private int action5(int zipCode, ZipCodeProcessor zcProc) {
		if (isValidZipCode(zipCode)) {
			int pop;
			HashMap<Integer, Integer> zipPopHM = (HashMap<Integer, Integer>) zcProc.process();
			if (zipPopHM.get(zipCode) != null) {
				pop = zipPopHM.get(zipCode);
			}
			else {return 0;}
			int attributeTotal = 0;
			if (pop == 0) { return 0;}
			else {
				for (Property prop : allPropertiesAL) {
					if (prop.getZipCode() == zipCode) {
						attributeTotal += prop.getMarketValue();
					}
				}
			}
			return attributeTotal / pop ;
			}
		else {return 0;}
	}
	
//	public static void main(String[] args) {
//		int zipCode = 19147;
//		int zipCode2 = 19148;
//		PropertyReader pr = new PropertyReader("propertiesshort.csv");
////		ArrayList<Property> pl = (ArrayList<Property>) pr.read();
//		PropertyProcessor pp = new PropertyProcessor(pr);
//		int answer = pp.getAvMarketValue(zipCode);
//		System.out.println("Average Residential Market Value (#3) of " + zipCode + " is " + answer);
//		int answer2 = pp.getAvTotalLivableArea(zipCode);
//		System.out.println("Average Living Area (#4) of " + zipCode + " is " + answer2);
//	}
	
}
