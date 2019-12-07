package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.data.*;

public class ParkingViolationProcessor {

	private ParkingViolationProcesorAbstract processor;
	private Map<Integer, Double> finePerCapitaMemo; // for memoization

	public ParkingViolationProcessor(String fileFormat, String fileName) {
		this.processor = getProcessor(fileFormat, fileName);
	}

	private ParkingViolationProcesorAbstract getProcessor(String fileFormat, String fileName) {
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

	/**
	 * #2 Total Fines Per Capita
	 * @param popZipMap - population-ZipCode Mapping data
	 */
	public void getTotalFinesPerCapita(Map<Integer, Integer> popZipMap) {
		Map<Integer, Double> finePerCapita = new HashMap<>();
		
		if (false) {
			
		} else {
			finePerCapita = this.calculateTotalFinesPerCapita(popZipMap);
		}
		
		for (Integer zip : finePerCapita.keySet()) {
			System.out.format(zip + " %.4f\n", finePerCapita.get(zip));
		}
	}
	
	private Map<Integer, Double> calculateTotalFinesPerCapita(Map<Integer, Integer> popZipMap) {

		HashMap<Integer, Double> finePerZip = new HashMap<Integer, Double>();
		HashMap<Integer, Double> finePerCapita = new HashMap<Integer, Double>();

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

	public static void main(String[] args) {
		ParkingViolationProcessor p = new ParkingViolationProcessor("csv", "parking.csv");
		List<ParkingViolation> pl = new ArrayList<ParkingViolation>();
		ZipCodeProcessor zp = new ZipCodeProcessor("population.txt");
		Map<Integer, Integer> zl = new HashMap<Integer, Integer>();
		zl = zp.process();
		pl = p.getParkingViolationList();
		p.getTotalFinesPerCapita(zl);
	}

}
