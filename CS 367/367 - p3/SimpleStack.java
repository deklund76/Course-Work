///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
// File:             SimpleStack.java
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
 * An ordered collection of items, where items are both added and removed from
 * the top.
 * 
 * @author CS367
 */
public class SimpleStack<E> implements StackADT<E> {

	//private fields
	private Listnode<E> items;
	private int numItems = 0;

	public SimpleStack() {
	}

	/**
	 * Adds item to the top of the stack.
	 * 
	 * @param item
	 *            the item to add to the stack.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void push(E item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(this.isEmpty()) {
			items = new Listnode<E>(item);
			numItems++;
			return;
		}
		items = new Listnode<E>(item, items, null);
		numItems++;
	}

	/**
	 * Removes the item on the top of the Stack and returns it.
	 * 
	 * @return the top item in the stack.
	 * @throws EmptyStackException
	 *             if the stack is empty.
	 */
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		E tmp = items.getData();
		items = items.getNext();
		numItems--;
		return tmp;
	}

	/**
	 * Returns the item on top of the Stack without removing it.
	 * 
	 * @return the top item in the stack.
	 * @throws EmptyStackException
	 *             if the stack is empty.
	 */
	public E peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return items.getData();
	}

	/**
	 * Returns true iff the Stack is empty.
	 * 
	 * @return true if stack is empty; otherwise false.
	 */
	public boolean isEmpty() {
		if(numItems == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Removes all items on the stack leaving an empty Stack.
	 */
	public void clear() {
		items = null;
	}

	/**
	 * Returns the number of items in the Stack.
	 * 
	 * @return the size of the stack.
	 */
	public int size() {
		return numItems;
	}
}
