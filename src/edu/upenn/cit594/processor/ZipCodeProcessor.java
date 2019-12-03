package edu.upenn.cit594.processor;

public class ZipCodeProcessor {
	
	protected ZipCodeReader zcReader;
	protected Set<ZipCode> allZipCodes;
	
	public ZipCodeProcessor (ZipCodeReader zcReader) {
		this.zcReader = zcReader;
		allZipCodes = zcReader.getPopulation();
	}
	
	public int totalPopulation() {
		
	}
}
