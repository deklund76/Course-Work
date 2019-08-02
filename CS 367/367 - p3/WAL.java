///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
// File:             WAL.java
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
 * Class representing the state before any operation performed, used to undo and
 * redo any operation.
 * 
 * @author Drew
 *
 */
public class WAL {
	
	//private fields
	private int oldValue;
	private int rowIndex;
	private int colIndex;

	/**
	 * Creates a WAL, which records what the old value is before an operation.
	 * Throws IllegalArgumentException if any argument is invalid.
	 * 
	 */
	public WAL(int rowIndex, int colIndex, int oldValue) {

		if (rowIndex < 0 || colIndex < 0) {
			throw new IllegalArgumentException();
		}

		this.oldValue = oldValue;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}

	/**
	 * Returns the old value.
	 * 
	 * @return int
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Returns the row Index.
	 * 
	 * @return int
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * Returns the col Index.
	 * 
	 * @return int
	 */
	public int getColIndex() {
		return colIndex;
	}

}
