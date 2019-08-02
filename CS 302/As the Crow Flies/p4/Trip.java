///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  AsTheCrowFlies
// File:             Trip.java
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

import java.util.*;

/**
 * Instantiable Class for Trip objects containing an arraylist of cities with
 * methods for modifying the list and calculating distance.
 *
 * @author Drew Eklund
 */
public class Trip {
	// Trip data field
	private ArrayList<City> trip;

	/**
	 * Creates a new Trip object which is just an empty ArrayList of cities.
	 */
	public Trip() {
		this.trip = new ArrayList<City>();
	}

	/**
	 * Adds a city to trip.
	 * 
	 * @param City
	 */
	public void addCity(City city) {
		this.trip.add(city);
	}

	/**
	 * Calculates total distance for a trip.
	 *
	 * @return double
	 */
	public double totalDistance() {
		double totalDistance = 0;
		for (int k = 1; k < trip.size(); k++) {
			totalDistance += trip.get(k).getDistance(trip.get(k - 1));
		}
		totalDistance += trip.get(0).getDistance(trip.get(trip.size() - 1));
		return totalDistance;
	}

	/**
	 * Converts Trip details to a string and returns it.
	 * 
	 * @return String
	 */
	public String toString() {
		String info = "There are " + trip.size() + " cities in this trip.\n";
		for (int k = 1; k < trip.size(); k++) {
			double distance = trip.get(k).getDistance(trip.get(k - 1));
			info += trip.get(k - 1).getName() + " to " + trip.get(k).getName()
					+ " as the crow flies is about " + (int) (distance * 1000)
					+ " meters (~" + (int) (distance * 6.2 / 10) + " miles)\n";
		}
		double distance = trip.get(0).getDistance(trip.get(trip.size() - 1));
		info += trip.get(trip.size() - 1).getName() + " to "
				+ trip.get(0).getName() + " as the crow flies is about "
				+ (int) (distance * 1000) + " meters (~"
				+ (int) (distance * 6.2 / 10) + " miles)\n";
		info += "Total Distance: " + (int) (this.totalDistance() * 1000)
				+ " meters (~" + (int) (this.totalDistance() * 6.2 / 10)
				+ " miles)";
		return info;
	}

	/**
	 * Checks if a trip has enough cities to be valid.
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		if (this.trip.size() < 2) {
			return false;
		}
		return true;
	}

}
