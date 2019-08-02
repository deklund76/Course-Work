///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  UWmail.java
// File:             Conversation.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Iterator;

/**
 * Object class representing a single email.
 *
 * 
 *
 * @author Drew Eklund
 */
public class Conversation implements Iterable<Email> {
	private DoublyLinkedList<Email> conversation;
	private int current = 0;

	/**
	 * Constructs a Conversation with the given email as the first and only
	 * email.
	 * 
	 * @param e
	 */
	public Conversation(Email e) {
		conversation = new DoublyLinkedList<Email>();
		conversation.add(e);
		current = 0;
	}

	/**
	 * Returns the index of the email which the user had in focus last time they
	 * viewed this conversation. If the Conversation has not been viewed yet,
	 * then return index of the last email in the conversation (the newest).
	 * 
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * Moves the pointer to the last viewed email back one, i.e. the user is
	 * viewing a conversation and presses p for the previous message.
	 * 
	 */
	public void moveCurrentBack() {
		current--;
	}

	/**
	 * Move the pointer to the last viewed email forward one, i.e. the user is
	 * viewing a conversation and presses n for the next message.
	 * 
	 */
	public void moveCurrentForward() {
		current++;
	}

	/**
	 * Return the number of emails in the conversation.
	 * 
	 */
	public int size() {
		return conversation.size();
	}

	/**
	 * Return the nth Email from the conversation.
	 * 
	 * @param int n
	 */
	public Email get(int n) {
		return conversation.get(n);
	}

	/**
	 * Add the email to the conversation. Note: we can assume that all emails in
	 * the .zip are in reverse chronological order, so every call to add(Email
	 * e) should be with an email that is older than the last.
	 * 
	 * @param e
	 */
	public void add(Email e) {
		conversation.add(e);
		current++;
	}

	/**
	 * Return an Iterator that can be used to iterate over the emails in the
	 * conversation.
	 * 
	 */
	public Iterator<Email> iterator() {
		return conversation.iterator();
	}

}
