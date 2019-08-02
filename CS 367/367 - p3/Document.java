///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
// File:             Document.java
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
 * Class representing a single document, i.e. a 2-d numeric table, in the
 * database. It stores all the users involved in this document and keeps track
 * of the actual data in a two-dimensional array.
 * 
 * @author Drew
 */
public class Document {
	
	//private fields
	private String docName;
	private int[][] doc;
	private int rowSize;
	private int colSize;
	private ArrayList<User> userList;

	/**
	 * Constructor for a single document. throws an IllegalArgumentException if
	 * any of the input is invalid.
	 */
	public Document(String docName, int rowSize, int colSize,
			List<User> userList) {

		if (docName == null || docName.isEmpty() || rowSize < 0 || colSize < 0
				|| userList == null) {
			throw new IllegalArgumentException();
		}

		this.docName = docName;
		this.rowSize = rowSize;
		this.colSize = colSize;
		this.userList = (ArrayList<User>) userList;
		doc = new int[rowSize][colSize];
	}

	/**
	 * Returns the user IDs for this document. You may or may not use this
	 * method in your program.
	 * 
	 * @return List<String>
	 */
	public List<String> getAllUserIds() {
		ArrayList<String> allUserIDs = new ArrayList<String>();
		for (int k = 0; k < userList.size(); k++) {
			allUserIDs.add(userList.get(k).getUserId());
		}
		return allUserIDs;
	}

	/**
	 * Applies the input operation to the document. Throws a
	 * IllegalArgumentException if the operation is not valid.
	 * 
	 * @param operation
	 */
	public void update(Operation operation) {

		User user = this.getUserByUserId(operation.getUserId());
		int row = operation.getRowIndex();
		int col = operation.getColIndex();

		if (user == null || col >= this.colSize || row >= this.rowSize) {
			throw new IllegalArgumentException();
		}

		if (operation.getOp().toString().equalsIgnoreCase("UNDO")) {
			WAL undo = user.popWALForUndo();
			int unrow = undo.getRowIndex();
			int uncol = undo.getColIndex();
			WAL newWAL = new WAL(unrow, uncol, doc[unrow][uncol]);
			doc[unrow][uncol] = undo.getOldValue();
			user.pushWALForRedo(newWAL);
		}

		else if (operation.getOp().toString().equalsIgnoreCase("REDO")) {
			WAL redo = user.popWALForRedo();
			int rerow = redo.getRowIndex();
			int recol = redo.getColIndex();
			WAL newWAL = new WAL(rerow, recol, doc[rerow][recol]);
			doc[redo.getRowIndex()][redo.getColIndex()] = redo.getOldValue();
			user.pushWALForUndo(newWAL);
		}

		else {

			WAL newWAL = new WAL(row, col, doc[row][col]);
			user.pushWALForUndo(newWAL);

			if (operation.getOp().toString().equalsIgnoreCase("SET")) {
				System.out.println("Set made");
				doc[row][col] = operation.getConstant();
			}

			else if (operation.getOp().toString().equalsIgnoreCase("CLEAR")) {
				doc[row][col] = 0;
			}

			else if (operation.getOp().toString().equalsIgnoreCase("ADD")) {
				doc[row][col] += operation.getConstant();
			}

			else if (operation.getOp().toString().equalsIgnoreCase("SUB")) {
				System.out.println("Sub made");
				doc[row][col] -= operation.getConstant();
			}

			else if (operation.getOp().toString().equalsIgnoreCase("MUL")) {
				doc[row][col] *= operation.getConstant();
			}

			else if (operation.getOp().toString().equalsIgnoreCase("DIV")) {
				doc[row][col] /= operation.getConstant();
			}

			user.clearAllRedoWAL();

		}

	} // end method

	/**
	 * Returns the document Name.
	 * 
	 * @return String
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * Returns the user of this document by user id. Returns null if there is no
	 * such user for this document
	 * 
	 * @param String
	 * @return User
	 */
	public User getUserByUserId(String userId) {
		for (int k = 0; k < userList.size(); k++) {
			if (userList.get(k).getUserId().equals(userId)) {
				return userList.get(k);
			}
		}
		return null;
	}

	/**
	 * Returns the value of the cell specified by the given row index and col
	 * index. Throws IllegalArgumentException if the index is out of bound.
	 * 
	 * @param int, int
	 * @return int
	 */
	public int getCellValue(int rowIndex, int colIndex) {
		if (rowIndex < 0 || colIndex < 0 || rowIndex > doc.length
				|| colIndex > doc[rowIndex].length) {
			throw new IllegalArgumentException();
		}
		return doc[rowIndex][colIndex];
	}

	/**
	 * Returns the string representation of this document. This method should be
	 * called for Document Content part in output.
	 * 
	 * @return String
	 */
	public String toString() {
		String description = "Document Name: " + docName + "\tSize: ["
				+ rowSize + "," + colSize + "]\nTable:\n";
		for (int k = 0; k < doc.length; k++) {
			for (int j = 0; j < doc[k].length; j++) {
				description += doc[k][j] + "\t";
			}
			description += "\n";
		}
		return description;
	}
}
