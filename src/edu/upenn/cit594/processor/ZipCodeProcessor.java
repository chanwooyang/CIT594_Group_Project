package edu.upenn.cit594.processor;

import java.util.HashMap;
import edu.upenn.cit594.datamanagement.ZipCodeReader;

public class ZipCodeProcessor {
	
	protected ZipCodeReader zcReader;
	protected HashMap<Integer, Integer> allZipCodes;
	
	public ZipCodeProcessor (ZipCodeReader zcReader) {
		this.zcReader = zcReader;
		allZipCodes = (HashMap<Integer, Integer>) zcReader.read();
	}
	
	public int totalPopulation() {
		// to do
		return -5;
	}
	
}
