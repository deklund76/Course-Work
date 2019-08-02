///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  Scheduler.java
// File:             IntervalBSTnode.java
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
 * A node of the IntervalBST that contains an object of type Interval interface
 * as its key. In this program, the Event class implements that interface.
 * 
 * @author Drew
 */
class IntervalBSTnode<K extends Interval> {
	//private data fields
	private K key;
	private IntervalBSTnode<K> left;
	private IntervalBSTnode<K> right;
	private long maxEnd;

	/**
	 * Constructs a BST node with the given key value and whose left and right
	 * children are null.
	 * 
	 * @param keyValue
	 */
	IntervalBSTnode(K keyValue) {
		key = keyValue;
		maxEnd = key.getEnd();
	}

	/**
	 * Constructs a BST node with the given key value, left child, and right
	 * child.
	 * 
	 * @param keyValue
	 * @param leftChild
	 * @param rightChild
	 * @param maxEnd
	 */
	public IntervalBSTnode(K keyValue, IntervalBSTnode<K> leftChild,
			IntervalBSTnode<K> rightChild, long maxEnd) {
		key = keyValue;
		left = leftChild;
		right = rightChild;
		this.maxEnd = maxEnd;
	}

	/**
	 * Returns the key value for this BST node.
	 * @return K
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Returns the left child for this BST node.
	 * @return IntervalBSTnode<K>
	 */
	public IntervalBSTnode<K> getLeft() {
		return left;
	}

	/**
	 * Returns the right child for this BST node.
	 * @return IntervalBSTnode<K>
	 */
	public IntervalBSTnode<K> getRight() {
		return right;
	}

	/**
	 * Returns the maximum end value of the intervals in this node's subtree.
	 * @return long
	 */
	public long getMaxEnd() {
		return maxEnd;
	}

	/**
	 * Changes the key value for this node to the one given.
	 * @param newK
	 */
	public void setKey(K newK) {
		key = newK;
	}

	/**
	 * Changes the left child for this node to the one given.
	 * @param newL
	 */
	public void setLeft(IntervalBSTnode<K> newL) {
		left = newL;
	}

	/**
	 * Changes the right child for this node to the one given.
	 * @param newR
	 */
	public void setRight(IntervalBSTnode<K> newR) {
		right = newR;
	}

	/**
	 * Changes the maxEnd to the updated maximum end in the subtree.
	 * @param newEnd
	 */
	public void setMaxEnd(long newEnd) {
		maxEnd = newEnd;
	}

	/**
	 * Returns the start timestamp of this interval.
	 * @return long
	 */
	public long getStart() {
		return key.getStart();
	}

	/**
	 * Returns the end timestamp of this interval.
	 * @return long
	 */
	public long getEnd() {
		return key.getEnd();
	}

	/**
	 * Returns the key value of this BST node.
	 * @return K
	 */
	public K getData() {
		return key;
	}

}