///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Files:            App.java AppRating.java AppScoreComparator.java
//                   AppStoreDB.java and User.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

/**
 * Class containing main method and execution
 *
 * @author Drew 
 */
public class AppStore {
	
	private static AppStoreDB appStoreDB = new AppStoreDB();
	private static User appUser = null;
	private static Scanner scanner = null;
	
	public static void main(String args[]) {
		if (args.length < 4) {			
			System.err.println("Bad invocation! Correct usage: "
					+ "java AppStore <UserDataFile> <CategoryListFile> "
					+ "<AppDataFile> <AppActivityFile>");
			System.exit(1);
		}
				
		boolean didInitialize = 
				initializeFromInputFiles(args[0], args[1], args[2], args[3]);
		
		if(!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}
		
		System.out.println("Welcome to the App Store!\n"
				   + "Start by browsing the top free and the top paid apps "
				   + "today on the App Store.\n"
				   + "Login to download or upload your favorite apps.\n");

		processUserCommands();
	}
	
	private static boolean initializeFromInputFiles(String userDataFile, String 
			categoryListFile, String appDataFile, String appActivityFile) {
		
		java.io.File userData = new java.io.File(userDataFile);
		java.io.File categoryList = new java.io.File(categoryListFile);
		java.io.File appData = new java.io.File(appDataFile);
		java.io.File appActivity = new java.io.File(appActivityFile);
		
		try {
			Scanner arg0 = new Scanner(userData);
			while(arg0.hasNext()) {
				String entry = arg0.next();
				String[] parsedEntry = entry.split(",");
				if(parsedEntry.length < 6) {
					throw new IllegalArgumentException();
				}
				String email = parsedEntry[0];
				String password = parsedEntry[1];
				String firstName = parsedEntry[2];
				String lastName = parsedEntry[3];
				String country = parsedEntry[4];
				String type = parsedEntry[5];
				
				appStoreDB.addUser(email, password, firstName, 
						lastName, country, type);
				
			}
			arg0.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		
		try {
			Scanner arg1 = new Scanner(categoryList);
			while(arg1.hasNext()) {
				String entry = arg1.next();
				appStoreDB.addCategory(entry);
			}
			arg1.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		
		try {
			Scanner arg2 = new Scanner(appData);
			while(arg2.hasNext()) {
				String entry = arg2.next();
				String[] parsedEntry = entry.split(",");
				if(parsedEntry.length < 6) {
					throw new IllegalArgumentException();
				}
				String email = parsedEntry[0];
				String appId = parsedEntry[1];
				String appName = parsedEntry[2];
				String category = parsedEntry[3];
				double price = Double.parseDouble(parsedEntry[4]);
				long timestamp = Long.parseLong(parsedEntry[5]);
				
				User uploader = appStoreDB.findUserByEmail(email);
				
				appStoreDB.uploadApp(uploader, appId, appName, 
						category, price, timestamp);
			}
			arg2.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		
		try {
			Scanner arg3 = new Scanner(appActivity);
			while(arg3.hasNext()) {
				String entry = arg3.next();
				String[] parsedEntry = entry.split(",");
				if(parsedEntry.length < 3) {
					throw new IllegalArgumentException();
				}
				String type = parsedEntry[0];
				String email = parsedEntry[1];
				String appId = parsedEntry[2];
				User user = appStoreDB.findUserByEmail(email);
				App app = appStoreDB.findAppByAppId(appId);
				if(type.equalsIgnoreCase("d")) {
					appStoreDB.downloadApp(user, app);
				}
				else if(type.equals("r")) {
					if(parsedEntry.length < 4) {
						throw new IllegalArgumentException();
					}
					Short rating = Short.parseShort(parsedEntry[3]);
					app.rate(user, rating);
				}
				
				else {
					throw new IllegalArgumentException();
				}
			}
			arg3.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		
		
		return true;
	}
	
	private static void processUserCommands() {
		scanner = new Scanner(System.in);
		String command = null;		
		do {
			if (appUser == null) {
				System.out.print("[anonymous@AppStore]$ ");
			} else {
				System.out.print("[" + appUser.getEmail().toLowerCase() 
						+ "@AppStore]$ ");
			}
			command = scanner.next();
			switch(command.toLowerCase()) {
				case "l":
					processLoginCommand();
					break;
					
				case "x": 
					processLogoutCommand();
					break;
					
				case "s":
					processSubscribeCommand();
					break;
				
				case "v":
					processViewCommand();
					break;
					
				case "d":
					processDownloadCommand();
					break;
					
				case "r":
					processRateCommand();
					break;
				
				case "u":
					processUploadCommand();
					break;
				
				case "p":
					processProfileViewCommand();
					break;								
					
				case "q":
					System.out.println("Quit");
					break;
				default:
					System.out.println("Unrecognized Command!");
					break;
			}
		} while (!command.equalsIgnoreCase("q"));
		scanner.close();
	}
	
	
	private static void processLoginCommand() {
		if (appUser != null) {
			System.out.println("You are already logged in!");
		} else {
			String email = scanner.next();
			String password = scanner.next();
			appUser = appStoreDB.loginUser(email, password);
			if (appUser == null) {
				System.out.println("Wrong username / password");
			}
		}
	}
	
	
	private static void processLogoutCommand() {
		if (appUser == null) {
			System.out.println("You are already logged out!");
		} else {
			appUser = null;
			System.out.println("You have been logged out.");
		}
	}
	
	private static void processSubscribeCommand() {
		if (appUser == null) {
			System.out.println("You need to log in "
					+ "to perform this action!");
		} else {
			if (appUser.isDeveloper()) {
				System.out.println("You are already a developer!");
			} else {
				appUser.subscribeAsDeveloper();
				System.out.println("You have been promoted as developer");
			}
		}
	}
	
	private static void processViewCommand() {
		String restOfLine = scanner.nextLine();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		int count;
		String category;
		switch(subCommand.toLowerCase()) {
			case "categories":
				System.out.println("Displaying list of categories...");
				List<String> categories = appStoreDB.getCategories();
				count = 1;
				for (String categoryName : categories) {
					System.out.println(count++ + ". " + categoryName);
				}
				break;
			case "recent":				
				category = null;
				if (in.hasNext()) {
					category = in.next();
				} 
				displayAppList(appStoreDB.getMostRecentApps(category));				
				break;
			case "free":
				category = null;
				if (in.hasNext()) {
					category = in.next();
				}
				displayAppList(appStoreDB.getTopFreeApps(category));
				break;
			case "paid":
				category = null;
				if (in.hasNext()) {
					category = in.next();
				}
				displayAppList(appStoreDB.getTopPaidApps(category));
				break;
			case "app":
				String appId = in.next();
				App app = appStoreDB.findAppByAppId(appId);
				if (app == null) {
					System.out.println("No such app found with the given app id!");
				} else {
					displayAppDetails(app);
				}
				break;
			default: 
				System.out.println("Unrecognized Command!");
		}
		in.close();
	}
					
	private static void processDownloadCommand() {
		if (appUser == null) {
			System.out.println("You need to log in "
					+ "to perform this action!");
		} else {
			String appId = scanner.next();
			App app = appStoreDB.findAppByAppId(appId);
			if (app == null) {
				System.out.println("No such app with the given id exists. "
						+ "Download command failed!");
			} else {
				try {
					appStoreDB.downloadApp(appUser, app);
					System.out.println("Downloaded App " + app.getAppName());
				} catch (Exception e) {				
					System.out.println("Something went wrong. "
							+ "Download command failed!");
				}
			}
		}
		
	}
			
	private static void processRateCommand() {
		if (appUser == null) {
			System.out.println("You need to log in "
					+ "to perform this action!");
		} else {
			String appId = scanner.next();
			App app = appStoreDB.findAppByAppId(appId);
			if (app == null) {
				System.out.println("No such app with the given id exists. "
						+ "Rating command failed!");
			} else {
				try {
					short rating = scanner.nextShort();
					appStoreDB.rateApp(appUser, app, rating);
					System.out.println("Rated app " + app.getAppName());
				} catch (Exception e) {
					System.out.println("Something went wrong. "
							+ "Rating command failed!");
				}
			}
		}
		
	}
	
	private static void processUploadCommand() {
		if (appUser == null) {
			System.out.println("You need to log in "
					+ "to perform this action!");
		} else {
			String appName = scanner.next();
			String appId = scanner.next();
			String category = scanner.next();
			double price = scanner.nextDouble();
			long uploadTimestamp = Instant.now().toEpochMilli();
			try {
				appStoreDB.uploadApp(appUser, appId, appName, category, 
						price, uploadTimestamp);
			} catch (Exception e) {
				System.out.println("Something went wrong. "
						+ "Upload command failed!");
			}
		}
	}

	
	private static void processProfileViewCommand() {		
		String restOfLine = scanner.nextLine();
		Scanner in = new Scanner(restOfLine);
		String email = null;
		if (in.hasNext()) {
			email = in.next();
		}
		if (email != null) {
			displayUserDetails(appStoreDB.findUserByEmail(email));
		} else {
			displayUserDetails(appUser);
		}
		in.close();
		
	}
			

	private static void displayAppList(List<App> apps) {
		if (apps.size() == 0) {
			System.out.println("No apps to display!");
		} else {
			int count = 1;
			for(App app : apps) {
				System.out.println(count++ + ". " 
						+ "App: " + app.getAppName() + "\t" 
						+ "Id: " + app.getAppId() + "\t" 
						+ "Developer: " + app.getDeveloper().getEmail());
			}	
		}
	}
	
	private static void displayAppDetails(App app) {
		if (app == null) {
			System.out.println("App not found!");
		} else {
			System.out.println("App name: " + app.getAppName());
			System.out.println("App id: " + app.getAppId());
			System.out.println("Category: " + app.getCategory());
			System.out.println("Developer Name: " 
					+ app.getDeveloper().getFirstName() + " " 
					+ app.getDeveloper().getLastName());
			System.out.println("Developer Email: " 
					+ app.getDeveloper().getEmail());
			System.out.println("Total downloads: " + app.getTotalDownloads());
			System.out.println("Average Rating: " + app.getAverageRating());
			
			// show revenue from app if the logged-in user is the app developer
			if (appUser != null && 
					appUser.getEmail()
						.equalsIgnoreCase(app.getDeveloper().getEmail())) {
				System.out.println("Your Revenue from this app: $" 
						+ app.getRevenueForApp());
			}
				
		}		
	}
	
	private static void displayUserDetails(User user) {		
		if (user == null) {
			System.out.println("User not found!");
		} else {
			System.out.println("User name: " + user.getFirstName() + " "
					+ user.getLastName());
			System.out.println("User email: " + user.getEmail());
			System.out.println("User country: " + user.getCountry());
						
			// print the list of downloaded apps
			System.out.println("List of downloaded apps: ");			
			List<App> downloadedApps = user.getAllDownloadedApps();
			displayAppList(downloadedApps);
			
			// print the list of uploaded app
			System.out.println("List of uploaded apps: ");
			List<App> uploadedApps = user.getAllUploadedApps();
			displayAppList(uploadedApps);
			
			// show the revenue earned, if current user is developer
			if (appUser!= null 
					&& user.getEmail().equalsIgnoreCase(appUser.getEmail()) 
					&& appUser.isDeveloper()) {
				double totalRevenue = 0.0;
				for (App app : uploadedApps) {
					totalRevenue += app.getRevenueForApp();
				}
				System.out.println("Your total earnings: $" + totalRevenue);
			}
				
		}
	}
}

