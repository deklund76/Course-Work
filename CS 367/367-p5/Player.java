///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  TheGame.java
// File:             Player.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a player with the inputed name and a set of items representing the
 * player's "magic sack"
 * 
 * @author CS367
 */
public class Player {
	// player name
	private String name;
	// the magic sack held by the player that contains all his/her items
	private Set<Item> magicSack;

	// Do not add anymore private data members

	/**
	 * Constructs a Player Object. throws an IllegalArgumentException for
	 * invalid arguments.
	 * 
	 * @param name
	 * @param startingItems
	 * @return String
	 */
	public Player(String name, Set<Item> startingItems) {
		this.name = name;
		this.magicSack = startingItems;
	}

	/**
	 * Getter method for the player's name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	// Returns a String consisting of the items in the sack
	// DO NOT MODIFY THIS METHOD
	public String printSack() {
		// neatly printed items in sack
		StringBuilder sb = new StringBuilder();
		sb.append("Scanning contents of your magic sack");
		sb.append(System.getProperty("line.separator"));
		for (Item itm : magicSack) {
			sb.append(itm.getName());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	/**
	 * Iterate through the sack, and find the items whose status is activated.
	 * 
	 * @return Set<Item>
	 */
	public Set<Item> getActiveItems() {
		HashSet<Item> activeItems = new HashSet<Item>();
		Iterator<Item> rummageThrough = magicSack.iterator();
		while (rummageThrough.hasNext()) {
			Item whatsThis = rummageThrough.next();
			if (whatsThis.activated()) {
				activeItems.add(whatsThis);
			}
		}
		return activeItems;
	}

	/**
	 * Finds the Item in the sack whose name is "itemName". Returns the item if
	 * it's found, otherwise return null.
	 * 
	 * @return Set<Item>
	 */
	public Item findItem(String item) {
		Iterator<Item> rummageThrough = magicSack.iterator();
		while (rummageThrough.hasNext()) {
			Item thisOne = rummageThrough.next();
			if (thisOne.getName().equals(item)) {
				return thisOne;
			}
		}
		return null;
	}

	/**
	 * Checks if the player has the "item" in his sack. Returns true if he does,
	 * otherwise returns false.
	 * 
	 * @param item
	 * @return boolean
	 */
	public boolean hasItem(Item item) {
		return magicSack.contains(item);
	}

	/**
	 * Adds the "item" to the Player's sack. Duplicate items are not allowed.
	 * Returns true if item successfully added, else returns false.
	 * 
	 * @param item
	 * @return boolean
	 */
	public boolean addItem(Item item) {
		return magicSack.add(item);
	}

	/**
	 * Removes the "item" from the sack. Returns true if removal is successful,
	 * else returns false.
	 * 
	 * @param item
	 * @return boolean
	 */
	public boolean removeItem(Item item) {
		return magicSack.remove(item);
	}
}
