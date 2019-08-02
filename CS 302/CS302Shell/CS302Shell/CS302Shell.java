///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            CS302Shell
// Files:            CS302Shell
// Semester:         Spring 2015
//
// Author:           Drew Eklund, ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  Legault
// Lab Section:      335
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Pair Partner:     N/A
// Email:            N/A
// CS Login:         N/A
// Lecturer's Name:  N/A
// Lab Section:      N/A
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//
// Persons: N/A         
//                   
//
// Online sources: N/A  
//                    
//                   
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Scanner;

/**
 * Simple command-line user interface
 *
 * <p>
 * Bugs: (double arithmetic sometimes results in unusual floating point values
 * due to rounding error)
 *
 * @author Drew Eklund
 */
public class CS302Shell {

	/**
	 * -Executes commands given correct input
	 * -Returns reasoning if a command is invalid
	 * List of commands as described by "help"
	 * add i j   - return the sum of two integers i+j
	 * sub i j   - return the difference between two integers  i-j
	 * mul i j   - return the product of two integers     i*j
	 * div i j   - return the division of  i/j (floating point result)
	 * quo i j   - return the quotient of  i/j (integer division)
	 * rem i j   - return the remainder of i/j (modulus)
	 * decimal a - return decimal value of (a), a is printable character
	 * help      - display this command listing
	 * exit      - exit application
	 *
	 * @param command -specifies which command to use
	 * @param input -characters or numbers to be used for preceding command
	 * @return result -returns output of command function given its input
	 */
	public static void main(String[] args) {

		// Scanner connected to keyboard for reading user input
		Scanner scnr = new Scanner(System.in);

		// DISPLAY WELCOME MESSAGE AND HELP
		System.out.println("Welcome to CS302Shell! "
				+ "Enter help for a list of commands or exit to end.");

		// Non-Boolean variables declared
		String userInput; // the command beginning the line of user input
						  // if command is valid, this variable becomes
						  // the user input for the rest of the line
		String first; // the first variable inputed in arithmetic commands
		String second; // the second variable inputed in arithmetic commands
		char a; // character to be tested for input validation
				// and the decimal command
		double i; // first variable for arithmetic commands
				  // if "String first" is a number
		double j; // second variable for arithmetic commands
		  		  // if "String second" is a number
		int k; // variable for the index of the character being tested
			   // in both input validation and the decimal command
		int ascii; // ascii value of a character in the decimal command
		int inputLength; // length of user input used for input validation
		int firstLength; // length of "String first" used for input validation
		int secondLength; // length of "String first" used for input validation
		int intValueI; // integer value of i used for quo and rem commands
		int intValueJ; // integer value of j used for quo and rem commands

		// MAIN COMMAND-LINE LOOP
		// do-while loop ensures command prompt is printed and part of loop
		do {
			// DISPLAY COMMAND PROMPT
			System.out.print("> ");
			// READ (SCAN) user input
			userInput = scnr.next();
			userInput = userInput.trim();
			// Boolean variables initialized below to reduce clutter later
				// Command Booleans
			boolean decimal = userInput.equals("decimal"); // evaluates to 
														   //true if user types 
														   //"decimal" first
			boolean help = userInput.equals("help"); // evaluates to true
													 // if user types 
													 // "help" first
			boolean exit = userInput.equals("exit"); // evaluates to true
			 										 // if user types 
													 // "exit" first
			boolean add = userInput.equals("add"); // evaluates to true if
												   // user types "add" first
			boolean sub = userInput.equals("sub"); // evaluates to true if
			   									   // user types "sub" first
			boolean mul = userInput.equals("mul"); // evaluates to true if
			   									   // user types "mul" first
			boolean div = userInput.equals("div"); // evaluates to true if
			   									   // user types "div" first
			boolean quo = userInput.equals("quo"); // evaluates to true if
			   									   // user types "quo" first
			boolean rem = userInput.equals("rem"); // evaluates to true if
			   									   // user types "rem" first
				// Input Validation Booleans
			boolean charIsDigit = Character.isDigit('1'); // evaluates to true
														  // if character
														  // being tested
														  // is a number
				// Declared but initialized after command is read
			boolean indexFound; // used to find the index of the space
								// between the two user inputs
								// in arithmetic commands
			boolean checkSecond; // used to decide whether to check the
								 // the second of the two user inputs
								 // in arithmetic commands
			// DECIMAL command
			if (decimal == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
											  // command are irrelevant
				k = 0;
				inputLength = userInput.length();
				// Input Validation
				if (inputLength == 0) {
					k++;
					System.out.print("Must include "
							+ "a character after decimal");
				}
				// Finds and prints ascii values of each character in input
				while (k < inputLength) {
					a = userInput.charAt(k);
					ascii = a;
					System.out.print(ascii + " ");
					k++;
				}
				// advances console a line so command prompt appears correctly
				System.out.println("");
			}
			// ADD command
			if (add == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
				  							  // command are irrelevant
				inputLength = userInput.length();
				// Input Validation
				// Checks for input after command
				if (inputLength == 0) {
					System.out.println("Must include a character after add");
				}
				k = 0;
				indexFound = false;
				// finds index of space separating two input variables
				while (k < inputLength && indexFound == false) {
					a = userInput.charAt(k);
					if (a == ' ') {
						indexFound = true;
					}
					k++;
				}
				// assigns substrings of user input for two variables
				if (k != 0) {
					first = userInput.substring(0, k - 1);
					second = userInput.substring(k, inputLength);
					k = 0;
					charIsDigit = true;
					firstLength = first.length();
					checkSecond = true;
					// checks first input variable for non-digit characters
					while (k < firstLength && charIsDigit == true) {
						a = first.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					if (charIsDigit == false) {
						System.out.println(first + " must be a number");
						checkSecond = false;
					}
					k = 0;
					secondLength = second.length();
					// checks second input variable for non-digit characters
					while (k < secondLength && charIsDigit == true) {
						a = second.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					// checks for second input variable
					if (second.length() == 0) {
						charIsDigit = false;

					}
					// prints response to invalid input
					if (charIsDigit == false && checkSecond == true) {
						if (second.length() == 0) {
							System.out.println("input must be two numbers");
						} else {
							System.out.println(second + " must be a number");
						}
					}
					// converts input to doubles
					if (charIsDigit == true) {
						i = Double.parseDouble(first);
						j = Double.parseDouble(second);
						// calculates and displays result
						System.out.println(i + j);
					}
				}

			}
			// SUB command
			if (sub == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
				  							  // command are irrelevant
				inputLength = userInput.length();
				// Input Validation
				// Checks for input after command
				if (inputLength == 0) {
					System.out.println("Must include a character after add");
				}
				k = 0;
				indexFound = false;
				// finds index of space separating two input variables
				while (k < inputLength && indexFound == false) {
					a = userInput.charAt(k);
					if (a == ' ') {
						indexFound = true;
					}
					k++;
				}
				// assigns substrings of user input for two variables
				if (k != 0) {
					first = userInput.substring(0, k - 1);
					second = userInput.substring(k, inputLength);
					k = 0;
					charIsDigit = true;
					firstLength = first.length();
					checkSecond = true;
					// checks first input variable for non-digit characters
					while (k < firstLength && charIsDigit == true) {
						a = first.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					if (charIsDigit == false) {
						System.out.println(first + " must be a number");
						checkSecond = false;
					}
					k = 0;
					secondLength = second.length();
					// checks second input variable for non-digit characters
					while (k < secondLength && charIsDigit == true) {
						a = second.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					// checks for second input variable
					if (second.length() == 0) {
						charIsDigit = false;

					}
					// prints response to invalid input
					if (charIsDigit == false && checkSecond == true) {
						if (second.length() == 0) {
							System.out.println("input must be two numbers");
						} else {
							System.out.println(second + " must be a number");
						}
					}
					// converts input to doubles
					if (charIsDigit == true) {
						i = Double.parseDouble(first);
						j = Double.parseDouble(second);
						// calculates and displays result
						System.out.println(i - j);
					}
				}

			}
			// S
			// MUL command
			if (mul == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
				                              // command are irrelevant
				inputLength = userInput.length();
				// Input Validation
				// Checks for input after command
				if (inputLength == 0) {
					System.out.println("Must include a character after add");
				}
				k = 0;
				indexFound = false;
				// finds index of space separating two input variables
				while (k < inputLength && indexFound == false) {
					a = userInput.charAt(k);
					if (a == ' ') {
						indexFound = true;
					}
					k++;
				}
				// assigns substrings of user input for two variables
				if (k != 0) {
					first = userInput.substring(0, k - 1);
					second = userInput.substring(k, inputLength);
					k = 0;
					charIsDigit = true;
					firstLength = first.length();
					checkSecond = true;
					// checks first input variable for non-digit characters
					while (k < firstLength && charIsDigit == true) {
						a = first.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					if (charIsDigit == false) {
						System.out.println(first + " must be a number");
						checkSecond = false;
					}
					k = 0;
					secondLength = second.length();
					// checks second input variable for non-digit characters
					while (k < secondLength && charIsDigit == true) {
						a = second.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					// checks for second input variable
					if (second.length() == 0) {
						charIsDigit = false;

					}
					// prints response to invalid input
					if (charIsDigit == false && checkSecond == true) {
						if (second.length() == 0) {
							System.out.println("input must be two numbers");
						} else {
							System.out.println(second + " must be a number");
						}
					}
					// converts input to doubles
					if (charIsDigit == true) {
						i = Double.parseDouble(first);
						j = Double.parseDouble(second);
						// calculates and displays result
						System.out.println(i * j);
					}
				}

			}
			// DIV command
			if (div == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
				  							  // command are irrelevant
				inputLength = userInput.length();
				// Input Validation
				// Checks for input after command
				if (inputLength == 0) {
					System.out.println("Must include a character after add");
				}
				k = 0;
				indexFound = false;
				// finds index of space separating two input variables
				while (k < inputLength && indexFound == false) {
					a = userInput.charAt(k);
					if (a == ' ') {
						indexFound = true;
					}
					k++;
				}
				// assigns substrings of user input for two variables
				if (k != 0) {
					first = userInput.substring(0, k - 1);
					second = userInput.substring(k, inputLength);
					k = 0;
					charIsDigit = true;
					firstLength = first.length();
					checkSecond = true;
					// checks first input variable for non-digit characters
					while (k < firstLength && charIsDigit == true) {
						a = first.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					if (charIsDigit == false) {
						System.out.println(first + " must be a number");
						checkSecond = false;
					}
					k = 0;
					secondLength = second.length();
					// checks second input variable for non-digit characters
					while (k < secondLength && charIsDigit == true) {
						a = second.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					// checks for second input variable
					if (second.length() == 0) {
						charIsDigit = false;

					}
					// prints response to invalid input
					if (charIsDigit == false && checkSecond == true) {
						if (second.length() == 0) {
							System.out.println("input must be two numbers");
						} else {
							System.out.println(second + " must be a number");
						}
					}
					// converts input to doubles
					if (charIsDigit == true) {
						i = Double.parseDouble(first);
						j = Double.parseDouble(second);
						// prevents division by zero
						if (j == 0) {
							System.out.println("cannot divide by zero");
						} 
						// calculates and displays result
						else {
							System.out.println(i / j);
						}
					}
				}

			}
			// QUO command
			if (quo == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
											  // command are irrelevant
				inputLength = userInput.length();
				// Input Validation
				// Checks for input after command
				if (inputLength == 0) {
					System.out.println("Must include a character after add");
				}
				k = 0;
				indexFound = false;
				// finds index of space separating two input variables
				while (k < inputLength && indexFound == false) {
					a = userInput.charAt(k);
					if (a == ' ') {
						indexFound = true;
					}
					k++;
				}
				// assigns substrings of user input for two variables
				if (k != 0) {
					first = userInput.substring(0, k - 1);
					second = userInput.substring(k, inputLength);
					k = 0;
					charIsDigit = true;
					firstLength = first.length();
					checkSecond = true;
					// checks first input variable for non-digit characters
					while (k < firstLength && charIsDigit == true) {
						a = first.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					if (charIsDigit == false) {
						System.out.println(first + " must be a number");
						checkSecond = false;
					}
					k = 0;
					secondLength = second.length();
					// checks second input variable for non-digit characters
					while (k < secondLength && charIsDigit == true) {
						a = second.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					// checks for second input variable
					if (second.length() == 0) {
						charIsDigit = false;

					}
					// prints response to invalid input
					if (charIsDigit == false && checkSecond == true) {
						if (second.length() == 0) {
							System.out.println("input must be two numbers");
						} else {
							System.out.println(second + " must be a number");
						}
					}
					// converts input to doubles
					if (charIsDigit == true) {
						i = Double.parseDouble(first);
						j = Double.parseDouble(second);
						intValueI = (int) i;
						intValueJ = (int) j;
						// prevents division by zero
						if (intValueJ == 0) {
							System.out.println("cannot divide by zero");
						} 
						// calculates and displays result
						else {
							System.out.println(intValueI / intValueJ);
						}
					}
				}

			}
			// REM command
			if (rem == true) {
				userInput = scnr.nextLine();
				userInput = userInput.trim(); // so number of spaces before
				  							  // command are irrelevant
				inputLength = userInput.length();
				// Input Validation
				// Checks for input after command
				if (inputLength == 0) {
					System.out.println("Must include a character after add");
				}
				k = 0;
				indexFound = false;
				// finds index of space separating two input variables
				while (k < inputLength && indexFound == false) {
					a = userInput.charAt(k);
					if (a == ' ') {
						indexFound = true;
					}
					k++;
				}
				// assigns substrings of user input for two variables
				if (k != 0) {
					first = userInput.substring(0, k - 1);
					second = userInput.substring(k, inputLength);
					k = 0;
					charIsDigit = true;
					firstLength = first.length();
					checkSecond = true;
					// checks first input variable for non-digit characters
					while (k < firstLength && charIsDigit == true) {
						a = first.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					if (charIsDigit == false) {
						System.out.println(first + " must be a number");
						checkSecond = false;
					}
					k = 0;
					secondLength = second.length();
					// checks second input variable for non-digit characters
					while (k < secondLength && charIsDigit == true) {
						a = second.charAt(k);
						charIsDigit = Character.isDigit(a);
						if (a == '.' && k != 0) {
							charIsDigit = true;
						}
						if (a == '-' && k == 0) {
							charIsDigit = true;
						}
						k++;
					}
					// checks for second input variable
					if (second.length() == 0) {
						charIsDigit = false;

					}
					// prints response to invalid input
					if (charIsDigit == false && checkSecond == true) {
						if (second.length() == 0) {
							System.out.println("input must be two numbers");
						} else {
							System.out.println(second + " must be a number");
						}
					}
					// converts input to doubles
					if (charIsDigit == true) {
						i = Double.parseDouble(first);
						j = Double.parseDouble(second);
						// converts doubles to integers
						intValueI = (int) i;
						intValueJ = (int) j;
						// prevents division by zero
						if (intValueJ == 0) {
							System.out.println("cannot divide by zero");
						} 
						// calculates and displays result
						else {
							System.out.println(intValueI % intValueJ);
						}
					}
				}

			}
			// HELP command
			// displays command list
			if (help == true) {
				System.out.println("add i j   - return the "
						+ "sum of two integers i+j");
				System.out.println("sub i j   - return the "
						+ "difference between two integers  i-j");
				System.out.println("mul i j   - return the "
						+ "product of two integers     i*j");
				System.out.println("div i j   - return the "
						+ "division of  i/j (floating point result)");
				System.out.println("quo i j   - return the "
						+ "quotient of  i/j (integer division)");
				System.out.println("rem i j   - return the "
						+ "remainder of i/j (modulus)");
				System.out.println("decimal a - return decimal value of "
						+ "(a), a is printable character");
				System.out.println("help      "
						+ "- display this command listing");
				System.out.println("exit      " + "- exit application");
				scnr.nextLine();
			}
			// UNRECOGNIZED command
			//responds to invalid command input
			if (help ^ exit ^ decimal ^ add ^ sub ^ mul ^ div ^ quo
					^ rem != true) {
				System.out.println(userInput + ": Command not found");
				scnr.nextLine();
			}
		} while (!userInput.equals("exit"));

		// Displays "Good-Bye"
		System.out.println("Good-Bye");
		// Close the scanner to avoid resource leak
		scnr.close();

	} // end of main method

} // end of CS302Shell class