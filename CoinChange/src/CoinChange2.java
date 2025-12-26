import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class CoinChange2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange2() {
    }

    private static final int[] COIN_VALUES = { 100, 50, 25, 10, 5, 1 };
    private static final String[] COIN_NAMES = { "Dollars", "Half-dollars", "Quarters",
            "Dimes", "Nickels", "Pennies" };

    /**
     */
    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the amount of cents: ");
        int cents = in.nextInteger();

        int[] coinCounts = new int[COIN_VALUES.length];

        int leftOver = cents;

        for (int i = 0; i < COIN_VALUES.length; i++) {
            coinCounts[i] = leftOver / COIN_VALUES[i];
            leftOver = leftOver % COIN_VALUES[i];
        }

        out.println("Change for " + cents + " cents:");
        for (int i = 0; i < COIN_VALUES.length; i++) {
            out.println(COIN_NAMES[i] + ": " + coinCounts[i]);
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
