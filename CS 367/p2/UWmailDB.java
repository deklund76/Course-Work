///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  UWmail.java
// File:             UWmailDB.java
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
 * Object class representing the database/datastructure to be used by UWmail.
 *
 * 
 *
 * @author Drew Eklund
 */
public class UWmailDB {
	private ListADT<Conversation> inbox;
	private ListADT<Conversation> trash;

	/**
	 * Constructs an empty UWmailDB.
	 * 
	 */
	public UWmailDB() {
		inbox = new DoublyLinkedList<Conversation>();
		trash = new DoublyLinkedList<Conversation>();
	}

	/**
	 * Return the number of conversations in the inbox.
	 * 
	 * @return int inbox size
	 */
	public int size() {
		return inbox.size();
	}

	/**
	 * Determines the correct email conversation to add e to, and does so. If
	 * there is no such conversation, it adds a new one to the inbox
	 * 
	 * @param e
	 */
	public void addEmail(Email e) {
		for (int k = 0; k < inbox.size(); k++) {
			if (e.getReferences() == null) {
				inbox.add(0, new Conversation(e));
				return;
			} else if (e.getReferences().contains(
					inbox.get(k).get(0).getMessageID())) {
				inbox.get(k).add(e);
				return;
			}
		}
		inbox.add(0, new Conversation(e));
	}

	/**
	 * Returns a DoublyLinkedList of Conversation's, representing the
	 * conversations in the inbox.
	 * 
	 * @return ListADT<Conversation> inbox
	 */
	public ListADT<Conversation> getInbox() {
		return inbox;
	}

	/**
	 * Returns a DoublyLinkedList of Conversation's, representing the
	 * conversations in the trash.
	 * 
	 * @return ListADT<Conversation> trash
	 */
	public ListADT<Conversation> getTrash() {
		return trash;
	}

	/**
	 * Moves the conversation at the nth index of the inbox to the trash.
	 * 
	 */
	public void deleteConversation(int idx) {
		trash.add(inbox.remove(idx));
	}

}
