/**
 * An alternative collection of rotor constants to use in your project.
 * The rotor constants of this file are based of the German Railway Enigma
 * @author sbrown
 */
public class RotorConstants {
    
    /** This constant specifies the number of letters printed on each rotor. */
    public static final int ROTOR_LENGTH = 26;
    
    /**
     * This is a 1D array of cipher strings, which represent the 
     * letter-orderings for each rotor. The first rotor (Rotor 0) is the 
     * identity rotor, which you may find useful for debugging. The remaining cipher strings 
     * correspond to Rotor I, Rotor II, etc.
     * 
     * <p>Real rotor data was taken from
     * http://en.wikipedia.org/wiki/Enigma_rotor_details#Rotor_wiring_tables
     */
    public static final String[] ROTORS = {
        // Identity rotor (useful for testing):
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        
        // Standard rotors I through III of the German Railway Enigma:
        "JGDQOXUSCAMIFRVTPNEWKBLZYH",
        "NTZPSFBOKMWRCJDIVLAEYUXHGQ",
        "JVIUBHTCDYAKEQZPOSGXNRMWFL"
    };
    
    /**
     * This is a String constant which represents the letters to be 
     * printed on the special Reflector rotor. The reflector rotor is placed 
     * at the end of the stack of other rotors. It cipher has the special property
     * that every pair of characters map to each other. 
     */
    public static final String REFLECTOR = "EJMZALYXVBWFCRQUONTSPIKHGD";
    
    /**
     * A disjoint 2D array of integers, which represents the locations of each
     * rotors' step notches. Each rotor has one or two step notches next to 
     * particular encoded letters. When a rotor is turned into the position of 
     * its step notch,
     * it also causes the next rotor to advance by one position.
     * 
     * <p> Each rotor may 
     * have one or more notches. This array has a 1-to-1 correspondence with 
     * the ROTORS array. That is, the rotor in index 0
     * of ROTORS will correspond to the values stored at NOTCHES index 0.
     */
    public static final int[][] NOTCHES = {
        // Identity rotor:
        { 22 }, // W
        
        // Standard rotors:
        { 2, 4, 6, 8 }, // C, E, G, I
        { 21, 22, 23, 24, 25 }, // V, W, X, Y, Z
        { 1, 13 }, // B, N
    };
    
}
