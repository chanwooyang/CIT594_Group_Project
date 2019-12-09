package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.datamanagement.*;

public class ZipCodeProcessor {

	protected ZipCodeReader reader;
	private String fileName;
	// for memoization: HashMap<fileName, totalPopulation>
	private HashMap<String, Integer> totalPopulationMemo = new HashMap<>(); 

	public ZipCodeProcessor(String fileName) {
		this.fileName = fileName;
		this.reader = createReader(fileName);
	}

	public ZipCodeReader createReader(String fileName) {
		return new ZipCodeReader(fileName);
	}

	public Map<Integer, Integer> process() {
		Map<Integer, Integer> data = new HashMap<Integer, Integer>();
		data = this.reader.read();
		return data;
	}

	/**
	 * #1 Total Population for All ZIP Codes
	 */
	public int getTotalPopulationAllZip() {
		int totalPop = 0;
//		String fileName = this.fileName.split("\\.")[0];
		
		if (totalPopulationMemo.containsKey("1")) {
			totalPop = totalPopulationMemo.get("1");
		} else {
			totalPop = this.calculateTotalPopulationAllZip();
			totalPopulationMemo.put("1", totalPop);
		}

		return totalPop;
	}

	private int calculateTotalPopulationAllZip() {
		Map<Integer, Integer> popZipMap = new HashMap<Integer, Integer>();
		popZipMap = this.process();

		int totalPop = 0;
		for (Integer zip : popZipMap.keySet()) {
			totalPop += popZipMap.get(zip);
		}
		
		return totalPop;

	}
	

	// main method below is for debugging/testing purpose
//	public static void main(String[] args) {
//		ZipCodeProcessor p = new ZipCodeProcessor("population.txt");
//		int totalPop = p.getTotalPopulationAllZip();
//		System.out.println("Total Population for all ZIP codes: " + totalPop);
//	}
}
