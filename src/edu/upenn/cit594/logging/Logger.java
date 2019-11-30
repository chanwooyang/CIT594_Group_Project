package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.upenn.cit594.Main;

public class Logger {
	
	private PrintWriter out;
	public static String logFileName = Main.getLogName();
	
	// singleton instance
	private static Logger instance = new Logger();
	
	private Logger() {
		try {
			out = new PrintWriter (new File(logFileName));
		} catch (FileNotFoundException e) {
			System.out.println("Log file was not found. Check log name in Main.java");
			e.printStackTrace();
		}
	}
	
	public static Logger getInstance() {
		return instance;
	}
	
	// non-static method for other classes to use
	public void log(String text) {
		out.println(text);
		out.flush();
	}
	
	public String getTime() {
		String timestamp = Long.toString(System.currentTimeMillis());
		
		// MUST add the whitespace
		
		return timestamp;
	}
}
