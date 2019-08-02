///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  AppStore.java
// File:             User.java
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
 * Class representing different User profiles
 *
 * @author Drew Eklund
 */
public class User {

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String country;
	private String type;
	private List<App> downloadedApps = new ArrayList<App>();
	private List<App> uploadedApps = new ArrayList<App>(); 

	/**
	 * Constructor for the User class
	 *
	 * @param email (email address of user)
	 * @param password (user's password)
	 * @param firstName (user's first name)
	 * @param lastName (user's last name)
	 * @param country (user's country)
	 * @param type (denotes if user is developer)
	 * 
	 * @author Drew
	 */
	public User(String email, String password, String firstName,
			String lastName, String country, String type)
			throws IllegalArgumentException {
		
		if(email == null || password == null || firstName == null || 
				lastName == null || country == null || type == null) {
			throw new IllegalArgumentException();
		}
		
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.type = type;
		
	}

	/**
	 * Accessor method for email variable
	 * 
	 * @author Drew
	 */
	public String getEmail() {
		return this.email;
	}

	public boolean verifyPassword(String testPassword) {
		if(testPassword.equals(this.password)) {
			return true;
		}
		return false;
	}

	/**
	 * Accessor method for firstName variable
	 * 
	 * @author Drew
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Accessor method for lastName variable
	 * 
	 * @author Drew
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Accessor method for country variable
	 * 
	 * @author Drew
	 */
	public String getCountry() {
		return this.country;
	}

	public boolean isDeveloper() {
		if(this.type.equals("developer")) {
			return true;
		}
		return false;
	}

	public void subscribeAsDeveloper() {
		this.type = "developer";
	}

	public void download(App app) { 
		downloadedApps.add(app);
	}

	public void upload(App app) {
		uploadedApps.add(app);
	}
	
	public List<App> getAllDownloadedApps() {
		return downloadedApps;
	}
	
	public List<App> getAllUploadedApps() {
		return uploadedApps;
	}
		
}

