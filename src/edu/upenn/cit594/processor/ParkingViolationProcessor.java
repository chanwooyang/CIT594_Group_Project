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
		Map<Integer, Double> finePerCapita = new HashMap<Integer, Double>();

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
	public void getTotalFinesPerCapita(Map<Integer, Integer> popZipMap) {
		Map<Integer, Double> finePerCapita = new HashMap<>();
		String fileName = this.fileName.split("\\.")[0];

		// memoization
		if (totalFinePerCapitaMemo.containsKey(fileName)) {
			finePerCapita = totalFinePerCapitaMemo.get(fileName);
		} else {
			finePerCapita = this.calculateTotalFinesPerCapita(popZipMap);
			totalFinePerCapitaMemo.put(fileName, finePerCapita);
		}

		for (Integer zip : finePerCapita.keySet()) {
			System.out.format(zip + " %.4f\n", finePerCapita.get(zip));
		}
	}

	/**
	 * #6 Additional Feature
	 * 
	 * @param popZipMap
	 * @return
	 */
	public double getTotalFineToAvgMarketValuePerZip(Map<Integer, Integer> popZipMap) {
		Map<Integer, Double> finePerZip = new HashMap<>();
		String fileName = this.fileName.split("\\.")[0];

		// memoization
		if (finePerZipMemo.containsKey(fileName)) {
			finePerZip = finePerZipMemo.get(fileName);
		} else {
			finePerZip = this.calculatetoalFinesPerZip();
			finePerZipMemo.put(fileName, finePerZip);
		}
		
		//TODO: implement rest of algorithms
		
		return 0.0;
		
	}

//	public static void main(String[] args) {
//		ParkingViolationProcessor p = new ParkingViolationProcessor("csv", "parking.csv");
//		List<ParkingViolation> pl = new ArrayList<ParkingViolation>();
//		ZipCodeProcessor zp = new ZipCodeProcessor("population.txt");
//		Map<Integer, Integer> zl = new HashMap<Integer, Integer>();
//		zl = zp.process();
//		pl = p.getParkingViolationList();
//		p.getTotalFinesPerCapita(zl);
//	}

}
