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
public final class Hailstone2 {
    /**
     * The multiplier used if the value is odd.
     */
    private static final int HAILSTONE_MULTI = 3;
    /**
     * The increment used to increase the value if it is odd.
     */
    private static final int ODD_INCREMENT = 1;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone2() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int length = 0;
        int start = n;
        while (start != 1) {
            out.print(start + " ");
            if (start % 2 == 0) {
                start /= 2;
            } else {
                start = HAILSTONE_MULTI * start + ODD_INCREMENT;
            }
            length++;
        }
        out.println();
        out.println("Final output is " + start);
        out.println("Length is " + length);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter a positive integer: ");
        int n = in.nextInteger();
        generateSeries(n, out);
        in.close();
        out.close();
    }

}
