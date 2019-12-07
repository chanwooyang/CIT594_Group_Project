package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.PropertyReader;

public class PropertyProcessor {
	
	protected PropertyReader propReader;
	protected ArrayList<Property> allPropertiesAL;
	protected HashMap<Integer, ArrayList<Property>> allPropertiesHM;
	
	public PropertyProcessor (PropertyReader propReader) {
		this.propReader = propReader;
		allPropertiesAL = (ArrayList<Property>) propReader.read();
	}
	
	public int action3 (int zipCode) {
		return actionHelper(zipCode, new AggregatorMarketValue());
	}
	
	public int action4 (int zipCode) {
		return actionHelper(zipCode, new AggregatorArea());
	}
	
	private int actionHelper (int zipCode, AggregatorProperty attributeToSum) {
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
	
	private boolean isValidZipCode(int zipCode) {
		if (zipCode)
		
		return false;
		
	}
	
}
