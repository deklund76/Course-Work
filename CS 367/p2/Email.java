///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p1
// Main Class File:  UWmail.java
// File:             Email.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Object class representing a single email.
 *
 * 
 *
 * @author Drew Eklund
 */
public class Email {
	// private member variables
	private Date date;
	private String messageID;
	private String subject;
	private String from;
	private String to;
	private ListADT<String> body;
	private String inReplyTo;
	private ListADT<String> references;

	/**
	 * Constructs an Email with the given attributes. This is the constructor
	 * for an email that is the first in the conversation, i.e. without
	 * In-Reply-To or References fields.
	 * 
	 * @param (date) (date email was recieved)
	 * @param (messageID) (email's unique ID)
	 * @param (subject) (subject of email)
	 * @param (from) (address of sender)
	 * @param (to) (address of reciever)
	 * @param (body) (List of lines of text written in body of email)
	 */
	public Email(Date date, String messageID, String subject, String from,
			String to, ListADT<String> body) {
		this.date = date;
		this.messageID = messageID;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.body = body;
	}

	/**
	 * Constructs an Email with the given attributes. This is the constructor
	 * for an email that is not the first in the conversation, i.e. contains
	 * In-Reply-To and References fields.
	 * 
	 * @param (date) (date email was recieved)
	 * @param (messageID) (email's unique ID)
	 * @param (subject) (subject of email)
	 * @param (from) (address of sender)
	 * @param (to) (address of reciever)
	 * @param (body) (List of lines of text written in body of email)
	 * @param (inReplyTo) (message ID of email this email replies to)
	 * @param (references) (a list of all other previous emails in the
	 *        conversation)
	 */
	public Email(Date date, String messageID, String subject, String from,
			String to, ListADT<String> body, String inReplyTo,
			ListADT<String> references) {
		this.date = date;
		this.messageID = messageID;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.body = body;
		this.inReplyTo = inReplyTo;
		this.references = references;
	}

	/**
	 * Return the date in a human-readable form. If the date on the email is
	 * today, it's formated with a SimpleDateFormat object and the formatting
	 * string "h:mm a". Otherwise, it's formated with a SimpleDateFormat object
	 * and the formatting string "MMM d".
	 * 
	 * @return String date
	 */
	public String getDate() {
		Date now = new Date();
		//Format for comparing stored date object with newly created date "now"
		DateFormat comparableFormat = new SimpleDateFormat("EEE, dd MMM yyyy",
				Locale.US);
		
		String currentDate = comparableFormat.format(now);
		String emailDate = comparableFormat.format(date);
		if (currentDate.equals(emailDate)) {
			DateFormat resultFormat = new SimpleDateFormat("HH:mm a", Locale.US);
			String result = resultFormat.format(date);
			return result;
		}
		DateFormat resultFormat = new SimpleDateFormat("MMM d", Locale.US);
		String result = resultFormat.format(date);
		return result;
	}

	/**
	 * Returns the unique email identifier that was stored in the Message-ID
	 * field.
	 * 
	 * @return String messageID
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * Returns what was stored in the Subject: field.
	 * 
	 * @return String Subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Return what was stored in the From: field.
	 * 
	 * @return String from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Return what was stored in the To: field.
	 * 
	 * @return String to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Return the lines of text from the end of the header to the end of the
	 * file.
	 * 
	 * @return ListADT<String> body
	 */
	public ListADT<String> getBody() {
		return body;
	}

	/**
	 * Return what was stored in the In-Reply-To: field. If the email was the
	 * first in the conversation, return null.
	 * 
	 * @return String inReplyTo
	 */
	public String getInReplyTo() {
		return inReplyTo;
	}

	/**
	 * Return the Message-ID's from the References: field. If the email was the
	 * first in the conversation, return null.
	 * 
	 * @return ListADT<String> references
	 */
	public ListADT<String> getReferences() {
		return references;
	}
}
