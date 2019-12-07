package edu.upenn.cit594.data;

/**
 * @author chanwooyang
 * Parkingviolation Data Class
 * Currently using only "fine", "license plate", and "zip code" data.
 * If more data column are used, instance variables can be added.
 */
public class ParkingViolation {

	private String licensePlate;
	private int fineUSD, zipCode;
	
	public ParkingViolation(int fineUSD, String licensePlate, int zipCode) {
		this.fineUSD = fineUSD;
		this.licensePlate = licensePlate;
		this.zipCode = zipCode;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public int getFineUSD() {
		return fineUSD;
	}

	public int getZipCode() {
		return zipCode;
	}

	
}
