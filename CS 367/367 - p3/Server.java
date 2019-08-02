///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Files:            User.java SimpleStack.java SimpleQueue.java Database.java
//                   Document.java Operation.java WAL.java Listnode.java
//                   QueueADT.java StackADT.java EmptyQueueException.java
//                   EmptyStackException.java 
// Semester:         367 Fall 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Server class simulating a doc editor like GoogleDocs, contains methods used
 * by main class
 * 
 * @author Drew
 */
public class Server {
	File input;
	File output;
	SimpleQueue<Operation> processQueue = new SimpleQueue<Operation>();
	Database database = new Database();

	/**
	 * Constructor for the Server class
	 * 
	 */
	public Server(String inputFileName, String outputFileName) {
		input = new File(inputFileName);
		output = new File(outputFileName);
	}

	/**
	 * This method runs the server by initializing the input file and processing
	 * that data to the output file
	 * 
	 */
	public void run() {
		initialize();
		process();
	}

	/**
	 * Initializes the server based on the information from the input file.
	 * Creates document objects and queues all operations in the input file
	 * 
	 */
	public void initialize() {
		try {
			Scanner init = new Scanner(input);
			String firstLine = init.nextLine();
			int numDocs = Integer.parseInt(firstLine);
			for (int k = 0; k < numDocs; k++) {
				String line = init.nextLine();
				String[] docInfo = line.split(",");
				ArrayList<User> userList = new ArrayList<User>();
				for (int j = 3; j < docInfo.length; j++) {
					userList.add(new User(docInfo[j]));
				}
				String name = docInfo[0];
				int row = Integer.parseInt(docInfo[1]);
				int col = Integer.parseInt(docInfo[2]);
				database.addDocument(new Document(name, row, col, userList));
			}
			while (init.hasNextLine()) {
				String line = init.nextLine();
				String[] opInfo = line.split(",");
				opInfo[3] = opInfo[3].toUpperCase();
				long timestamp;
				String user;
				Operation.OP op = Operation.OP.valueOf(opInfo[3]);
				int row;
				int col;
				switch (opInfo.length) {

				case 4:
					timestamp = Long.parseLong(opInfo[0]);
					user = opInfo[1];
					op = Operation.OP.valueOf(opInfo[3]);

					Operation operation4 = new Operation(opInfo[2], user, op,
							timestamp);
					processQueue.enqueue(operation4);
					break;

				case 6:
					timestamp = Long.parseLong(opInfo[0]);
					user = opInfo[1];
					op = Operation.OP.valueOf(opInfo[3]);
					row = Integer.parseInt(opInfo[4]);
					col = Integer.parseInt(opInfo[5]);

					Operation operation6 = new Operation(opInfo[2], user, op,
							row, col, timestamp);
					processQueue.enqueue(operation6);
					break;

				case 7:
					timestamp = Long.parseLong(opInfo[0]);
					user = opInfo[1];
					op = Operation.OP.valueOf(opInfo[3]);
					row = Integer.parseInt(opInfo[4]);
					col = Integer.parseInt(opInfo[5]);
					int con = Integer.parseInt(opInfo[6]);

					Operation operation7 = new Operation(opInfo[2], user, op,
							row, col, con, timestamp);
					processQueue.enqueue(operation7);
					break;

				default:
					break;

				}
			}
			init.close();
		} catch (FileNotFoundException fnfe) {

		}
	}

	/**
	 * Processes each operation. Once all operations have been queued, begins
	 * extracting one operation from the operation queue one at a time, updating
	 * the database and logging everything to the output file.
	 * 
	 */
	public void process() {

		try {
			PrintWriter out = new PrintWriter(output);
			while (!processQueue.isEmpty()) {
				String write = database.update(processQueue.dequeue());
				System.out.println(write);
				out.write(write);
			}
			out.close();
		} catch (FileNotFoundException fnfe) {

		}
	}

	/**
	 * Main class that passes the arguments to the server constructor and
	 * runs the server.
	 * 
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Server [input.txt] [output.txt]");
			System.exit(0);
		}
		Server server = new Server(args[0], args[1]);
		server.run();
	}
}
