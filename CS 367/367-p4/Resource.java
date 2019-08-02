///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  Scheduler.java
// File:             Resource.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Iterator;

/**
 * Represents a resource in Union South and it stores the events for the
 * resource in an IntervalBST.
 * 
 * @author CS367
 */
public class Resource {
	private String name;
	private SortedListADT<Event> events;

	Resource(String name) {
		this.name = name;
		events = new IntervalBST<Event>();
	}

	public String getName() {
		return name;
	}

	public boolean addEvent(Event e) {
		if (e == null)
			return false;
		events.insert(e);
		return true;
	}

	public boolean deleteEvent(long start) {
		return events.delete(new Event(start, start, "", "", "", ""));
	}

	public Iterator<Event> iterator() {
		return events.iterator();
	}

}
