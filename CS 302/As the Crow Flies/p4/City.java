///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  AsTheCrowFlies
// File:             City.java
// Semester:         CS302 Spring 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  LeGault
// Lab Section:      335
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner: N/A    
// Email:            N/A
// CS Login:         N/A
// Lecturer's Name:  N/A
// Lab Section:      N/A
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////

/**
 * Instantiable Class for City objects describing cities and their coordinates
 *
 * @author Drew Eklund
 */
public class City {

	// Data Fields
	private final String STATE;
	private final String CITY;
	private final double LAT;
	private final double LONG;

	/**
	 * Creates a city object given 2 strings and 2 doubles.
	 *
	 * @param String
	 * @param String
	 * @param double
	 * @param double
	 */
	public City(String state, String city, double lat, double lon) {
		this.STATE = state.toUpperCase();
		this.CITY = city.toUpperCase();
		this.LAT = lat;
		this.LONG = lon;
	}

	/**
	 * Converts trip details to a String and returns it.
	 * 
	 * @return String
	 */
	public String toString() {
		return this.STATE + "," + this.CITY + "," + this.LAT + "," + this.LONG;
	}

	/**
	 * Gets the distance between two cities given their geographical coordiantes
	 * using the Haversine formula.
	 *
	 * @param City
	 * @return double
	 */
	public double getDistance(City that) {
		// Simplified variables for Haversine Formula
		double lat1 = Math.toRadians(this.LAT);
		double lat2 = Math.toRadians(that.LAT);
		double latChange = lat2 - lat1;
		double longChange = Math.toRadians(that.LONG)
				- Math.toRadians(this.LONG);
		// Haversine Formula
		double a = Math.pow(Math.sin(latChange / 2), 2);
		double b = a + Math.cos(lat1) * Math.cos(lat2)
				* Math.pow(Math.sin(longChange / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(b), Math.sqrt(1 - b));
		double distance = 6371 * c;
		return distance;
	}

	/**
	 * Accesses a city's name.
	 * 
	 * @return String
	 */
	public String getName() {
		return this.CITY;
	}
}
