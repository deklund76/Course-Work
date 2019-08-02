///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  AppStore.java
// File:             AppRating.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Instantiable Class for AppRatings
 *
 * @author Drew Eklund
 */
public class AppRating {	
	private App app;
	private User user;
	private short rating;
	
	/**
	 * constructor of AppRating class
	 *
	 * @param User (User rating app)
	 * @param App (app being rated)
	 * @param rating (rating out of 5)
	 * 
	 * @author Drew
	 */
	public AppRating(App app, User user, short rating) {
		this.app = app;
		this.user = user;
		this.rating = rating;
	}
	
	/**
	 * Accessor method for app variable
	 * 
	 * @return App
	 * @author Drew
	 */
	public App getApp() {
		return this.app;
	}
	
	/**
	 * Accessor method for user variable
	 * 
	 * @return user
	 * @author Drew
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * Accessor method for rating variable
	 * 
	 * @return rating
	 * @author Drew
	 */
	public short getRating() {
		return this.rating;
	}
}

