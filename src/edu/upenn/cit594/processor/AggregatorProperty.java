package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public interface AggregatorProperty {
	
	public abstract int getValue(Property attribute);
	
}
