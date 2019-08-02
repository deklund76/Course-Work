///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
// File:             SimpleQueue.java
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
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class SimpleQueue<E> implements QueueADT<E> {

	//private fields
	private Listnode<E> qFront;
	private Listnode<E> qRear;
	private int numItems = 0;

	public SimpleQueue() {
	}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if (isEmpty()) {
			Listnode<E> newNode = new Listnode<E>(item);
			qRear = newNode;
			qFront = newNode;
			numItems++;
			return;
		}
		qRear.setPrev(new Listnode<E>(item, qRear, null));
		qRear = qRear.getPrev();
		numItems++;
	}

	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue() {
		if (isEmpty()) {
			throw new EmptyQueueException();
		}
		E tmp = qFront.getData();
		if (qFront.getPrev() != null) {
			qFront.getPrev().setNext(null);
			qFront = qFront.getPrev();
		}
		numItems--;
		return tmp;
	}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek() {
		if (isEmpty()) {
			throw new EmptyQueueException();
		}
		return qFront.getData();
	}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty() {
		if (numItems == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear() {
		qFront = null;
		qRear = null;
		numItems = 0;
	}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size() {
		return numItems;
	}
}
