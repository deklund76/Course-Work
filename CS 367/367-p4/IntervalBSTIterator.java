///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  Scheduler.java
// File:             IntervalBSTIterator.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;

/**
 * An iterator over the IntervalBST.
 * 
 * @author Drew
 */
public class IntervalBSTIterator<K extends Interval> implements Iterator<K> {

	private Stack<IntervalBSTnode<K>> stack; // for keeping track of nodes

	/**
	 * Constructs an IntervalBSTIterator using the root of the IntervalBST tree.
	 * 
	 * @param root
	 */
	public IntervalBSTIterator(IntervalBSTnode<K> root) {
		stack = new Stack<IntervalBSTnode<K>>();

		while (root != null) {
			stack.push(root);
			root = root.getLeft();
		}
	}

	/**
	 * Returns true if there are more elements to iterate over, otherwise
	 * returns false.
	 * @return boolean
	 */
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	/**
	 * Returns the next element, while iterating over the nodes of the
	 * IntervalBST tree.
	 * 
	 * @return K
	 */
	public K next() {
		IntervalBSTnode<K> node = stack.pop();
		K next = node.getKey();
		if (node.getRight() != null) {
			node = node.getRight();
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}
		}
		return next;
	}

	/**
	 * unused method
	 */
	public void remove() {
		// DO NOT CHANGE: you do not need to implement this method
		throw new UnsupportedOperationException();
	}
}