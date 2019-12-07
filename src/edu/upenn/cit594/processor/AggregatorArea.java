package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class AggregatorArea implements AggregatorProperty {

	@Override
	public int getValue(Property attribute) {
		return (int) attribute.getTotalLivableArea();
	}

}
