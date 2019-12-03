package edu.upenn.cit594;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.ui.UserInterface;


public class Main {
	
	static String logName;
	private final static int NUM_ARGUMENTS = 5;
	
	public static void main(String[] args) {
		if (args.length != NUM_ARGUMENTS) {
			System.out.println("Incorrect number of arguments");
			System.exit(0);
		}
		
		// When the program starts, it should write the current time followed by the runtime arguments.
		Logger lg = Logger.getInstance();
		lg.log(lg.getTime() + " " + args[0] + " " + args[1] + " "  + args[2] + " " + args[3] + " "  + args[4]);
		
		// exit, if format of parking violations file is not json or txt
		String format = args[0].toLowerCase();
		if (!format.equals("json") && !format.equals("csv")) {
			System.out.println("Incorrect Parking Violations File Type. Not json or csv");
			System.exit(0);
		}
		
		// exit, if parking violations file cannot be used
		String parkVioFileName = args[1];
		try {
			// whenever an input file is opened for reading, the program should "log it."
			lg.log(lg.getTime() + " "  + parkVioFileName);
			BufferedReader br = new BufferedReader(new FileReader(parkVioFileName));
			br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("Could not find Parking Violations File or it is locked.");
			e.printStackTrace();
		}

		// exit, if property values file cannot be used
		String propValFileName = args[2];
		try {
			// whenever an input file is opened for reading, the program should "log it."
			lg.log(lg.getTime() + " "  + propValFileName);
			BufferedReader br = new BufferedReader(new FileReader(propValFileName));
			br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("Could not find Property Values File or it is locked.");
			e.printStackTrace();
		}
		
		//exit, if population file cannot be used
		String popFileName = args[3];
		try {
			// whenever an input file is opened for reading, the program should "log it."
			lg.log(lg.getTime() + " "  + popFileName);
			BufferedReader br  = new BufferedReader( new FileReader(popFileName));
			br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("Could not find Population File or it is locked.");
			e.printStackTrace();
		}
		
		
		// set the name of the log file for Logger
		setLogName(args[4]);
		
		// running the program
		
		
		
		ParkingViolationReader rd = null;
		if (format.equals("csv")) {
			rd = new CSVParkingViolationReader(parkVioFileName);
		}
		else if (format.equals("json")) { 
			rd = new JSONParkingViolationReader(parkVioFileName);
		}
		PropertyReader propCSV = new PropertyReader (propValFileName);
		PopulationReader popText = new PopulationReader (popFileName);

		PopulationProcessor popProc = new PopulationProcessor(popText);
		PropertyProcessor propProc = new PropertyProcessor(propCSV);
		ParkingViolationProcessor pvProc = new ParkingViolationProcessor(rd);
		UserInterface ui = new UserInterface(popProc, propProc, pvProc);
		
		ui.promptUser();
	}	
	
	public static void setLogName(String name) {
		logName = name;
	}
	
	public static String getLogName() {
		return logName;
	}
}
