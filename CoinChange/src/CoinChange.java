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
public final class CoinChange {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange() {
    }

    private static final int DOLLAR = 100;
    private static final int HALF_DOLLAR = 50;
    private static final int QUARTER = 25;
    private static final int DIME = 10;
    private static final int NICKEL = 5;
    private static final int PENNY = 1;

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

        int dollars = cents / DOLLAR;
        int leftOver = cents % DOLLAR;

        int halfDollars = leftOver / HALF_DOLLAR;
        leftOver = leftOver % HALF_DOLLAR;

        int quarters = leftOver / QUARTER;
        leftOver = leftOver % QUARTER;

        int dimes = leftOver / DIME;
        leftOver = leftOver % DIME;

        int nickels = leftOver / NICKEL;
        leftOver = leftOver % NICKEL;

        int pennies = leftOver / PENNY;

        out.println("Change for " + cents + " cents:");
        out.println("Dollars: " + dollars);
        out.println("Half-dollars: " + halfDollars);
        out.println("Quarters: " + quarters);
        out.println("Dimes: " + dimes);
        out.println("Nickels: " + nickels);
        out.println("Pennies: " + pennies);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
