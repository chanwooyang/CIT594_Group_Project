package edu.upenn.cit594.data;

public class Property {
	
	private int zipCode;
	private double marketValue, totalLivableArea;
	
	public Property(int zipCode, double marketValue, double totLivableArea) {
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totLivableArea;
	}

	public int getZipCode() {
		return zipCode;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}

}
