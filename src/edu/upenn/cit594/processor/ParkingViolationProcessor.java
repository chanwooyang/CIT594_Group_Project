package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.data.*;

public class ParkingViolationProcessor {

	private ParkingViolationProcesorAbstract processor;
	private String fileName;
	// Memoization for #2: HashMap<fileName, totalFinePerCapita>
	private HashMap<String, Map<Integer, Double>> totalFinePerCapitaMemo = new HashMap<>();
	private HashMap<String, Map<Integer, Double>> finePerZipMemo = new HashMap<>();

	public ParkingViolationProcessor(String fileFormat, String fileName) {
		this.processor = getProcessor(fileFormat, fileName);
	}

	private ParkingViolationProcesorAbstract getProcessor(String fileFormat, String fileName) {
		this.fileName = fileName;

		if (fileFormat.equals("csv")) {
			return new CSVParkingViolationProcessor(fileName);
		} else if (fileFormat.equals("json")) {
			return new JSONParkingViolationProcessor(fileName);
		}

		return null;
	}

	private List<ParkingViolation> getParkingViolationList() {

		List<ParkingViolation> tickets = new ArrayList<ParkingViolation>();
		tickets = this.processor.process();

		return tickets;
	}

	private Map<Integer, Double> calculatetoalFinesPerZip() {
		Map<Integer, Double> finePerZip = new HashMap<Integer, Double>();

		List<ParkingViolation> parkingViolations = new ArrayList<ParkingViolation>();
		parkingViolations = this.getParkingViolationList();
		
		for (ParkingViolation ticket : parkingViolations) {
			int currentZip = ticket.getZipCode();
			int currentFine = ticket.getFineUSD();
			String licensePlate = ticket.getLicensePlate();

			// check if a vehicle is from PA
			if (!licensePlate.equals("PA"))
				continue;

			if (finePerZip.containsKey(currentZip)) {
				double accumFine = finePerZip.get(currentZip);
				accumFine += currentFine;
				finePerZip.put(currentZip, accumFine);
			} else {
				double accumFine = currentFine;
				finePerZip.put(currentZip, accumFine);
			}
		}
		
		return finePerZip;
		
	}

	private Map<Integer, Double> calculateTotalFinesPerCapita(Map<Integer, Integer> popZipMap) {

		Map<Integer, Double> finePerZip = new HashMap<Integer, Double>();
		Map<Integer, Double> finePerCapita = new TreeMap<Integer, Double>();

		// memoization
		String fileName = this.fileName.split("\\.")[0];
		if (finePerZipMemo.containsKey(fileName)) {
			finePerZip = finePerZipMemo.get(fileName);
		} else {
			finePerZip = this.calculatetoalFinesPerZip();
			finePerZipMemo.put(fileName, finePerZip);
		}
		

		for (Integer zip : popZipMap.keySet()) {
			if (finePerZip.containsKey(zip)) {
				double accumFine = finePerZip.get(zip);
				if (accumFine < 0.0001 && accumFine > -0.0001) {
					continue;
				}
				int populationInZip = popZipMap.get(zip);

				double avgFine = accumFine / populationInZip;
				finePerCapita.put(zip, avgFine);
			}
		}
		return finePerCapita;
	}
	
	/**
	 * #2 Total Fines Per Capita
	 * 
	 * @param popZipMap - population-ZipCode Mapping data
	 */
	public Map<Integer, Double> getTotalFinesPerCapita(Map<Integer, Integer> popZipMap) {
		Map<Integer, Double> finePerCapita = new TreeMap<>();
		String fileName = this.fileName.split("\\.")[0];

		// memoization
		if (totalFinePerCapitaMemo.containsKey(fileName)) {
			finePerCapita = totalFinePerCapitaMemo.get(fileName);
		} else {
			finePerCapita = this.calculateTotalFinesPerCapita(popZipMap);
			totalFinePerCapitaMemo.put(fileName, finePerCapita);
		}

		return finePerCapita;
	}

	/**
	 * #6 Additional Feature
	 * 
	 * @param popZipMap
	 * @return
	 */
	public TreeMap<Integer, Double> getTotalFineToAvgMarketValuePerZip(Map<Integer, Integer> popZipMap, PropertyProcessor pp) {
		Map<Integer, Double> finePerZip = new TreeMap<>();
		String fileName = this.fileName.split("\\.")[0];
		TreeMap<Integer, Double> answerHM = new TreeMap<Integer, Double>();
		

		// memoization
		if (finePerZipMemo.containsKey(fileName)) {
			finePerZip = finePerZipMemo.get(fileName);
		} else {
			finePerZip = this.calculatetoalFinesPerZip();
			finePerZipMemo.put(fileName, finePerZip);
		}
		
		//TODO: implement rest of algorithms
		TreeMap<Integer, Double> finePerCapTM = (TreeMap<Integer, Double>) getTotalFinesPerCapita(popZipMap);
		
		for (Map.Entry<Integer, Double> entry : finePerCapTM.entrySet()) {
			// use PropertyProcessor with ZIP Code to get the average Market Value for that ZIP Code
			int zipCode = entry.getKey(); 
			int avMV = pp.getAvMarketValue(zipCode);
			double ratio = 0;
			if (avMV != 0) {
				ratio = entry.getValue() / avMV * 100000;
			}
			answerHM.put(zipCode, ratio);
		}
		return answerHM;
		
	}

	// main method below is for debugging/testing purpose
//	public static void main(String[] args) {
//		ParkingViolationProcessor p = new ParkingViolationProcessor("csv", "parking.csv");
//		List<ParkingViolation> pl = new ArrayList<ParkingViolation>();
//		ZipCodeProcessor zp = new ZipCodeProcessor("population.txt");
//		Map<Integer, Integer> zl = new HashMap<Integer, Integer>();
//		Map<Integer, Double> fpc = new HashMap<>();
//		zl = zp.process();
//		pl = p.getParkingViolationList();
//		fpc = p.getTotalFinesPerCapita(zl);
//		for (Integer zip : fpc.keySet()) {
//			System.out.format(zip + " %.4f\n", fpc.get(zip));
//		}
//	}
}
