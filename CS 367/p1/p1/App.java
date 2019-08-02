///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  AppStore.java
// File:             App.java
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
import java.util.List;

/**
 * Instantiable Class for Apps
 *
 * @author Drew Eklund
 */
public class App implements Comparable<App> {
	
	//App data fileds
	private User developer;
	private String appId;
	private String appName;
	private String category;
	private double price;
	private long uploadTimestamp;
	private long total_downloads;
	private List<AppRating> appRatings = new ArrayList<AppRating>();

	/**
	 * Constructor for App class
	 *
	 * @param developer (user profile of developer)
	 * @param appId (ID representing app)
	 * @param appName (Name of app)
	 * @param category (category of app)
	 * @param price (double representing app price)
	 * @param uploadTimestamp (long representing time of upload)
	 * 
	 * @author Drew
	 */
	public App(User developer, String appId, String appName, String category,
			double price, long uploadTimestamp) throws IllegalArgumentException {
		this.developer = developer;
		this.appId = appId;
		this.appName = appName;
		this.category = category;
		this.price = price;
		this.uploadTimestamp = uploadTimestamp;
		this.total_downloads = 0;
	}
	
	/**
	 * Accessor method for developer variable
	 * 
	 * @return User
	 * 
	 * @author Drew
	 */
	public User getDeveloper() {
		return this.developer;
	}
	
	/**
	 * Accessor method for appId variable
	 * 
	 * @return string
	 * 
	 * @author Drew
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * Accessor method for appName variable
	 * 
	 * @return String
	 * 
	 * @author Drew
	 */
	public String getAppName() {
		return this.appName;
	}

	public String getCategory() {
		return this.category;
	}

	/**
	 * Accessor method for price variable
	 * 
	 * @return double
	 * 
	 * @author Drew
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Accessor method for uploadTimestamp variable
	 * 
	 * @return long
	 * @author Drew
	 */
	public long getUploadTimestamp() {
		return this.uploadTimestamp;
	}

	public void download(User user) {		
		this.total_downloads++;
		user.download(this);
	}

	public void rate(User user, short rating) throws IllegalArgumentException {
		
		for(int k = 0; k < appRatings.size(); k++) {
			if(appRatings.get(k).getUser().getEmail().equals(user.getEmail())) {
				throw new IllegalArgumentException();
			}
		}
		
		if(user.getAllDownloadedApps().contains(this)) {
			throw new IllegalArgumentException();
		}
		
		appRatings.add(new AppRating(this, user, rating));
		
	}

	public long getTotalDownloads() {
		return this.total_downloads;
	}

	public double getAverageRating() {
		double averageRating = 0;
		int sum = 0;
		for(int k = 0; k < appRatings.size(); k++) {
			sum += appRatings.get(k).getRating();
		}
		if(appRatings.size() > 0) {
		averageRating = sum / appRatings.size();
		}
		return averageRating;
	}
	
	public double getRevenueForApp() {
		return price * total_downloads;
	}


	public double getAppScore() {
		return this.getAverageRating() * Math.log(1 + total_downloads);
	}

	@Override
	public int compareTo(App otherApp) {
		if(this.getUploadTimestamp() > otherApp.getUploadTimestamp()) {
			return -1;
		}
		
		if(this.getUploadTimestamp() < otherApp.getUploadTimestamp()) {
			return 1;
		}
		
		return 0;
	}
}

