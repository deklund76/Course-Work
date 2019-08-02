///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p5
// Files:            DirectedGraph.java GraphADT.java Item.java 
//					 MessageHandler.java Player.java Room.java TheGame.java
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TheGame {
	private static String gameIntro; // initial introduction to the game
	private static String winningMessage; // winning message of game
	private static String gameInfo; // additional game info
	private static boolean gameWon = false; // state of the game
	private static Scanner scanner = null; // for reading files
	private static Scanner ioscanner = null; // for reading standard input
	private static Player player; // object for player of the game
	private static Room location; // current room in which player is located
	private static Room winningRoom; // Room which player must reach to win
	private static Item winningItem; // Item which player must find
	private static DirectedGraph<Room> layout; // Graph structure of the Rooms

	/**
	 * Implements the main program and is the core engine that is responsible
	 * for reading the input file and processing player commands.
	 * 
	 * @author Drew
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Bad invocation! Correct usage: "
					+ "java AppStore <gameFile>");
			System.exit(1);
		}

		boolean didInitialize = initializeGame(args[0]);

		if (!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}

		System.out.println(gameIntro); // game intro

		processUserCommands();
	}

	private static boolean initializeGame(String gameFile) {

		try {
			// reads player name
			System.out
					.println("Welcome worthy squire! What might be your name?");
			ioscanner = new Scanner(System.in);
			String playerName = ioscanner.nextLine();

			// gameFile (Args[0])
			File game = new File(gameFile);
			scanner = new Scanner(game);

			layout = new DirectedGraph<Room>();

			gameIntro = scanner.nextLine().trim();
			winningMessage = scanner.nextLine().trim();
			gameInfo = scanner.nextLine().trim();
			scanner.nextLine();

			HashSet<Item> sack = new HashSet<Item>();

			String nextTag = scanner.nextLine();
			while (nextTag.contains("#item")) {
				// Get paramaters for each player item
				String name = scanner.nextLine().trim();
				String description = scanner.nextLine().trim();
				String activString = scanner.nextLine().trim();
				String message = scanner.nextLine().trim();
				String oneTimeString = scanner.nextLine().trim();
				String usedString = scanner.nextLine().trim();
				boolean activated;
				boolean oneTimeUse;

				// convert string to boolean and test it's valid
				if (activString.equalsIgnoreCase("true")) {
					activated = true;
				} else if (activString.equalsIgnoreCase("false")) {
					activated = false;
				} else {
					scanner.close();
					return false;
				}

				// convert string to boolean and test it's valid
				if (oneTimeString.equalsIgnoreCase("true")) {
					oneTimeUse = true;
				} else if (oneTimeString.equalsIgnoreCase("false")) {
					oneTimeUse = false;
				} else {
					scanner.close();
					return false;
				}

				Item playerItem = new Item(name, description, activated,
						message, oneTimeUse, usedString);

				sack.add(playerItem);

				if (nextTag.contains("#win")) {
					winningItem = playerItem;
				}

				nextTag = scanner.nextLine();
			}

			player = new Player(playerName, sack);

			while (nextTag.contains("#room")) {
				// Store room tag to check if it's the winning room later
				String winCheck = nextTag;

				// Get parameters for room from file
				String roomName = scanner.nextLine().trim();
				String roomDescription = scanner.nextLine().trim();
				String visible = scanner.nextLine().trim();
				String habitable = scanner.nextLine().trim();
				boolean visibility;
				boolean habitability;
				String habMsg = "";

				// convert string to boolean and test it's valid
				if (visible.equalsIgnoreCase("true")) {
					visibility = true;
				} else if (visible.equalsIgnoreCase("false")) {
					visibility = false;
				} else {
					scanner.close();
					return false;
				}

				// convert string to boolean and test it's valid
				if (habitable.equalsIgnoreCase("true")) {
					habitability = true;
				} else if (habitable.equalsIgnoreCase("false")) {
					habitability = false;
				} else {
					scanner.close();
					return false;
				}

				if (!habitability) {
					habMsg = scanner.nextLine().trim();
				}

				HashSet<Item> items = new HashSet<Item>();

				nextTag = scanner.nextLine();
				while (nextTag.contains("#item")) {
					// Get paramaters for each player item
					String name = scanner.nextLine().trim();
					String description = scanner.nextLine().trim();
					String activString = scanner.nextLine().trim();
					String message = scanner.nextLine().trim();
					String oneTimeString = scanner.nextLine().trim();
					String usedString = scanner.nextLine().trim();
					boolean activated;
					boolean oneTimeUse;

					// convert string to boolean and test it's valid
					if (activString.equalsIgnoreCase("true")) {
						activated = true;
					} else if (activString.equalsIgnoreCase("false")) {
						activated = false;
					} else {
						scanner.close();
						return false;
					}

					// convert string to boolean and test it's valid
					if (oneTimeString.equalsIgnoreCase("true")) {
						oneTimeUse = true;
					} else if (oneTimeString.equalsIgnoreCase("false")) {
						oneTimeUse = false;
					} else {
						scanner.close();
						return false;
					}

					Item roomItem = new Item(name, description, activated,
							message, oneTimeUse, usedString);

					items.add(roomItem);

					if (nextTag.contains("#win")) {
						winningItem = roomItem;
					}

					nextTag = scanner.nextLine();
				}

				// List of message handlers for constructing room
				List<MessageHandler> handlers = new ArrayList<MessageHandler>();

				while (nextTag.contains("#messageHandler")) {
					// Get parameters for message handlers
					String msg = scanner.nextLine().trim();
					String type = scanner.nextLine().trim();
					String roomName2 = null;

					if (type.equals("room")) {
						roomName2 = scanner.nextLine().trim();
					}

					handlers.add(new MessageHandler(msg, type, roomName2));

					nextTag = scanner.nextLine().trim();
				}

				Room room = new Room(roomName, roomDescription, visibility,
						habitability, habMsg, items, handlers);

				layout.addVertex(room);

				if (location == null) {
					location = room;
				}

				if (winCheck.contains("#win")) {
					winningRoom = room;
				}
			}

			nextTag = scanner.next();

			while (!nextTag.contains("#Adjacency")) {
				// Get parameters for adding locked passages
				String roomName = nextTag;
				String room2Name = scanner.nextLine().trim();
				String whyLocked = scanner.nextLine().trim();
				Room room = null;
				Room passage = null;

				// Get room objects from String names
				for (Room rm : layout.getAllVertices()) {
					if (rm.getName().equals(roomName)) {
						room = rm;
					}
					if (rm.getName().equals(room2Name)) {
						passage = rm;
					}
				}

				room.addLockedPassage(passage, whyLocked);

				nextTag = scanner.next();
			}
			scanner.nextLine();

			// Process Adjacency List and add edges to the game layout
			while (scanner.hasNextLine()) {
				String entry = scanner.nextLine().trim();

				String[] adjacency = entry.split(" ");
				Room v1 = null;
				Room v2 = null;

				for (Room rm : layout.getAllVertices()) {
					if (rm.getName().equals(adjacency[0])) {
						v1 = rm;
					}
				}

				for (int k = 1; k < adjacency.length; k++) {
					for (Room rm : layout.getAllVertices()) {
						if (rm.getName().equals(adjacency[k])) {
							v2 = rm;
						}
					}
					layout.addEdge(v1, v2);
				}
			}

			scanner.close();
			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void processUserCommands() {
		String command = null;
		do {

			System.out.print("\nPlease Enter a command ([H]elp):");
			command = ioscanner.next();
			switch (command.toLowerCase()) {
			case "p": // pick up
				processPickUp(ioscanner.nextLine().trim());
				goalStateReached();
				break;
			case "d": // put down item
				processPutDown(ioscanner.nextLine().trim());
				break;
			case "u": // use item
				processUse(ioscanner.nextLine().trim());
				break;
			case "lr":// look around
				processLookAround();
				break;
			case "lt":// look at
				processLookAt(ioscanner.nextLine().trim());
				break;
			case "ls":// look at sack
				System.out.println(player.printSack());
				break;
			case "g":// goto room
				processGoTo(ioscanner.nextLine().trim());
				goalStateReached();
				break;
			case "q":
				System.out.println("You Quit! You, " + player.getName()
						+ ", are a loser!!");
				break;
			case "i":
				System.out.println(gameInfo);
				break;
			case "h":
				System.out
						.println("\nCommands are indicated in [], and may be followed by \n"
								+ "any additional information which may be needed, indicated within <>.\n"
								+ "\t[p]  pick up item: <item name>\n"
								+ "\t[d]  put down item: <item name>\n"
								+ "\t[u]  use item: <item name>\n"
								+ "\t[lr] look around\n"
								+ "\t[lt] look at item: <item name>\n"
								+ "\t[ls] look in your magic sack\n"
								+ "\t[g]  go to: <destination name>\n"
								+ "\t[q]  quit\n" + "\t[i]  game info\n");
				break;
			default:
				System.out.println("Unrecognized Command!");
				break;
			}
		} while (!command.equalsIgnoreCase("q") && !gameWon);
		ioscanner.close();
	}

	private static void processLookAround() {
		System.out.print(location.toString());
		for (Room rm : layout.getNeighbors(location)) {
			System.out.println(rm.getName());
		}
	}

	private static void processLookAt(String item) {
		Item itm = player.findItem(item);
		if (itm == null) {
			itm = location.findItem(item);
		}
		if (itm == null) {
			System.out.println(item + " not found");
		} else
			System.out.println(itm.toString());
	}

	private static void processPickUp(String item) {
		if (player.findItem(item) != null) {
			System.out.println(item + " already in sack");
			return;
		}
		Item newItem = location.findItem(item);
		if (newItem == null) {
			System.out.println("Could not find " + item);
			return;
		}
		player.addItem(newItem);
		location.removeItem(newItem);
		System.out.println("You picked up ");
		System.out.println(newItem.toString());
	}

	private static void processPutDown(String item) {
		if (player.findItem(item) == null) {
			System.out.println(item + " not in sack");
			return;
		}
		Item newItem = player.findItem(item);
		location.addItem(newItem);
		player.removeItem(newItem);
		System.out.println("You put down " + item);
	}

	private static void processUse(String item) {
		Item newItem = player.findItem(item);
		if (newItem == null) {
			System.out.println("Your magic sack doesn't have a " + item);
			return;
		}
		if (newItem.activated()) {
			System.out.println(item + " already in use");
			return;
		}
		if (notifyRoom(newItem)) {
			if (newItem.isOneTimeUse()) {
				player.removeItem(newItem);
			}
		}
	}

	private static void processGoTo(String destination) {
		Room dest = findRoomInNeighbours(destination);
		if (dest == null) {
			for (Room rm : location.getLockedPassages().keySet()) {
				if (rm.getName().equalsIgnoreCase(destination)) {
					System.out.println(location.getLockedPassages().get(rm));
					return;
				}
			}
			System.out.println("Cannot go to " + destination + " from here");
			return;
		}
		Room prevLoc = location;
		location = dest;
		if (!player.getActiveItems().isEmpty())
			System.out.println("The following items are active:");
		for (Item itm : player.getActiveItems()) {
			notifyRoom(itm);
		}
		if (!dest.isHabitable()) {
			System.out.println("Thou shall not pass because");
			System.out.println(dest.getHabitableMsg());
			location = prevLoc;
			return;
		}

		System.out.println();
		processLookAround();
	}

	private static boolean notifyRoom(Item item) {
		Room toUnlock = location.receiveMessage(item.on_use());
		if (toUnlock == null) {
			if (!item.activated())
				System.out.println("The " + item.getName()
						+ " cannot be used here");
			return false;
		} else if (toUnlock == location) {
			System.out.println(item.getName() + ": " + item.on_useString());
			item.activate();
		} else {
			// add edge from location to to Unlock
			layout.addEdge(location, toUnlock);
			if (!item.activated())
				System.out.println(item.on_useString());
			item.activate();
		}
		return true;
	}

	private static Room findRoomInNeighbours(String room) {
		Set<Room> neighbours = layout.getNeighbors(location);
		for (Room rm : neighbours) {
			if (rm.getName().equalsIgnoreCase(room)) {
				return rm;
			}
		}
		return null;
	}

	private static void goalStateReached() {
		if ((location == winningRoom && player.hasItem(winningItem))
				|| (location == winningRoom && winningItem == null)) {
			System.out.println("Congratulations, " + player.getName() + "!");
			System.out.println(winningMessage);
			System.out.println(gameInfo);
			gameWon = true;
		}
	}

}
