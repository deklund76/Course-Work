///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  AppStore.java
// File:             AppScoreComparator.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Comparator;

/**
 * Comparator for comparing and sorting apps by score
 *
 * @author Drew 
 */
public class AppScoreComparator implements Comparator<App> {
	
	@Override
	public int compare(App app1, App app2) {
		if(app1.getAppScore() < app2.getAppScore()) {
			return -1;
		}
		if(app1.getAppScore() > app2.getAppScore()) {
			return 1;
		}
		if(app1.getUploadTimestamp() > app2.getUploadTimestamp()) {
			return -1;
		}
		return 1;
	}

}

