package edu.upenn.cit594;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
	
	static String logName;
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Incorrect number of arguments");
			System.exit(0);
		}
		
		// exit, if format of tweets file is not json or txt
		String format = args[0].toLowerCase();
		if (!format.equals("json") && !format.equals("text")) {
			System.out.println("Incorrect Tweet File Type. Not json or txt");
			System.exit(0);
		}
		
		// exit, if Tweets file cannot be used
		String tweetFileName = args[1];
		try {
			BufferedReader br = new BufferedReader(new FileReader(tweetFileName));
			br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("Could not find Tweets File or it is locked.");
			e.printStackTrace();
		}

		// exit, if States file cannot be used
		String stateFileName = args[2];
		try {
			BufferedReader br = new BufferedReader(new FileReader(stateFileName));
			br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("Could not find States File or it is locked.");
			e.printStackTrace();
		}
		
		// set the name of the log file for Logger
		setLogName(args[3]);
		
		// running the program

	}	
	
	public static void setLogName(String name) {
		logName = name;
	}
	
	public static String getLogName() {
		return logName;
	}
}
