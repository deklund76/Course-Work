///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  Scheduler.java
// File:             SchedulerDB.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Maintains a list of Resource objects.
 * 
 * @author Drew
 */
public class SchedulerDB {

	//private data fields
	private List<Resource> resources;

	/**
	 * constructor for the SchedulerDB class.
	 */
	SchedulerDB() {
		resources = new ArrayList<Resource>();
	}

	/**
	 * adds a resource
	 * 
	 * @param resource
	 * @return boolean
	 */
	public boolean addResource(String resource) {
		for (int k = 0; k < resources.size(); k++) {
			if (resources.get(k).getName().equals(resource)) {
				return false;
			}
		}
		resources.add(new Resource(resource));
		return true;
	}

	/**
	 * removes a resource
	 * 
	 * @param r
	 * @return boolean
	 */
	public boolean removeResource(String r) {
		for (int k = 0; k < resources.size(); k++) {
			if (resources.get(k).getName().equals(r)) {
				resources.remove(k);
				return true;
			}
		}
		return false;
	}

	/**
	 * adds an event to a resource
	 * 
	 * @param r
	 * @param start
	 * @param end
	 * @param name
	 * @param organization
	 * @param description
	 * @return boolean
	 */
	public boolean addEvent(String r, long start, long end, String name,
			String organization, String description) {
		if (findResource(r) == null) {
			this.addResource(r);
		}
		for (int k = 0; k < resources.size(); k++) {
			if (resources.get(k).getName().equals(r)) {
				try {
					start *= 1000;
					end *= 1000;
					resources.get(k).addEvent(
							new Event(start, end, name, r, organization,
									description));
					return true;
				} catch (Exception e) {
				}
			}
		}
		return false;
	}

	/**
	 * deletes an event from a resource
	 * 
	 * @param start
	 * @param resource
	 * @return boolean
	 */
	public boolean deleteEvent(long start, String resource) {
		if (findResource(resource) == null) {
			return false;
		}
		return findResource(resource).deleteEvent(start);
	}

	/**
	 * returns a resource given its name
	 * 
	 * @param r
	 * @return Resource
	 */
	public Resource findResource(String r) {
		for (int k = 0; k < resources.size(); k++) {
			if (resources.get(k).getName().equals(r)) {
				return resources.get(k);
			}
		}
		return null;
	}

	/**
	 * Returns a list of resources maintained by this SchedulerDB class.
	 * 
	 * @return List<Resource>
	 */
	public List<Resource> getResources() {
		return resources;
	}

	/**
	 * Returns a list of events for the given resource
	 * 
	 * @param resource
	 * @return List<Event>
	 */
	public List<Event> getEventsInResource(String resource) {
		List<Event> events = new ArrayList<Event>();
		if (findResource(resource) == null) {
			return events;
		}
		Iterator<Event> eventitr = findResource(resource).iterator();
		while (eventitr.hasNext()) {
			events.add(eventitr.next());
		}
		return events;
	}

	/**
	 * Returns a list of events from all resources between the given start and
	 * end.
	 * 
	 * @param start
	 * @param end
	 * @return List<Event>
	 */
	public List<Event> getEventsInRange(long start, long end) {
		List<Event> result = new ArrayList<Event>();
		for (int k = 0; k < resources.size(); k++) {
			result.addAll(getEventsInRangeInResource(start, end,
					resources.get(k).getName()));
		}
		return result;
	}

	/**
	 * Returns a list of events from the given resource between the given start
	 * and end.
	 * 
	 * @param start
	 * @param end
	 * @param resource
	 * @return List<Event>
	 */
	public List<Event> getEventsInRangeInResource(long start, long end,
			String resource) {
		start *= 1000;
		end *= 1000;
		List<Event> events = new ArrayList<Event>();
		if (findResource(resource) == null) {
			return events;
		}
		Iterator<Event> eventitr = findResource(resource).iterator();
		while (eventitr.hasNext()) {
			Event event = eventitr.next();
			if (event.getEnd() > start && event.getStart() < end) {
				events.add(event);
			}
		}
		return events;
	}

	/**
	 * Returns the list of all the events stored in the SchedulerDB for the all the resources.
	 * @return List<Event>
	 */
	public List<Event> getAllEvents() {
		List<Event> result = new ArrayList<Event>();
		for (int k = 0; k < resources.size(); k++) {
			result.addAll(getEventsInResource(resources.get(k).getName()));
		}
		return result;
	}

	/**
	 * Returns a list of events for a given organization.
	 * @param org
	 * @return List<Event>
	 */
	public List<Event> getEventsForOrg(String org) {
		List<Event> allEvents = this.getAllEvents();
		List<Event> eventsForOrg = new ArrayList<Event>();
		for (int k = 0; k < allEvents.size(); k++) {
			if (allEvents.get(k).getOrganization().equals(org)) {
				eventsForOrg.add(allEvents.get(k));
			}
		}
		return eventsForOrg;
	}
}
