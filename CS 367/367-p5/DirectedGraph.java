///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Main Class File:  TheGame.java
// File:             DirectedGraph.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements GraphADT with an adjacency lists representation of a directed
 * graph
 * 
 * @author CS367
 */
public class DirectedGraph<V> implements GraphADT<V> {
	private HashMap<V, ArrayList<V>> hashmap;

	// DO NOT ADD ANY OTHER DATA MEMBERS

	/**
	 * Constructs an empty DirectedGraph
	 */
	public DirectedGraph() {
		hashmap = new HashMap<V, ArrayList<V>>();
	}

	/**
	 * Creates a graph from a preconstructed hashmap. This method will be used
	 * for testing your submissions.
	 * 
	 * @param hashmap
	 */
	public DirectedGraph(HashMap<V, ArrayList<V>> hashmap) {
		if (hashmap == null) {
			throw new IllegalArgumentException();
		}
		this.hashmap = hashmap;
	}

	@Override
	public boolean addVertex(V vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException();
		}
		if (hashmap.containsKey(vertex)) {
			return false;
		}
		hashmap.put(vertex, new ArrayList<V>());
		return true;
	}

	@Override
	public boolean addEdge(V v1, V v2) {
		if (!hashmap.containsKey(v1) || !hashmap.containsKey(v2)) {
			throw new IllegalArgumentException();
		}
		if ((v1 == v2) || hashmap.get(v1).contains(v2)) {
			return false;
		}
		hashmap.get(v1).add(v2);
		return true;
	}

	@Override
	public Set<V> getNeighbors(V vertex) {
		if (!hashmap.containsKey(vertex)) {
			throw new IllegalArgumentException();
		}
		return new HashSet<V>(hashmap.get(vertex));
	}

	@Override
	public void removeEdge(V v1, V v2) {
		if ((v1 == null) || (v2 == null)) {
			throw new IllegalArgumentException();
		}
		if (hashmap.containsKey(v1) && hashmap.containsKey(v2)
				&& hashmap.get(v1).contains(v2)) {
			hashmap.get(v1).remove(v2);
		}
	}

	@Override
	public Set<V> getAllVertices() {
		return hashmap.keySet();
	}

	@Override
	// Returns a String that depicts the Structure of the Graph
	// This prints the adjacency list
	// This has been done for you
	// DO NOT MODIFY
	public String toString() {
		StringWriter writer = new StringWriter();
		for (V vertex : this.hashmap.keySet()) {
			writer.append(vertex + " -> " + hashmap.get(vertex) + "\n");
		}
		return writer.toString();
	}

}
