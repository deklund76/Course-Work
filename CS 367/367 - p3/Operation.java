///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
// File:             Operation.java
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
 * Class representing a single change in the database. A change might include
 * creating a table, undo, addition, etc. represented by the enumeration OP
 * 
 * @author Drew
 */
public class Operation {
	// Enumeration of operator type.
	public enum OP {
		SET, // set,row,col,const -> set [row,col] to const
		CLEAR, // clear,row,col -> set [row,col] to 0
		ADD, // add,row,col,const -> add [row,col] by const
		SUB, // sub,row,col,const -> sub [row,col] by const
		MUL, // mul,row,col,const -> mul [row,col] by const
		DIV, // div,row,col,const -> div [row,col] by const
		UNDO, // undo the last operation
		REDO // redo the last undo
	}

	//private fields
	private String userID;
	private OP op;
	private String docName;
	private int rowIndex;
	private int colIndex;
	private int constant;
	private long timestamp;

	/**
	 * This constructor is for operations that involve only one cell (i.e.
	 * incrementing cell[rowIndex, colIndex] by a constant). Throws
	 * IllegealArgumentException if op is not supported, or arguments are not
	 * valid.
	 */
	public Operation(String docName, String userId, OP op, int rowIndex,
			int colIndex, int constant, long timestamp) {

		String opVal = op.toString();

		if (docName == null || docName.isEmpty() || userId == null
				|| userId.isEmpty() || opVal.equalsIgnoreCase("UNDO")
				|| opVal.equalsIgnoreCase("REDO")
				|| opVal.equalsIgnoreCase("CLEAR") || rowIndex < 0
				|| colIndex < 0) {
			throw new IllegalArgumentException();
		}

		this.docName = docName;
		userID = userId;
		this.op = op;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.constant = constant;
		this.timestamp = timestamp;
	}

	/**
	 * This constructor is for operations without constant(i.e. clear).Throws
	 * IllegealArgumentException if op is not supported, or arguments are not
	 * valid.
	 */
	public Operation(String docName, String userId, OP op, int rowIndex,
			int colIndex, long timestamp) {

		String opVal = op.toString();

		if (docName == null || docName.isEmpty() || userId == null
				|| userId.isEmpty() || !opVal.equalsIgnoreCase("CLEAR")
				|| rowIndex < 0 || colIndex < 0) {
			throw new IllegalArgumentException();
		}

		this.docName = docName;
		userID = userId;
		this.op = op;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.timestamp = timestamp;
	}

	/**
	 * This constructor is for operations that involve a document as a whole
	 * (i.e. undo previous action).Throws IllegealArgumentException if op is not
	 * supported, or arguments are not valid.
	 */
	public Operation(String docName, String userId, OP op, long timestamp) {

		String opVal = op.toString();

		if (docName == null
				|| docName.isEmpty()
				|| userId == null
				|| userId.isEmpty()
				|| !(opVal.equalsIgnoreCase("UNDO") || opVal
						.equalsIgnoreCase("REDO"))) {
			throw new IllegalArgumentException();
		}

		this.docName = docName;
		userID = userId;
		this.op = op;
		this.timestamp = timestamp;
	}

	/**
	 * Returns the document Name.
	 * 
	 * @return String
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * Returns the userID.
	 * 
	 * @return String
	 */
	public String getUserId() {
		return userID;
	}

	/**
	 * Returns the operator of this operation.
	 * 
	 * @return OP
	 */
	public OP getOp() {
		return op;
	}

	/**
	 * Returns the row that is involved. Returns -1 if this operation does not
	 * involve a cell.
	 * 
	 * @return int
	 */
	public int getRowIndex() {
		if (getOp().toString().equalsIgnoreCase("UNDO")
				|| getOp().toString().equalsIgnoreCase("REDO")) {
			return -1;
		}
		return rowIndex;
	}

	/**
	 * Returns the col that is involved. Returns -1 if this operation does not
	 * involve a cell.
	 * 
	 * @return int
	 */
	public int getColIndex() {
		if (getOp().toString().equalsIgnoreCase("UNDO")
				|| getOp().toString().equalsIgnoreCase("REDO")) {
			return -1;
		}
		return colIndex;
	}

	/**
	 * Returns the timestamp of this operation.
	 * 
	 * @return long
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the constant that is involved. Returns -1 if this operation does
	 * not involve a cell.
	 * 
	 * @return long
	 */
	public int getConstant() {
		if (getOp().toString().equalsIgnoreCase("UNDO")
				|| getOp().toString().equalsIgnoreCase("REDO")
				|| getOp().toString().equalsIgnoreCase("CLEAR")) {
			return -1;
		}
		return constant;
	}

	/**
	 * Returns the string representation of this operation.
	 * 
	 * @return String
	 */
	public String toString() {
		if (op.toString().equals("CLEAR")) {
			String clear = timestamp + "\t" + docName + "\t" + userID + "\t"
					+ op + "\t[" + rowIndex + "," + colIndex + "]";
			return clear;
		} else if (op.toString().equals("UNDO") || op.toString().equals("REDO")) {
			String undo = timestamp + "\t" + docName + "\t" + userID + "\t"
					+ op;
			return undo;
		} else {
			String normalOp = timestamp + "\t" + docName + "\t" + userID + "\t"
					+ op + "\t[" + rowIndex + "," + colIndex + "]" + "\t"
					+ constant;
			return normalOp;
		}
	}
}
