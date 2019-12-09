package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

public abstract class ParkingViolationProcesorAbstract {

	protected ParkingViolationReader reader;
	
	public ParkingViolationProcesorAbstract(String fileName) {
		this.reader = createReader(fileName);
	}
	
	public abstract ParkingViolationReader createReader(String fileName);
	
	public List<ParkingViolation> process() {
		List<ParkingViolation> data = reader.read();
		return data;
	}
}
