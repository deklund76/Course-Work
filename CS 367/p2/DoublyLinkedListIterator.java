///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  UWmail.java
// File:             DoublyLinkedListIterator.java
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
import java.util.NoSuchElementException;

/**
 * Object class representing a single email.
 *
 * 
 *
 * @author Drew Eklund
 */
public class DoublyLinkedListIterator<E> implements Iterator<E> {

	// *** fields ***
	private Listnode<E> items; // the list we're iterating over
	private int curPos; // the position of the next item

	/**
	 * Constructs an iterator for the DoublyLinkedList data structure
	 * 
	 */
	public DoublyLinkedListIterator(Listnode<E> head) {
		items = head;
		curPos = 0;
	}

	/**
	 * Returns true if the iteration has more elements. (In other words, returns
	 * true if next() would return an element rather than throwing an
	 * exception.)
	 * 
	 * @return boolean
	 */
	@Override
	public boolean hasNext() {
		if (items.getNext() == null) {
			return false;
		}
		return true;
	}

	/**
	 * returns the next element in the list being iterated.
	 * 
	 * @return E
	 */
	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E result = items.getData();
		items = items.getNext();
		curPos++;
		return result;
	}

	/**
	 * not supported for this iterator
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
