package edu.upenn.cit594.data;

import java.util.*;

public class ZipCode {

	private Map<Integer, Integer> popPerZipcode;

	public ZipCode(Map<Integer, Integer> popPerZip) {
		this.popPerZipcode = popPerZip;
	}

	public Map<Integer, Integer> getPopulPerZipCodeMap() {
		return popPerZipcode;
	}

	public int getPopulation(int zipCode) {

		try {
			int population = popPerZipcode.get(zipCode);
			return population;
		} catch (NullPointerException e) {
			return -1; // indication of null
		}

	}

}
