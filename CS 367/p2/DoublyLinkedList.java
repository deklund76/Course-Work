///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  UWmail.java
// File:             DoublyLinkedList.java
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
 * @author Drew Eklund
 */
public class DoublyLinkedList<E> implements ListADT<E> {
	private Listnode<E> items; // pointer to the header node of the list of
	// items
	private int numItems;
	private Listnode<E> lastNode;

	/**
	 * Constructs a DoublyLinkedList data structure
	 * 
	 */
	public DoublyLinkedList() {
		numItems = 0;
	}

	/**
	 * returns an iterator for the DoublyLinkedList data structure
	 * 
	 * @return Iterator<E>
	 */
	@Override
	public Iterator<E> iterator() {
		return new DoublyLinkedListIterator<E>(items);
	}

	/**
	 * Adds an item to the list by creating a node and connecting it
	 * 
	 * @param E item
	 */
	@Override
	public void add(E item) {
		Listnode<E> newNode = new Listnode<E>(item);

		if (numItems == 0) {
			items = newNode;
			lastNode = newNode;
			numItems++;
			return;
		}
		
		

		lastNode.setNext(newNode);
		newNode.setPrev(lastNode);
		lastNode = newNode;
		numItems++;
	}

	/**
	 * Adds an item to the list by creating a node and connecting it
	 * given a specific index "pos"
	 * 
	 * @param int pos
	 * @param E item
	 */
	@Override
	public void add(int pos, E item) {
		// check for bad position
	    if (pos < 0 || pos > numItems) {
	        throw new IndexOutOfBoundsException();
	    }
		
	    // if asked to add to end, let the other add method do the work
	    if (pos == numItems) {
	        add(item);
	        return;
	    }
	    
	    //new node to be added
	    Listnode<E> newNode = new Listnode<E>(item);
	    
	    if (pos == 0) {
	        newNode.setNext(items);
	        items.setPrev(newNode);
	        items = newNode;
	        numItems++;
	        return;
	    }
	 
	    // find the node n after which to add a new node and add the new node
	    Listnode<E> n = items;
	    for (int k = 0; k < pos; k++) {
	        n = n.getNext();
	    }
	    Listnode<E> p = n.getPrev();
	    newNode.setPrev(p);
	    newNode.setNext(n);
	    p.setNext(newNode);
	    n.setPrev(newNode);
	    numItems++;
	}

	/**
	 * returns true if this list contains the element "item", false otherwise
	 * 
	 * @param E item
	 * @return boolean
	 */
	@Override
	public boolean contains(E item) {
		Listnode<E> n = items;
		for(int k = 0; k < numItems; k++) {
			if(n.getData().equals(item)) {
				return true;
			}
			if(n != lastNode) {
			n = n.getNext();
			}
		}
		return false;
	}

	/**
	 * returns the element in the list at the given position
	 * 
	 * @param int pos
	 * @return E result
	 */
	@Override
	public E get(int pos) {
		if (pos < 0 || pos > numItems) {
	        throw new IndexOutOfBoundsException();
	    }
		if(pos > numItems/2) {
			Listnode<E> n = lastNode;
			int reverse = numItems - pos;
		for (int k = 0; k < reverse; k++) {
	        n = n.getPrev();
	    }
		return n.getData();
		}
		else {
			Listnode<E> n = items;
			for (int k = 0; k < pos; k++) {
		        n = n.getNext();
		    }
			return n.getData();
		}
	}

	/**
	 * returns true if this list is empty, false otherwise.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isEmpty() {
		if(items == null) {
			return true;
		}
		return false;
	}

	/**
	 * removes a node from the list and returns its element
	 * 
	 * @param int pos
	 * @return E data
	 */
	@Override
	public E remove(int pos) {
		if(pos > numItems/2) {
			Listnode<E> n = lastNode;
			int reverse = numItems - pos;
		for (int k = 0; k < reverse; k++) {
	        n = n.getPrev();
	    }
		if(n == lastNode) {
			lastNode = n.getPrev();
			lastNode.setNext(null);
			numItems--;
			return n.getData();
		}
		n.getPrev().setNext(n.getNext());
		n.getNext().setPrev(n.getPrev());
		numItems--;
		return n.getData();
		}
		else {
			Listnode<E> n = items;
			for (int k = 0; k < pos; k++) {
		        n = n.getNext();
		    }
			if(n == items) {
				items = n.getNext();
				items.setPrev(null);
			}
			n.getPrev().setNext(n.getNext());
			n.getNext().setPrev(n.getPrev());
			numItems--;
			return n.getData();
		}
	}

	/**
	 * Return the number of items in the list
	 * 
	 * @return int numItems
	 */
	@Override
	public int size() {
		return numItems;
	}

	
}
