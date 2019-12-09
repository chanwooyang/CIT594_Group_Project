package edu.upenn.cit594;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.processor.ZipCodeProcessor;
import edu.upenn.cit594.ui.UserInterface;


public class Main {
	
	static String logName;
	private final static int NUM_ARGUMENTS = 5;
	
	public static void main(String[] args) {
		if (args.length != NUM_ARGUMENTS) {
			System.out.println("Incorrect number of arguments");
			System.exit(0);
		}
		
		// set the name of the log file for Logger
		setLogName(args[4]);
		
		// When the program starts, it should write the current time followed by the runtime arguments.
		Logger lg = Logger.getInstance();
		lg.log(lg.getTime() + " " + args[0] + " " + args[1] + " "  + args[2] + " " + args[3] + " "  + args[4]);
		
		// exit, if format of parking violations file is not json or txt
		String format = args[0];
		if (!format.equals("json") && !format.equals("csv")) {
			System.out.println("Incorrect Parking Violations File Type. Not json or csv");
			System.exit(0);
		}
		
		// exit, if parking violations file cannot be used
		String parkVioFileName = args[1];
		// could also use the canRead() method.
		try {
			// whenever an input file is opened for reading, the program should "log it."
			lg.log(lg.getTime() + " "  + parkVioFileName);
			BufferedReader br1 = new BufferedReader(new FileReader(parkVioFileName));
			br1.readLine();
			br1.close();
		} catch (IOException e) {
			System.out.println("Could not find Parking Violations File or it is locked.");
			e.printStackTrace();
			System.exit(0);
		}

		// exit, if property values file cannot be used
		String propValFileName = args[2];
		// could also use the canRead() method.
		try {
			// whenever an input file is opened for reading, the program should "log it."
			lg.log(lg.getTime() + " "  + propValFileName);
			BufferedReader br2 = new BufferedReader(new FileReader(propValFileName));
			br2.readLine();
			br2.close();
		} catch (IOException e) {
			System.out.println("Could not find Property Values File or it is locked.");
			e.printStackTrace();
			System.exit(0);
		}
		
		//exit, if population file cannot be used
		String popFileName = args[3];
		// could also use the canRead() method.
		try {
			// whenever an input file is opened for reading, the program should "log it."
			lg.log(lg.getTime() + " "  + popFileName);
			BufferedReader br3  = new BufferedReader( new FileReader(popFileName));
			br3.readLine();
			br3.close();
		} catch (IOException e) {
			System.out.println("Could not find Population File or it is locked.");
			e.printStackTrace();
			System.exit(0);
		}
		
		
		// running the program
		
		
		// the ParkingViolationProcessor creates the ParkingViolationReaders!
//		ParkingViolationReader rd = null;
//		if (format.equals("csv")) {
//			rd = new CSVParkingViolationReader(parkVioFileName);
//		}
//		else if (format.equals("json")) { 
//			rd = new JSONParkingViolationReader(parkVioFileName);
//		}
		
		// the ZipCodeProcessor creates the ZipCodeReader!
//		ZipCodeReader popText = new ZipCodeReader (popFileName);
		ZipCodeProcessor popProc = new ZipCodeProcessor(popFileName);
		PropertyReader propCSV = new PropertyReader (propValFileName);
		PropertyProcessor propProc = new PropertyProcessor(propCSV);
		ParkingViolationProcessor pvProc = new ParkingViolationProcessor(format, parkVioFileName);
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
