package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class AggregatorMarketValue implements AggregatorProperty {
	
//	private HashMap<Integer, Integer> resultsMV = new HashMap<Integer, Integer>();
	
	@Override
	public int getValue(Property prop) {
		return (int) prop.getMarketValue();
	}
	
	

}
