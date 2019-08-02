///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  Scheduler.java
// File:             IntervalBST.java
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
 * A Binary Search Tree (BST) that implements the SortedListADT interface. Its
 * implementation uses IntervalBSTnodes as the nodes of the tree.
 * 
 * @author Drew
 */
public class IntervalBST<K extends Interval> implements SortedListADT<K> {
	//private data fields
	private IntervalBSTnode<K> root;
	private int numItems = 0;

	/**
	 * Constructs an empty IntervalBST
	 */
	public IntervalBST() {
	}

	/**
	 * Inserts the given key into the Sorted List.
	 * 
	 * @param K
	 */
	public void insert(K key) {
		root = insert(root, key);
		numItems++;
	}

	/**
	 * Deletes the given key from the Sorted List. If the key is in the Sorted
	 * List, the key is deleted and true is returned. If the key is not in the
	 * Sorted List, the Sorted List is unchanged and false is returned.
	 * 
	 * @param K
	 * @return boolean
	 */
	public boolean delete(K key) {
		if (this.lookup(key) != null) {
			root = delete(root, key);
			numItems--;
			return true;
		}
		return false;
	}

	/**
	 * Searches for the given key in the Sorted List and returns it. If the key
	 * is not in the Sorted List, null is returned.
	 * 
	 * @param K
	 * @return K
	 */
	public K lookup(K key) {
		return this.lookup(root, key);
	}

	/**
	 * Returns the number of items in the Sorted List.
	 * 
	 * @return int
	 */
	public int size() {
		return numItems;
	}

	/**
	 * Returns true if and only if the Sorted List is empty.
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns an iterator over the Sorted List that iterates over the items in
	 * the Sorted List from smallest to largest.
	 * @return iterator
	 */
	public Iterator<K> iterator() {
		return new IntervalBSTIterator(root);
	}

	// private helper methods

	/**
	 * private helper class for insert
	 * @param n
	 * @param key
	 * @return IntervalBSTnode<K>
	 * @throws IntervalConflictException
	 */
	private IntervalBSTnode<K> insert(IntervalBSTnode<K> n, K key)
			throws IntervalConflictException {
		if (n == null) {
			return new IntervalBSTnode<K>(key);
		}

		if (n.getKey().equals(key)) {
			throw new IntervalConflictException();
		}

		if (key.compareTo(n.getKey()) < 0) {
			// add key to the left subtree
			n.setLeft(insert(n.getLeft(), key));
			return n;
		}

		else {
			// add key to the right subtree
			if (n.getMaxEnd() < key.getEnd()) {
				n.setMaxEnd(key.getEnd());
			}
			n.setRight(insert(n.getRight(), key));
			return n;
		}
	}

	/**
	 * private helper class for delete
	 * @param n
	 * @param key
	 * @return IntervalBSTnode<K>
	 */
	private IntervalBSTnode<K> delete(IntervalBSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		if(n.getMaxEnd() == key.getEnd()) {
			IntervalBSTnode<K> tmp = n;
			while(tmp.getRight() != null) {
				tmp = tmp.getRight();
			}
			n.setMaxEnd(tmp.getEnd());
		}
		
		if (key.equals(n.getKey())) {
			// n is the node to be removed
			if (n.getLeft() == null && n.getRight() == null) {
				return null;
			}
			if (n.getLeft() == null) {
				return n.getRight();
			}
			if (n.getRight() == null) {
				return n.getLeft();
			}

			// if we get here, then n has 2 children
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight(delete(n.getRight(), smallVal));
			return n;
		}

		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(delete(n.getLeft(), key));
			return n;
		}

		else {
			n.setRight(delete(n.getRight(), key));
			return n;
		}
	}

	/**
	 * private helper class for delete's private helper class
	 * @param n
	 * @return K
	 */
	private K smallest(IntervalBSTnode<K> n)
	// precondition: n is not null
	// postcondition: return the smallest value in the subtree rooted at n

	{
		if (n.getLeft() == null) {
			return n.getKey();
		} else {
			return smallest(n.getLeft());
		}
	}

	/**
	 * private helper class for lookup
	 * @param n
	 * @param key
	 * @return K
	 */
	private K lookup(IntervalBSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		if (n.getKey().equals(key)) {
			return key;
		}

		if (key.compareTo(n.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return lookup(n.getLeft(), key);
		}

		else {
			// key > this node's key; look in right subtree
			return lookup(n.getRight(), key);
		}
	}

}