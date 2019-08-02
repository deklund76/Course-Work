///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  AppStore.java
// File:             AppStoreDB.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing database for appstore and containing all necessarry data
 *
 * @author Drew Eklund
 */
public class AppStoreDB {

	private List<App> appData = new ArrayList<App>();
	private List<User> userData = new ArrayList<User>();
	private List<String> categories = new ArrayList<String>();

	/**
	 * Simple no argument constructor
	 * 
	 * @author Drew
	 */
	public AppStoreDB() {
	}

	public User addUser(String email, String password, String firstName,
			String lastName, String country, String type)
			throws IllegalArgumentException {

		User newUser = new User(email, password, firstName, lastName, country,
				type);

		for (int k = 0; k < userData.size(); k++) {
			if (email.equals(userData.get(k).getEmail())) {
				throw new IllegalArgumentException();
			}
		}

		userData.add(newUser);

		return newUser;

	}

	public void addCategory(String category) {
		categories.add(category);
	}

	/**
	 * Accessor method for category variable
	 * 
	 * @author Drew
	 */
	public List<String> getCategories() {
		return categories;
	}

	public User findUserByEmail(String email) {
		for (int k = 0; k < userData.size(); k++) {
			if (email.equals(userData.get(k).getEmail())) {
				return userData.get(k);
			}
		}
		return null;
	}

	public App findAppByAppId(String appId) {
		for (int k = 0; k < appData.size(); k++) {
			if (appId.equals(appData.get(k).getAppId())) {
				return appData.get(k);
			}
		}
		return null;
	}

	public User loginUser(String email, String password) {
		User user = this.findUserByEmail(email);
		if(user.verifyPassword(password)) {
			return user;
		}
		return null;
	}

	public App uploadApp(User uploader, String appId, String appName,
			String category, double price, long timestamp)
			throws IllegalArgumentException {
		
		if(uploader == null || appId == null || appName == null 
				|| category == null) {
			throw new IllegalArgumentException();
		}

		if (!userData.contains(uploader)) {
			throw new IllegalArgumentException();
		}

		if (this.findAppByAppId(appId) != null) {
			throw new IllegalArgumentException();
			}

		if (!categories.contains(category)) {
			throw new IllegalArgumentException();
		}

		if (price < 0) {
			throw new IllegalArgumentException();
		}

		if (timestamp < 0) {
			throw new IllegalArgumentException();
		}

		App newApp = new App(uploader, appId, appName, category, price,
				timestamp);

		appData.add(newApp);

		return newApp;
	}

	public void downloadApp(User user, App app) {
		app.download(user);
		user.download(app);
	}

	public void rateApp(User user, App app, short rating) {
		app.rate(user, rating);
	}

	public boolean hasUserDownloadedApp(User user, App app) {
		return user.getAllDownloadedApps().contains(app);
	}

	public List<App> getTopFreeApps(String category) {
		
		if(!categories.contains(category)) {
			throw new IllegalArgumentException();
		}
		
		List<App> topApps = new ArrayList<App>();
		for(int k = 0; k < appData.size(); k++) {
			if(appData.get(k).getPrice() == 0) {
			topApps.add(appData.get(k));
			}
		}
		
		if(category == null) {
			Collections.sort(topApps, new AppScoreComparator());
		}
		else {
			for(int k = 0; k < appData.size(); k++) {
				if(!topApps.get(k).getCategory().equals(category)) {
					topApps.remove(k);
				}
			}
			Collections.sort(topApps, new AppScoreComparator());
		}
		return topApps;
	}

	public List<App> getTopPaidApps(String category) {
		if(!categories.contains(category)) {
			throw new IllegalArgumentException();
		}
		
		List<App> topApps = new ArrayList<App>();
		for(int k = 0; k < appData.size(); k++) {
			if(appData.get(k).getPrice() > 0) {
			topApps.add(appData.get(k));
			}
		}
		
		if(category == null) {
			Collections.sort(topApps, new AppScoreComparator());
		}
		else {
			for(int k = 0; k < appData.size(); k++) {
				if(!topApps.get(k).getCategory().equals(category)) {
					topApps.remove(k);
				}
			}
			Collections.sort(topApps, new AppScoreComparator());
		}
		return topApps;
	}

	public List<App> getMostRecentApps(String category) {
		if(!categories.contains(category)) {
			throw new IllegalArgumentException();
		}
		
		List<App> newApps = new ArrayList<App>();
		for(int k = 0; k < appData.size(); k++) {
			newApps.add(appData.get(k));
		}
		
		if(category == null) {
			Collections.sort(newApps);
		}
		else {
			for(int k = 0; k < appData.size(); k++) {
				if(!newApps.get(k).getCategory().equals(category)) {
					newApps.remove(k);
				}
			}
			Collections.sort(newApps);
		}
		return newApps;
	}
}
