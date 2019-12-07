package edu.upenn.cit594.datamanagement;

import java.util.*;

import edu.upenn.cit594.data.*;

public abstract class ParkingViolationReader {
	
	protected String fileName;
	
	public ParkingViolationReader(String fileName) {
		this.fileName = fileName;
	}
	
	public abstract List<ParkingViolation> read();
	
}
