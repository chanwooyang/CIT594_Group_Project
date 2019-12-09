package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.*;

public class CSVParkingViolationProcessor extends ParkingViolationProcesorAbstract {

	public CSVParkingViolationProcessor(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ParkingViolationReader createReader(String fileName) {

		return new CSVParkingViolationReader(fileName);
	}

}
