///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Main Class File:  Server.java
// File:             Database.java
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
 * Class representing a single database on the server with a list of documents
 * 
 * 
 * @author Drew
 */
public class Database {

	//private fields
	private ArrayList<Document> documentList;

	/**
	 * Constructs an empty database.
	 */
	public Database() {
		documentList = new ArrayList<Document>();
	}

	/**
	 * Adds one document to database. Throws IllegalArgumentException if doc is
	 * invalid.
	 * 
	 */
	public void addDocument(Document doc) {
		if (doc == null) {
			throw new IllegalArgumentException();
		}
		documentList.add(doc);
	}

	/**
	 * Returns the list of documents in the database.
	 * 
	 */
	public List<Document> getDocumentList() {
		return documentList;
	}

	/**
	 * Applies the given operation to the database. Returns the formatted string
	 * representation of the updated document content. Throw
	 * IllegalArgumentException if the operation is invalid, e.g. the document
	 * is not in the database.
	 * 
	 */
	public String update(Operation operation) {

		Document doc = getDocumentByDocumentName(operation.getDocName());

		if (doc == null) {
			throw new IllegalArgumentException();
		}

		doc.update(operation);

		String update = "----------Update Database----------\n";
		update += operation.toString() + "\n\n";
		update += doc.toString();

		return update;
	}

	/**
	 * Returns one document based on document id. Returns nullif there is no
	 * such document in the database.
	 * 
	 */
	public Document getDocumentByDocumentName(String docName) {
		for (int k = 0; k < documentList.size(); k++) {
			if (documentList.get(k).getDocName().equals(docName)) {
				return documentList.get(k);
			}
		}
		return null;
	}

}
