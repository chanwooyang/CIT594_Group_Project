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
					if (tokens[1].isEmpty() || tokens[4].isEmpty() || tokens[6].isEmpty()) {
						continue;
					}

//					String timeStamp = tokens[0];
					int fineUSD = Integer.parseInt(tokens[1]);
//					String violDesc = tokens[2];
//					int vehID = Integer.parseInt(tokens[3]);
					String licensePlate = tokens[4];
//					int violID = Integer.parseInt(tokens[5]);

					int zipCode = Integer.parseInt(tokens[6]);

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
			System.out.println("ParkingViolation file: \"" + fileName + "\" does not exist. Terminating Program...");
			System.exit(0);
		} catch (SecurityException e) {
			System.out.println("No permission to the ParkingViolation file: \"" + fileName + "\". Terminating Program...");
			System.exit(0);
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
