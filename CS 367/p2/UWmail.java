///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p2
// Files:            Email.java Conversation.java ListADT.java
//                   Listnode.java DoublyLinkedListIterator.java 
//                   DoublyLinkedList.java MalformedEmailException.java 
//                   and UWmailDB.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Integer;
import java.lang.NumberFormatException;

/**
 * Main class which also contains a few methods to help it read email files from
 * a zip file, and process them into the database and then display the email
 * client as well as run a shell for the user to navigate said client.
 * 
 * <p>Bugs: (There is a major bug in the loadEmails method. I searched for
 * SEVEN HOURS and could not find it's source, but suffice to say, things aren't
 * pretty. Maybe you can find it, when you do I really would like to know what's
 * wrong.)
 *
 * @author Drew Eklund
 */
public class UWmail {
	
	//Database to be used by UWmail
	private static UWmailDB uwmailDB = new UWmailDB();
	
	//Scanner used for user input
	private static final Scanner stdin = new Scanner(System.in);

	/**
	 * Main method where arguments are processed and main program execution
	 * occurs
	 *
	 */
	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("Usage: java UWmail [EMAIL_ZIP_FILE]");
			System.exit(0);
		}

		loadEmails(args[0]);

		displayInbox();
	}

	/**
	 * Method which loads email files from a zip file, parses them and puts them
	 * in the database.
	 *
	 */
	private static void loadEmails(String fileName) {
		try (ZipFile zf = new ZipFile(fileName);) {
			Enumeration<? extends ZipEntry> entries = zf.entries();
			ListADT<Email> tmp = new DoublyLinkedList<Email>();
			while (entries.hasMoreElements()) {
				ZipEntry ze = entries.nextElement();
				if (ze.getName().endsWith(".txt")) {
					try {
						InputStream in = zf.getInputStream(ze);
						Scanner sc = new Scanner(in);
						String line;
						Date date = null;
						String messageID = null;
						String subject = null;
						String from = null;
						String to = null;
						ListADT<String> body = new DoublyLinkedList<String>();
						String inReplyTo = null;
						ListADT<String> references = new DoublyLinkedList<String>();
						boolean replyConstructor = false;
						for (int k = 0; k < 5; k++) {
							line = sc.nextLine();
							
							//Each line is broken into it's two main components
							//it's key and its value and placed in an array
							String[] key0Value1 = line.split(": ");

							if (k == 2) {
								subject = line.substring(line.indexOf(':') + 1,
										line.length());
							} else if (key0Value1.length != 2) {
								throw new MalformedEmailException();
							}
							if (key0Value1[0].equals("In-Reply-To")) {
								k = k - 2;
								replyConstructor = true;
							}
							//Switch represents the different values
							//to be parsed given the fixed order of these values
							switch (k) {
							case -2:
								inReplyTo = key0Value1[1];
								break;
							case -1:
								String[] ref = key0Value1[1].split(",");
								for (int j = 0; j < ref.length; j++) {
									references.add(ref[j]);
								}
								break;
							case 0:
								DateFormat format = new SimpleDateFormat(
										"EEE, dd MMM yyyy HH:mm:ss Z",
										Locale.ENGLISH);
								String day = key0Value1[1];
								try {
									date = format.parse(day);
								} catch (ParseException e) {
									throw new MalformedEmailException();
								}
								break;
							case 1:
								messageID = key0Value1[1];
								break;
							case 2:
								break;
							case 3:
								from = key0Value1[1];
								break;
							case 4:
								to = key0Value1[1];
								break;
							}
						}
						if (!sc.hasNextLine()) {
							sc.close();
							throw new MalformedEmailException();
						}
						while (sc.hasNextLine()) {
							line = sc.nextLine();
							body.add(line);
						}
						if (replyConstructor) {
							tmp.add(0, new Email(date, messageID, subject,
									from, to, body, inReplyTo, references));
						} else {
							tmp.add(0, new Email(date, messageID, subject,
									from, to, body));
						}
						sc.close();
					} catch (MalformedEmailException e) {
					}
				}
			}
			Iterator<Email> trans = tmp.iterator();
			while (trans.hasNext()) {
				Email addthis = trans.next();
				uwmailDB.addEmail(addthis);
			}
		} catch (ZipException e) {
			System.out
					.println("A .zip format error has occurred for the file.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("File <FileNameHere.zip> not found.");
			System.exit(1);
		} catch (SecurityException e) {
			System.out.println("Unable to obtain read access for the file.");
			System.exit(1);
		}
	}

	/**
	 * Method which displays the inbox and also contains a series of prompts for
	 * navigating UWmail.
	 *
	 */
	private static void displayInbox() {
		boolean done = false;
		System.out.println("Inbox:");
		System.out.println("-----------------------------------"
				+ "---------------------------------------------");
		if (uwmailDB.getInbox().isEmpty()) {
			System.out.println("No conversations to show.");
		} else {
			/*
			 * for (int k = 0; k < uwmailDB.size() + 1; k++) {
			 * System.out.println(uwmailDB.getInbox().get(k)); }
			 * Iterator<Conversation> trans = uwmailDB.getInbox().iterator();
			 * System.out.println("iterator"); while(trans.hasNext()) {
			 * System.out.println(trans.next()); }
			 */

			for (int k = 0; k < uwmailDB.size() + 1; k++) {
				Conversation currentConvo = uwmailDB.getInbox().get(k);
				String subject = currentConvo.get(0).getSubject();
				String date = currentConvo.get(currentConvo.size() - 1)
						.getDate();
				System.out
						.println("[" + k + "] " + subject + " (" + date + ")");
			}
		}
		while (!done) {
			System.out.print("Enter option ([#]Open conversation, [T]rash, "
					+ "[Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) {

				int val = 0;
				boolean isNum = true;

				try {
					val = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					isNum = false;
				}

				if (isNum) {
					if (val < 0) {
						System.out.println("The value can't be negative!");
						continue;
					} else if (val >= uwmailDB.size() + 1) {
						System.out.println("Not a valid number!");
						continue;
					} else {
						displayConversation(val);
						continue;
					}

				}

				if (input.length() > 1) {
					System.out.println("Invalid command!");
					continue;
				}

				switch (input.charAt(0)) {
				case 'T':
				case 't':
					displayTrash();
					break;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				default:
					System.out.println("Invalid command!");
					break;
				}
			}
		}
		System.exit(0);
	}

	/**
	 * Method which displays the trash and also contains a series of prompts for
	 * navigating UWmail.
	 *
	 */
	private static void displayTrash() {
		boolean done = false;
		System.out.println("Trash:");
		System.out.println("-----------------------------------"
				+ "---------------------------------------------");
		if (uwmailDB.getTrash().isEmpty()) {
			System.out.println("No conversations to show.");
		} else {
			for (int k = 0; k < uwmailDB.getTrash().size(); k++) {
				Conversation currentConvo = uwmailDB.getTrash().get(k);
				String subject = currentConvo.get(0).getSubject();
				String date = currentConvo.get(currentConvo.size() - 1)
						.getDate();
				System.out
						.println("[" + k + "] " + subject + " (" + date + ")");
			}
		}
		while (!done) {
			System.out.print("Enter option ([I]nbox, [Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) {
				if (input.length() > 1) {
					System.out.println("Invalid command!");
					continue;
				}

				switch (input.charAt(0)) {
				case 'I':
				case 'i':
					displayInbox();
					break;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				default:
					System.out.println("Invalid command!");
					break;
				}
			}
		}
		System.exit(0);
	}

	/**
	 * Method which displays the current conversation and also contains a series
	 * of prompts for navigating UWmail.
	 *
	 */
	private static void displayConversation(int val) {
		if (val < 0 || val > uwmailDB.size()) {
			displayInbox();
		}
		boolean done = false;
		Conversation conversation = uwmailDB.getInbox().get(val);
		System.out.println("SUBJECT: " + conversation.get(0).getSubject());
		System.out.println("---------------------------------------"
				+ "-----------------------------------------");
		for (int k = 0; k < conversation.size(); k++) {
			Email emailK = conversation.get(k);
			if (k == conversation.getCurrent()) {
				System.out.println("From: " + emailK.getFrom());
				System.out.println("To: " + emailK.getTo());
				System.out.println(emailK.getDate());
				System.out.println();
				for (int j = 0; j < emailK.getBody().size(); j++) {
					System.out.println(emailK.getBody().get(j));
				}
			} else {
				System.out.println(emailK.getFrom() + " | "
						+ emailK.getBody().get(0) + " | " + emailK.getDate());
			}
			System.out.println("---------------------------------------"
					+ "-----------------------------------------");
		}

		while (!done) {
			System.out
					.print("Enter option ([N]ext email, [P]revious email, "
							+ "[J]Next conversation, [K]Previous\nconversation, [I]nbox, "
							+ "[#]Move to trash, [Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) {

				if (input.length() > 1) {
					System.out.println("Invalid command!");
					continue;
				}

				switch (input.charAt(0)) {
				case 'P':
				case 'p':
					if (conversation.getCurrent() > 0) {
						conversation.moveCurrentBack();
					}
					displayConversation(val);
					break;
				case 'N':
				case 'n':
					if (conversation.getCurrent() < conversation.size() - 1) {
						conversation.moveCurrentForward();
					}
					displayConversation(val);
					break;
				case 'J':
				case 'j':
					if (val != uwmailDB.getInbox().size() - 1) {
						val++;
					}
					displayConversation(val);
					break;

				case 'K':
				case 'k':
					if (val != 0) {
						val--;
					}
					displayConversation(val);
					break;

				case 'I':
				case 'i':
					displayInbox();
					return;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				case '#':
					uwmailDB.deleteConversation(val);
					displayInbox();
					return;

				default:
					System.out.println("Invalid command!");
					break;
				}
			}
		}
		System.exit(0);
	}
}
