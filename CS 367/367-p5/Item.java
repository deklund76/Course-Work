///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  TheGame.java
// File:             Item.java
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
 * Represents an item in the game. The player can pick up, use, or drop an item.
 * When used items can unlock new rooms.
 * 
 * @author CS367
 */
public class Item {
	// name of item
	private String name;
	// description of item
	private String description;

	private boolean activated;

	private String message;

	private boolean oneTimeUse;

	private String usedString;

	/**
	 * Constructs an Item Object. If activated is true, the item is active and
	 * has been used. If oneTimeUse is true, the item can be used only once.
	 * Throws an IllegalArgumentException if parameters are missing or invalid.
	 * 
	 * @param name
	 * @param description
	 * @param activated
	 * @param message
	 * @param oneTimeUse
	 * @param usedString
	 */
	public Item(String name, String description, boolean activated,
			String message, boolean oneTimeUse, String usedString) {

		if (name == null || description == null || message == null
				|| usedString == null) {
			throw new IllegalArgumentException();
		}

		this.name = name;
		this.description = description;
		this.activated = activated;
		this.message = message;
		this.oneTimeUse = oneTimeUse;
		this.usedString = usedString;
	}

	/**
	 * Returns item's name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns description's name
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Checks if the item is activated. Returns true if it is, else return
	 * false.
	 * 
	 * @return boolean
	 */
	public boolean activated() {
		return activated;
	}

	/**
	 * Returns the "message" that the item wants to send to the room.
	 * 
	 * @return String
	 */
	public String on_use() {
		return message;
	}

	/**
	 * Activates the object. This changes the activation status to true.
	 */
	public void activate() {
		activated = true;
	}

	/**
	 * Returns the "on_useString" for the Item.
	 * 
	 * @return String
	 */
	public String on_useString() {
		return usedString;
	}

	/**
	 * 
	 * Returns true if the item can only be used once. Else returns false.
	 * 
	 * @return boolean
	 */
	public boolean isOneTimeUse() {
		return oneTimeUse;
	}

	@Override
	// This returns a String consisting of the name and description of the Item
	// This has been done for you.
	// DO NOT MODIFY
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Item Name: " + this.name);
		sb.append(System.getProperty("line.separator"));
		sb.append("Description: " + this.description);
		return sb.toString();
	}
}
