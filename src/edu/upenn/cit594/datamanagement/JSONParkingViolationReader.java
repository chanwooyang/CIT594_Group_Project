package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;

import edu.upenn.cit594.data.*;

public class JSONParkingViolationReader extends ParkingViolationReader{

	public JSONParkingViolationReader(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ParkingViolation> read() {
		
		JSONParser parser = new JSONParser();
		ArrayList<ParkingViolation> ticketList = new ArrayList<ParkingViolation>();
		
		try {
			JSONArray tickets = (JSONArray) parser.parse(new FileReader(fileName));
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iter = tickets.iterator();
			
			while(iter.hasNext()) {
				JSONObject ticket = (JSONObject) iter.next();
				
				String zipCodeStr = ticket.get("zip_code").toString().strip();
				String fineStr = ticket.get("fine").toString().strip();
				String licensePlate = ticket.get("state").toString().strip();
				
				if (zipCodeStr.isEmpty() || fineStr.isEmpty() || licensePlate.isEmpty()) {
					continue;
				}
				
				int fineUSD = Integer.parseInt(fineStr);
				int zipCode = Integer.parseInt(zipCodeStr);
				
				ParkingViolation currentTicket = new ParkingViolation(fineUSD, licensePlate, zipCode);

				ticketList.add(currentTicket);
				
			}
			
			return ticketList;

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// main method below is for debugging/testing purpose
	public static void main(String[] args) {
		ParkingViolationReader r = new JSONParkingViolationReader("parking.json");
		List<ParkingViolation> ticketList = new ArrayList<ParkingViolation>();
		ticketList = r.read();

		for (ParkingViolation ticket : ticketList) {
			System.out.println(ticket.getFineUSD() + " " + ticket.getLicensePlate() + " " + ticket.getZipCode());
		}
	}

}
