package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.*;

public class JSONParkingViolationProcessor extends ParkingViolationProcesorAbstract{

	public JSONParkingViolationProcessor(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ParkingViolationReader createReader(String fileName) {
		
		return new JSONParkingViolationReader(fileName);
	}

}
