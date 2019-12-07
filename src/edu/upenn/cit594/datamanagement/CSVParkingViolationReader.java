package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.*;

import edu.upenn.cit594.data.*;

public class CSVParkingViolationReader extends ParkingViolationReader {

	public CSVParkingViolationReader(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ParkingViolation> read() {

		ArrayList<ParkingViolation> ticketList = new ArrayList<ParkingViolation>();

		try {
			FileReader csv = new FileReader(fileName);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(csv);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] tokens = line.split(",", -1);

				try {
					// Check if fine, license plate, zip code data are missing
					if (tokens[1].strip().isEmpty() || tokens[4].strip().isEmpty() || tokens[6].strip().isEmpty()) {
						continue;
					}

//					String timeStamp = tokens[0].strip();
					int fineUSD = Integer.parseInt(tokens[1].strip());
//					String violDesc = tokens[2].strip();
//					int vehID = Integer.parseInt(tokens[3].strip());
					String licensePlate = tokens[4].strip();
//					int violID = Integer.parseInt(tokens[5].strip());

					int zipCode = Integer.parseInt(tokens[6].strip());

					ParkingViolation currentTicket = new ParkingViolation(fineUSD, licensePlate, zipCode);

					ticketList.add(currentTicket);

				} catch (IndexOutOfBoundsException e) {
					// do nothing. skip the line.
				} catch (NumberFormatException e) {
					// do nothing. skip the line.
				}

			}

			return ticketList;

		} catch (FileNotFoundException e) {
			System.out.println("ParkingViolation csv file does not exist. Terminating Program...");
		} catch (SecurityException e) {
			System.out.println("No permission to the ParkingViolation csv file. Terminating Program...");
		}

		return null;
	}

	// main method below is for debugging/testing purpose
	public static void main(String[] args) {
		ParkingViolationReader r = new CSVParkingViolationReader("parking.csv");
		List<ParkingViolation> ticketList = new ArrayList<ParkingViolation>();
		ticketList = r.read();

		for (ParkingViolation ticket : ticketList) {
			System.out.println(ticket.getFineUSD() + " " + ticket.getLicensePlate() + " " + ticket.getZipCode());
		}
	}
}