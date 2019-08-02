///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  Scheduler.java
// File:             Event.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An implementation of the Interval interface which contains information about
 * an event for an existing resource.
 * 
 * @author Drew
 */
public class Event implements Interval {
	//private data fields
	private long start;
	private long end;
	private String name;
	private String resource;
	private String organization;
	private String description;

	/**
	 * Constructor for an Event object. Throws an IllegalArgumentException for
	 * invalid inputs
	 * 
	 * @param start
	 * @param end
	 * @param name
	 * @param resource
	 * @param organization
	 * @param description
	 */
	Event(long start, long end, String name, String resource,
			String organization, String description) {
		this.start = start;
		this.end = end;
		this.name = name;
		this.resource = resource;
		this.organization = organization;
		this.description = description;
	}

	@Override
	/**
	 * Returns the start of the interval.
	 * @return long
	 */
	public long getStart() {
		return start;
	}

	@Override
	/**
	 * Returns the end of the interval.
	 * @return long
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * Returns the name of the event.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the resource for the event
	 * 
	 * @return String
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Returns the organization of the event.
	 * 
	 * @return String
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Returns the description of the event.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	@Override
	/**
	 * Compares two events of type Interval. Returns -1 if the start timestamp 
	 * of this interval-type event is less than the start timestamp of the other 
	 * interval-type event, otherwise returns 1.
	 * 
	 * @return int
	 */
	public int compareTo(Interval i) {
		if (i.getStart() > start) {
			return -1;
		}
		return 1;
	}

	/**
	 * Returns true if the start timestamp of this event is equal to the start
	 * timestamp of the other event, else returns false.
	 * 
	 * @param e
	 * @return boolean
	 */
	public boolean equals(Event e) {
		if (e.getStart() == start) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * Returns true if the start timestamp of this event is equal to the start 
	 * timestamp of the other event, else returns false.
	 * 
	 * @return boolean
	 */
	public boolean overlap(Interval i) {
		if ((i.getStart() >= start && i.getStart() < end)
				|| (i.getStart() < start && i.getEnd() > start)) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * Returns the string representation of this event. 
	 * @return String
	 */
	public String toString() {
		Date startTime = new Date(start);
		Date endTime = new Date(end);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy,kk:mm");

		String output = name + "\nBy: " + organization + "\nIn: " + resource
				+ "\nStart: " + df.format(startTime) + "\nEnd: "
				+ df.format(endTime) + "\nDescription: " + description;

		return output;
	}
}
