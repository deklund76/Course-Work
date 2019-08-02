///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Files:            Event.java Interval.java IntervalBST.java 
//					 IntervalBSTIterator.java IntervalBSTnode.java
//					 IntervalConflictExceptior.java Resource.java Scheduler.java
//					 SchedulerDB.java SortedListADT.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Implements the main program, reads the initialization file, processes user
 * input, and displays the output. This class populates and relies on the
 * SchedulerDB.
 * 
 * @author Drew
 */
public class Scheduler {

	//private data fields
	private static SchedulerDB schedulerDB = new SchedulerDB();
	private static Scanner scanner = null;

	/**
	 * Main method which runs the application. Checks that command line
	 * arguments exist and are valid. Initializes the Database from the input
	 * file and prompts the user for commands until they enter 'q'.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java Scheduler <resourceListFile>");
			System.exit(1);
		}

		boolean didInitialize = initializeFromInputFile(args[0]);

		if (!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}

		System.out.println("Welcome to the UW-Madison Scheduling Client");

		processUserCommands();
	}

	/**
	 * initializes the SchedulerDB by parsing a resource list file.
	 * 
	 * @param resourceListFile
	 * @return
	 */
	private static boolean initializeFromInputFile(String resourceListFile) {

		try {
			File input = new File(resourceListFile);
			Scanner init = new Scanner(input);
			
			//Data fields to be initialized and used to add resources/events
			String resourceName;
			String name;
			String startString;
			String endString;
			long start;
			long end;
			String org;
			String descr;
			
			init.nextLine();
			while (init.hasNextLine()) {
				resourceName = init.nextLine();
				name = init.nextLine();
				while (!name.startsWith("#")) {
					System.out.println(name);
					
					//Parse and store timestamps
					startString = init.nextLine();
					endString = init.nextLine();
					DateFormat format = new SimpleDateFormat(
							"MM/dd/yyyy,kk:mm", Locale.ENGLISH);
					Date startDate = format.parse(startString);
					Date endDate = format.parse(endString);
					start = startDate.getTime() / 1000;
					end = endDate.getTime() / 1000;
					
					//Enter other fields
					org = init.nextLine();
					descr = init.nextLine();
					
					//Add event
					schedulerDB.addEvent(resourceName, start, end, name, org,
							descr);
					if (init.hasNextLine()) {
						name = init.nextLine();
					} else {
						name = "#";
					}
				}
			}
			init.close();
		} catch (FileNotFoundException e) {
			return false;
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Prompts for the user to enter command options and continues to process
	 * them until the user enters the 'q' command.
	 */
	private static void processUserCommands() {
		scanner = new Scanner(System.in);
		String command = null;
		do {

			System.out.print("\nPlease Enter a command ([H]elp):");
			command = scanner.next();
			switch (command.toLowerCase()) {
			case "v":
				processDisplayCommand();
				break;
			case "a":
				processAddCommand();
				break;
			case "d":
				processDeleteCommand();
				break;
			case "q":
				System.out.println("Quit");
				break;
			case "h":
				System.out
						.println("\nThis scheduler has commands that are entered as a single character indicated in [].\n"
								+ "The main commands are to view, add, delete, or quit.\n"
								+ "The first three main commands need a secondary command possibly with additional input.\n"
								+ "A secondary command's additional input is described within <>.\n"
								+ "Please note that a comma (,) in the add event command represents a need to press\n"
								+ "the return character during the command. Also note that times must be in the format\n"
								+ "of mm/dd/yyyy,hh:mm.\n"
								+ "[v]iew\n"
								+ "	[r] = view all resources\n"
								+ "	[e] = view all events\n"
								+ "	[t] <resource name> = view events in a resource\n"
								+ "	[o] <organization name> = view events with an organization\n"
								+ "	[n] <start time> <end time> = view events within a time range\n"
								+ "	[s] <start time> <end time> <resource name> = view events within in a time range in a resource\n"
								+ "[a]dd\n"
								+ "	[r] <resource name> = add a resource\n"
								+ "	[e] <resource name>, = add an event\n"
								+ "		      <start time> <end time> <event name>, \n"
								+ "		      <organization name>, \n"
								+ "		      <event description>\n"
								+ "[d]elete\n"
								+ "	[r] <resource name> = delete a resource\n"
								+ "	[e] <event start time> <resource name> = delete an event\n"
								+ "[q]uit\n");
				break;
			default:
				System.out.println("Unrecognized Command!");
				break;
			}
		} while (!command.equalsIgnoreCase("q"));
		scanner.close();
	}

	/**
	 * processes display commands.
	 */
	private static void processDisplayCommand() {
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch (subCommand.toLowerCase()) {
		// additional input in comments (comma means return)
		case "r":
			printResourceList(schedulerDB.getResources());
			break;
		case "e":
			printEventList(schedulerDB.getAllEvents());
			break;
		case "t": // resource,
			printEventList(schedulerDB.getEventsInResource(scanner.nextLine()
					.trim()));
			break;
		case "s": // start end resource,
			printEventList(schedulerDB.getEventsInRangeInResource(
					convertToTime(scanner.next()),
					convertToTime(scanner.next()), scanner.nextLine().trim()));
			break;
		case "o": // organization
			printEventList(schedulerDB.getEventsForOrg(scanner.nextLine()
					.trim()));
			break;
		case "n": // start end
			printEventList(schedulerDB.getEventsInRange(
					convertToTime(scanner.next()),
					convertToTime(scanner.next())));
			break;
		default:
			System.out.println("Unrecognized Command!");
		}
		in.close();
	}

	/**
	 * processes add commands.
	 */
	private static void processAddCommand() {
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch (subCommand.toLowerCase()) {
		case "r": // resource
			if (!schedulerDB.addResource(scanner.nextLine().trim())) {
				System.out
						.println("Could not add: no two resources may have the same name.");
			} else {
				System.out.println("Successfully added resource.");
			}
			break;
		case "e": // resource, start end name, organization, description
			try {
				if (!schedulerDB.addEvent(scanner.nextLine().trim(),
						convertToTime(scanner.next()), convertToTime(scanner
								.next()), scanner.nextLine().trim(), scanner
								.nextLine().trim(), scanner.nextLine().trim())) {
					System.out.println("Could not add: resource not found.");
				} else {
					System.out.println("Successfully added event.");
				}
			} catch (IntervalConflictException expt) {
				System.out
						.println("Could not add: this event conflicted with an existing event.");
			}
			break;
		default:
			System.out.println("Unrecognized Command!");
		}
		in.close();
	}

	/**
	 * processes delete commands.
	 */
	private static void processDeleteCommand() {
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch (subCommand.toLowerCase()) {
		case "r": // resource
			if (!schedulerDB.removeResource(scanner.nextLine().trim())) {
				System.out.println("Could not delete. Resource not found.");
			} else {
				System.out.println("Successfully deleted resource.");
			}
			break;
		case "e": // resource, start
			if (!schedulerDB.deleteEvent(convertToTime(scanner.next().trim()),
					scanner.nextLine().trim())) {
				System.out.println("Could not delete. Resource not found.");
			} else {
				System.out.println("Successfully deleted event.");
			}
			break;
		default:
			System.out.println("Unrecognized Command!");
		}
		in.close();
	}

	/**
	 * prints a list of all resources in the database.
	 * @param list
	 */
	private static void printResourceList(List<Resource> list) {
		Iterator<Resource> itr = list.iterator();
		if (!itr.hasNext()) {
			System.out.println("No resources to display.");
		}
		while (itr.hasNext()) {
			System.out.println(itr.next().getName());
		}
	}

	/**
	 * prints a list of all events in the database.
	 * @param list
	 */
	private static void printEventList(List<Event> list) {
		Iterator<Event> itr = list.iterator();
		if (!itr.hasNext()) {
			System.out.println("No events to display.");
		}
		while (itr.hasNext()) {
			System.out.println("\n" + itr.next().toString());
		}
	}

	/**
	 * helper class for converting String representations of dates to timestamps
	 * of type long
	 * @param time
	 * @return long
	 */
	private static long convertToTime(String time) {
		long result = 0;
		Format format = new SimpleDateFormat("MM/dd/yyyy,HH:mm");
		try {
			Date date = (Date) format.parseObject(time);
			result = date.getTime() / 1000;
		} catch (Exception e) {
			System.out
					.println("Dates are not formatted correctly.  Must be \"MM/dd/yyyy,HH:mm\"");
		}
		return result;
	}

}
