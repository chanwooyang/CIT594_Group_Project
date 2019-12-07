package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class AggregatorMarketValue implements AggregatorProperty {

	@Override
	public int getValue(Property prop) {
		return (int) prop.getMarketValue();
	}

}
