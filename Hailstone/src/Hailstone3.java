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
public final class Hailstone3 {
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
    private Hailstone3() {
    }

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
        int max = 0;
        while (start != 1) {
            out.print(start + " ");
            if (start % 2 == 0) {
                start /= 2;
            } else {
                start = HAILSTONE_MULTI * start + ODD_INCREMENT;
            }
            if (max < start) {
                max = start;
            }
            length++;
        }
        out.println();
        out.println("Final output is " + start);
        out.println("Length is " + length);
        out.println("Max value of the series is " + max);
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
