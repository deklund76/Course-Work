///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            AsTheCrowFlies
// Files:            AsTheCrowFlies.java, City.java, Trip.java
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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides a user interface for inputing city data manually or by files, and
 * creating a trip through multiple cities and saving it to a file. Distances
 * are calculated "as the crow flies".
 *
 * @author Drew Eklund
 */
public class AsTheCrowFlies {

	/**
	 * Main method with user interface and command loop.
	 *
	 */
	public static void main(String[] args) throws Exception {

		// Scanner
		Scanner scnr = new Scanner(System.in);

		// *Variables*
		// boolean used to exit command loop
		boolean exit = false;
		// boolean used to decide when to ask to add to previous trip
		boolean firstRodeo = true;
		// Arraylist of cities that can be added to a trip
		ArrayList<City> availableCities = new ArrayList<City>();
		// indicates index for input confirmation in command "OPTION 4"
		int numberOfCities = 0;
		// Trip to be replaced and modified
		Trip trip = new Trip();

		// Welcome message
		System.out.println("As The Crow Flies");
		// Main command loop
		while (exit == false) {
			System.out.println();
			System.out.println("1. Load available cities from a file");
			System.out.println("2. Display available cities");
			System.out.println("3. Create a trip");
			System.out.println("4. Add a city to available cities");
			System.out.println("5. Exit Program");
			System.out.print("Enter choice as integer [1-5]: ");

			String menuChoice = scnr.next();
			while (!(menuChoice.equals("1") || menuChoice.equals("2")
					|| menuChoice.equals("3") || menuChoice.equals("4") 
					|| menuChoice.equals("5"))) {
				System.out.println("Invalid input. Try again.");
				System.out.print("Enter choice as integer [1-5]: ");
				menuChoice = scnr.next();
			}

			// 1. Load available cities from a file
			if (menuChoice.equals("1")) {
				System.out.print("Enter the filename: ");
				int citiesAdded = 0;
				String name = scnr.next();
				java.io.File file = new java.io.File(name);
				try {
					Scanner input = new Scanner(file);
					while (input.hasNextLine()) {
						String line = input.nextLine();
						String state = "";
						String city = "";
						String lat = "";
						String lon = "";
						try {
							state = line.substring(0, line.indexOf(','));
							city = line.substring(state.length() + 1,
									line.indexOf(',', state.length() + 1));
							lat = line
									.substring(
											state.length() + city.length() + 2,
											line.indexOf(',', state.length()
													+ city.length() + 2));
							lon = line.substring(state.length() + city.length()
									+ lat.length() + 3, line.length());
						} catch (StringIndexOutOfBoundsException bad) {

						}
						try {
							double latt = Double.parseDouble(lat);
							double lonn = Double.parseDouble(lon);
							if (Math.abs(latt) <= Math.abs(90)
									&& Math.abs(lonn) <= Math.abs(180)) {
								availableCities.add(new City(state, city, latt,
										lonn));
								citiesAdded++;
								numberOfCities++;
							}
						} catch (NumberFormatException e) {

						}
					}
					input.close();
				} catch (FileNotFoundException fnfe) {
					System.out.println("Unable to read file");
				}
				System.out.println(citiesAdded + " cities added");
			}
			// 2. Display available cities
			else if (menuChoice.equals("2")) {
				for (int k = 0; k < availableCities.size(); k++) {
					System.out.println(availableCities.get(k).toString());
				}
			}
			// 3. Create a trip
			else if (menuChoice.equals("3")) {
				if (availableCities.size() < 2) {
					System.out.println("There are " + availableCities.size()
							+ " cities to choose from.\n"
							+ "Must have at least 2 cities to choose from.");
				} else {
					System.out.println("There are " + availableCities.size()
							+ " cities to choose from.");
					if (firstRodeo) {
						trip = new Trip();
						System.out.println("New trip created, "
								+ "needs at least two cities.");
					} else {
						System.out.print("Add to current trip (y/n)? ");
						if (!scnr.next().substring(0, 1).equalsIgnoreCase("y")) 
						{
							trip = new Trip();
							System.out.println("New trip created, "
									+ "needs at least two cities.");
						}
					}
					String city = "Eau Claire";
					scnr.nextLine();
					while (!city.isEmpty()) {
						System.out.print("Enter next city name "
								+ "(or enter to end): ");
						city = scnr.nextLine();
						for (int k = 0; k < availableCities.size(); k++) {
							if (city.equalsIgnoreCase(availableCities.get(k)
									.getName())) {
								trip.addCity(availableCities.get(k));
								break;
							}
						}
					}
					if (trip.isValid()) {
						firstRodeo = false;
						System.out.println(trip.toString());
						System.out.print("Write trip details to file (y/n)? ");
						if (scnr.next().substring(0, 1).equalsIgnoreCase("y")) {
							System.out.print("Enter filename: ");
							String name = scnr.next();
							java.io.PrintWriter output;
							output = new java.io.PrintWriter(name);
							output.println(trip.toString());

							output.close();
						}
					} else {
						System.out.println("Must have at least "
								+ "2 cities in a trip.");
					}
				}
			}
			// 4. Add a city to available cities
			else if (menuChoice.equals("4")) {
				// City values
				String state;
				String city;
				double lat = 0;
				double lon = 0;
				System.out.print("Enter state name: ");
				scnr.nextLine();
				state = scnr.nextLine();
				System.out.print("Enter city name: ");
				city = scnr.nextLine();
				System.out.print("Enter latitude as double (-90.0 to 90.0): ");
				boolean validInput = false;
				while (!validInput) {
					if (!scnr.hasNextDouble()) {
						System.out.println("Invalid input. Try again.");
						scnr.next();
					} else {
						lat = scnr.nextDouble();
						if (lat > Math.abs(90.0)) {
							System.out.println("Invalid input. Try again.");
						} else {
							validInput = true;
						}
					}
				}
				System.out.print("Enter longitude as double "
						+ "(-180.0 to 180.0): ");
				validInput = false;
				while (!validInput) {
					if (!scnr.hasNextDouble()) {
						System.out.println("Invalid input. Try again.");
						scnr.next();
					} else {
						lon = scnr.nextDouble();
						if (lon > Math.abs(180.0)) {
							System.out.println("Invalid input. Try again.");
						} else {
							validInput = true;
						}
					}
				}
				availableCities.add(new City(state, city, lat, lon));
				System.out.println("Added: "
						+ availableCities.get(numberOfCities).toString());
				numberOfCities++;
			}
			// 5. Exit Program
			else if (menuChoice.equals("5")) {
				System.out.println("Thank you for your business.");
				java.io.File file = new java.io.File("available_cities.txt");
				java.io.PrintWriter output = new java.io.PrintWriter(file);
				for (int k = 0; k < availableCities.size(); k++) {
					output.println(availableCities.get(k).toString());
				}
				output.close();
				System.out.println("Saved available cities to "
						+ "available_cities.txt");
				exit = true;
			}

		}
		scnr.close();
	}
}
