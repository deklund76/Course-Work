///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
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

/**
 * Class representing different users in the database, with their ID and stacks
 * for undo and redoing actions
 * 
 * @author Drew
 */
public class User {
	//private fields
	private String userID;
	private SimpleStack<WAL> undoStack = new SimpleStack<WAL>();
	private SimpleStack<WAL> redoStack = new SimpleStack<WAL>();

	/**
	 * Creates a User. Throws IllegalArgumentException is userId is invalid.
	 * 
	 */
	public User(String userId) {
		if (userId == null || userId.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.userID = userId;
	}

	/**
	 * Returns the top WAL for undo. If there is no such WAL, returns null.
	 * 
	 */
	public WAL popWALForUndo() {
		return undoStack.pop();
	}

	/**
	 * Returns the top WAL for redo. If there is no such WAL, returns null.
	 * 
	 */
	public WAL popWALForRedo() {
		return redoStack.pop();
	}

	/**
	 * Pushes the WAL into undo stack. Throws IllegalArgumentException if wal is
	 * null.
	 * 
	 */
	public void pushWALForUndo(WAL trans) {
		if (trans == null) {
			throw new IllegalArgumentException();
		}
		undoStack.push(trans);
	}

	/**
	 * Pushes the WAL into redo stack. Throws IllegalArgumentException if wal is
	 * null.
	 * 
	 */
	public void pushWALForRedo(WAL trans) {
		if (trans == null) {
			throw new IllegalArgumentException();
		}
		redoStack.push(trans);
	}

	/**
	 *Clear all redo WALs.
	 */
	public void clearAllRedoWAL() {
		redoStack.clear();
	}

	/**
	 *Clear all undo WALs.
	 */
	public void clearAllUndoWAL() {
		undoStack.clear();
	}

	/**
	 * return the User's ID.
	 */
	public String getUserId() {
		return userID;
	}
}
