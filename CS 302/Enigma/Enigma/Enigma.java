///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Enigma
// Files:            Enigma.java
// Semester:         CS302 Spring 2015
//
// Author:           Drew Eklund
// Email:            ameklund@wisc.edu
// CS Login:         deklund
// Lecturer's Name:  LeGault
// Lab Section:      335
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner: N/A    
// Email:            N/A
// CS Login:         N/A
// Lecturer's Name:  N/A
// Lab Section:      N/A
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                                
//
// Persons:   N/A      
//                  
//
// Online sources:  N/A
//                    
//                   
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;

/**
 * Simulate the encryption of messages that was performed by the World War
 * II-era German Enigma cipher machine.
 * 
 * <ul>
 * <li><a href="http://en.wikipedia.org/wiki/Enigma_machine" target="wiki">
 * http://en.wikipedia.org/wiki/Enigma_machine</a></li>
 * <li><a href="https://www.youtube.com/watch?v=G2_Q9FoD-oQ" target="youtube">
 * https://www.youtube.com/watch?v=G2_Q9FoD-oQ</a></li>
 * </ul>
 */
public class Enigma {

	/**
	 * User enter some initial configuration information and is then prompted
	 * for the lines of text to be encrypted.
	 * 
	 * Each line is encrypted according to the rotor configuration. The encoded
	 * line is displayed to the user.
	 *
	 * @param args
	 *            UNUSED
	 */
	public static void main(String[] args) {

		Scanner scnr = new Scanner(System.in);

		// Prints welcome message and prompts rotor configuration
		System.out.println("Willkommen auf der Enigma-Maschine");
		System.out.println();
		System.out.println("Please enter a Rotor Configuration.");
		System.out.println("This must be a list of numbers in the "
				+ "range from 0 to " + (RotorConstants.ROTORS.length - 1)
				+ " , separated by spaces.");
		System.out.println("Note that rotor 0 is the identity rotor.");
		String rotorIndicesLine = scnr.nextLine();

		// Data Structures
		// Array listing which rotors to be used in order of input
		int[] rotorIndices = parseRotorIndices(rotorIndicesLine);
		// Array representing rotorNotches, first dimension is the rotor number
		// second dimension is its notch locations
		int[][] rotorNotches = getRotorNotches(rotorIndices);
		// Array representing rotor offsets with initial values of 0, these
		// values are changed by advancing the rotors
		int[] rotorOffsets = new int[rotorIndices.length];
		// Array representing the current configuration of rotors
		// with the first dimension being the rotor number and
		// the second dimension the rotor's respective array of values
		int[][] rotorsInUse = setUpRotors(rotorIndices);
		// reflector rotor used in the encode method. It's unique in the
		// fact that every pair of letters map to eachother
		int[] reflectorRotor = convertRotor(RotorConstants.REFLECTOR);

		// After a rotor configuration is entered, user is
		// prompted for lines of text to be encoded
		System.out.println();
		System.out.println("Enter lines of text to be encoded: ");
		// Main command loop, only exits upon errors or manually through eclipse
		// Encodes user input as cipher text mimicking the enigma machine
		while (1 > 0) {
			System.out.println();
			String plainText = scnr.nextLine();
			System.out.print("Encoded result: ");
			for (int k = 0; k < plainText.length(); k++) {
				char cipherText = encode(rotorsInUse, reflectorRotor,
						plainText.charAt(k));
				if (Character.isLetter(cipherText) == true) {
					advance(rotorsInUse, rotorOffsets, rotorNotches);
				}
				System.out.print(cipherText);
			}
		}
	}

	/**
	 * Rotates (advances) a single rotor by one position. This is done by
	 * removing the first value of the array, shifting all the other values
	 * towards the beginning of the array, and placing the first value back into
	 * the array as the last value.
	 *
	 * @param rotor
	 *            The rotor that must be rotated (or shifted).
	 */
	public static void rotate(int[] rotor) {

		int temp = rotor[0];
		for (int k = 0; k < rotor.length - 1; k++) {
			rotor[k] = rotor[k + 1];
		}
		rotor[rotor.length - 1] = temp;
	}

	/**
	 * Parses (interprets) the rotor configuration string of integer values and
	 * returns an integer array of those values.
	 * 
	 * Example: If the input string is:
	 * 
	 * <pre>
	 * &quot;3 7 2&quot;
	 * </pre>
	 * 
	 * The method returns an int array containing three indices: { 3, 7, 2 }.
	 *
	 * <h6>Parameter Validation</h6> <blockquote>
	 * <p>
	 * If any of the specified indices is not a valid index into the
	 * {@code RotorConstants.ROTORS} array, the method prints:
	 * 
	 * <pre>
	 * Invalid rotor. You must enter an integer between 0 and 8.
	 * </pre>
	 * 
	 * to {@code System.out} and then quits the program by calling
	 * {@code System.exit(-1)}.
	 *
	 * <p>
	 * The same rotor may not be used twice. If the user tries to specify the
	 * same rotor multiple more than once, this method prints:
	 * 
	 * <pre>
	 * You cannot use the same rotor twice.
	 * </pre>
	 * 
	 * to {@code System.out} and then quits the program by calling
	 * {@code System.exit(-1)}. </blockquote>
	 * 
	 * @param rotorIndicesLine
	 *            Text containing rotor index values separated by spaces.
	 * @return Array of rotor index values.
	 */
	public static int[] parseRotorIndices(String rotorIndicesLine) {

		// Gets rid of any potential extra whitespace characters and ensures
		// numIndices is correctly initialized below
		rotorIndicesLine = rotorIndicesLine.trim();

		// Checks to make sure user enters rotors and exits if no rotors are
		// entered
		if (rotorIndicesLine.isEmpty()) {
			System.out.println("You must specify at least one rotor.");
			System.exit(-1);
		}

		// Integer variable representing number of Rotors entered
		int numIndices = 1;
		for (int k = 0; k < rotorIndicesLine.length(); k++) {
			if (rotorIndicesLine.charAt(k) == ' ') {
				numIndices++;
			}
		}

		// Array listing which rotors to be used in order of input
		int[] rotorIndices = new int[numIndices];

		// Integer variable used as start index for parsing user input
		// as substrings. Initialized as 0 outside the loop to avoid problems
		int start = 0;

		// Parses user input and enters values into the rotorIndices array
		for (int index = 0; index < numIndices; index++) {
			if (numIndices > 1) {
				int end = rotorIndicesLine.indexOf(' ', start);
				if (end > 0 && end <= rotorIndicesLine.length()) {
					int number = Integer.parseInt(rotorIndicesLine.substring(
							start, end));
					rotorIndices[index] = number;
					start = end + 1;
				} else {
					int number = Integer.parseInt(rotorIndicesLine.substring(
							start, rotorIndicesLine.length()));
					rotorIndices[index] = number;
				}
			} else {
				int number = Integer.parseInt(rotorIndicesLine.substring(0,
						rotorIndicesLine.length()));
				rotorIndices[index] = number;
			}
		}

		// Checks that all rotors entered are valid, exits if they are not
		for (int k = 0; k < numIndices; k++) {
			if (rotorIndices[k] < 0
					|| rotorIndices[k] > RotorConstants.ROTORS.length - 1) {
				System.out.println("Invalid rotor. You must enter an integer "
						+ "between 0 and " + (RotorConstants.ROTORS.length - 1)
						+ ".");
				System.exit(-1);
			}
		}

		// Checks for duplicate rotors, exits if found
		for (int k = 0; k < numIndices; k++) {
			for (int i = k + 1; i < numIndices; i++) {
				if (rotorIndices[k] == rotorIndices[i]) {
					System.out.println("You cannot use the same rotor twice.");
					System.exit(-1);
				}
			}
		}
		return rotorIndices;
	}

	/**
	 * Creates and returns array of rotor ciphers, based on rotor indices.
	 * 
	 * The array of rotor ciphers returned is a 2D array, where each "row" of
	 * the array represents a given rotor's rotor cipher in integral form.
	 * 
	 * The number of rows is equal to the number of rotors in use, as specified
	 * by the length of rotorIndices parameter.
	 *
	 * @param rotorIndices
	 *            The indices of the rotors to use. Each value in this array
	 *            should be a valid index into the {@code RotorConstants.ROTORS}
	 *            array.
	 * 
	 * @return The array of rotors, each of which is itself an array of ints
	 *         copied from {@code RotorConstants.ROTORS}.
	 */
	public static int[][] setUpRotors(int[] rotorIndices) {

		// Declares the array representing the rotor configuration
		// with necessary dimensions
		int[][] rotorsInUse = new int[rotorIndices.length][RotorConstants.ROTOR_LENGTH];

		// Assigns necessary values to rotorsInUse array
		for (int k = 0; k < rotorIndices.length; k++) {
			rotorsInUse[k] = convertRotor(RotorConstants.ROTORS[rotorIndices[k]]);
		}
		return rotorsInUse;
	}

	/**
	 * Creates and returns a 2D array containing the notch positions for each
	 * rotor being used.
	 * 
	 * <p>
	 * Each "row" of the array represents the notch positions of a single rotor.
	 * A rotor may have more than one notch position, so each "row" will contain
	 * one or more integers. There will be multiple rows, if multiple rotors are
	 * in use.
	 * </p>
	 * 
	 * <p>
	 * Note that this array may be jagged, since different rotors have different
	 * numbers of notch positions.
	 * </p>
	 *
	 * <p>
	 * 
	 * <pre>
	 *     Example:
	 *     Input: [2, 1, 3]
	 *     Output: a 2D Array that would look something like this:
	 *            [[Array of notch positions of Rotor II],
	 *             [Array of notch positions of Rotor I] ,
	 *             [Array of notch positions of Rotor III]]
	 * </pre>
	 *
	 * @param rotorIndices
	 *            Indices of the rotors. Each value in this array should be a
	 *            valid index into the {@code RotorConstants.ROTORS} array.
	 * 
	 * @return An array containing the notch positions for each selected rotor.
	 */
	public static int[][] getRotorNotches(int[] rotorIndices) {

		// Array representing rotorNotches, first dimension is the rotor number
		// second dimension is its notch locations
		int[][] rotorNotches = new int[rotorIndices.length][0];

		// Assigns necessary values to the rotorNotches array
		for (int k = 0; k < rotorIndices.length; k++) {
			rotorNotches[k] = RotorConstants.NOTCHES[rotorIndices[k]];
		}
		return rotorNotches;
	}

	/**
	 * Converts a rotor cipher from its textual representation into an integer-
	 * based rotor cipher. Each int would be in the range [0, 25], representing
	 * the alphabetical index of the corresponding character.
	 *
	 * <p>
	 * The mapping of letters to integers works as follows: <br />
	 * Each letter should be converted into its alphabetical index. That is, 'A'
	 * maps to 0, 'B' maps to 1, etc. until 'Z', which maps to 25.
	 *
	 * <p>
	 * 
	 * <pre>
	 * Example:
	 * Input String: EKMFLGDQVZNTOWYHXUSPAIBRCJ
	 * Output Array: [4 10 12 5 11 6 3 16 21 25 13 19 14 22 24 7 23 20 18 15 0
	 *                8 1 17 2 9]
	 * </pre>
	 *
	 * @param rotorText
	 *            Text representation of the rotor. This will be like the
	 *            Strings in {@code RotorConstants.ROTORS}; that is, it will be
	 *            a String containing all 26 upper-case letters.
	 * 
	 * @return array of values between 0 and 25, inclusive that represents the
	 *         integer index value of each character.
	 */
	public static int[] convertRotor(String rotorText) {

		// Array of numbers representing the letters listed in the
		// cipher text for the rotor being converted
		int[] rotorArray = new int[rotorText.length()];

		// Initializes rotorArray based off its textual representation
		for (int k = 0; k < rotorText.length(); k++) {
			rotorArray[k] = (int) rotorText.charAt(k) - 65;
		}
		return rotorArray;
	}

	/**
	 * Encodes a single uppercase character according to the current state of
	 * the Enigma encoding rotors.
	 *
	 * <p>
	 * To do this:
	 * <ol>
	 * <li>Convert input character to its alphabetical index, e.g. 'A' would be
	 * 0, 'B' would be 1, etc.</li>
	 * <li>Run the letter through the rotors in forward order.</li>
	 * </ol>
	 * 
	 * <p>
	 * To "run character through rotors", use your converted-letter as the index
	 * into the desired row of the rotors array. Then, apply the reflector, and
	 * run the letter through the rotors again, but in reverse. Encoding in
	 * reverse not only implies that the rotor-order is to be reversed. It also
	 * means that each cipher is applied in reverse.
	 * </p>
	 * 
	 * An example:
	 * 
	 * <pre>
	 *      Cipher (input):     EKMFLGDQVZNTOWYHXUSPAIBRCJ
	 *      Alphabet (output):  ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 * </pre>
	 * 
	 * While encoding in reverse, 'E' would get encoded as 'A', 'K' as 'B', etc.
	 * (In the forward direction, 'E' would get encoded as 'L')
	 *
	 * Finally, convert your letter integer index value back to a traditional
	 * character value.
	 *
	 * @param rotors
	 *            Current configuration of rotor ciphers, each in integral rotor
	 *            cipher form. Each "row" of this array represents a rotor
	 *            cipher.
	 * 
	 * @param reflector
	 *            The special reflector rotor in integral rotor cipher form.
	 * 
	 * @param input
	 *            The character to be encoded. Must be an upper-case letter. DO
	 *            NOT CONVERT TO UPPERCASE HERE.
	 * 
	 * @return The result of encoding the input character. ALL encoded
	 *         characters are upper-case.
	 */
	public static char encode(int[][] rotors, int[] reflector, char input) {

		// Encodes letters into ciphertext
		if (Character.isLetter(input) == true) {
			input = Character.toUpperCase(input);
			int inputIndex = (int) input - 65;
			for (int k = 0; k < rotors.length; k++) {
				inputIndex = rotors[k][inputIndex];
			}
			inputIndex = reflector[inputIndex];
			for (int k = rotors.length - 1; k >= 0; k--) {
				for (int i = 0; i < rotors[k].length; i++) {
					if (inputIndex == rotors[k][i]) {
						inputIndex = i;
						break;
					}
				}
			}
			char cipherChar = (char) (inputIndex + 65);
			return cipherChar;
		} else {
			return input;
		}
	}

	/**
	 * Advances the rotors. (Always advances rotor at index 0. May also advance
	 * other rotors depending upon notches that are reached.)
	 *
	 * <ol>
	 * <li>Advancement takes place, starting at rotor at index 0.</li>
	 * <li>The 0th rotor is rotated</li>
	 * <li>Update the corresponding offset in <tt>rotorOffsets.</tt></li>
	 * <li>Check to see if the current offset matches a notch (meaning that a
	 * notch has been reached).</li>
	 * <li>If a notch has been reached:
	 * <ol>
	 * <li>The next rotor must also be advanced</li>
	 * <li>And have its rotorOffset updated.</li>
	 * <li>And if a notch is reached, the next rotor is advanced.</li>
	 * </ol>
	 * <li>Otherwise, notch was not reached, so no further rotors are advanced.</li>
	 * </ol>
	 *
	 * <p>
	 * Advancement halts when a <tt>rotorOffset</tt> is updated and it does not
	 * reach a notch for that rotor.
	 * </p>
	 * 
	 * Note: The reflector never advances, it always stays stationary.
	 *
	 * @param rotors
	 *            The array of rotor ciphers in their current configuration. The
	 *            rotor at index 0 is the first rotor to be considered for
	 *            advancement. It will always rotate exactly once. The remaining
	 *            rotors may advance when notches on earlier rotors are reach.
	 *            Later rotors will not not advance as often unless there is a
	 *            notch at each index. (Tip: We try such a configuration during
	 *            testing). The data in this array will be updated to show the
	 *            rotors' new positions.
	 * 
	 * @param rotorOffsets
	 *            The current offsets by which the rotors have been rotated.
	 *            These keep track of how far each rotor has rotated since the
	 *            beginning of the program. The offset at index i will
	 *            correspond to rotor at index 0 of rotors. e.g. offset 0
	 *            pertains to the 0th rotor cipher in rotors. Will be updated
	 *            in-place to reflect the new offsets after advancing. The
	 *            offsets are compared to notches to know when next rotor must
	 *            also be advanced.
	 * 
	 * @param rotorNotches
	 *            The positions of the notches on each of the rotors. Each row
	 *            of this array represents the notches of a certain rotor. The
	 *            ith row will correspond to the notches of the ith rotor cipher
	 *            in rotors. Only when a rotor advances to its notched position
	 *            does the next rotor in the chain advance.
	 */
	public static void advance(int[][] rotors, int[] rotorOffsets,
			int[][] rotorNotches) {

		// Increments the first rotor offset so that it will never exceed 25
		rotorOffsets[0] = (rotorOffsets[0] + 27) % 26;

		// boolean variable used to ensure rotors only rotate in order
		boolean rotate = true;

		// First rotor is always rotated
		rotate(rotors[0]);

		// Rotates remaining rotors if necessary
		for (int k = 0; k < rotors.length - 1 && rotate == true; k++) {
			for (int j = 0; j < rotorNotches[k].length; j++) {
				if (rotorOffsets[k] == rotorNotches[k][j]) {
					rotate(rotors[k + 1]);
					rotorOffsets[k + 1] = (rotorOffsets[k + 1] + 1) % 26;
					rotate = true;
					break;
				} else {
					rotate = false;
				}
			}
		}
	}

} // end of Enigma class
