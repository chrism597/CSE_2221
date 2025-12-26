import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Chris Ma
 *
 */
public final class Newton1 {
    /**
     * The variable used for the error when caluclating the square root.
     */
    private static final double ERRORTHRESH = 0.0001;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
    }

    /**
     * This method approximates the square root using Newton iteration.
     *
     * @param x
     * @return returns the approximated square root
     */
    private static double sqrt(double x) {
        double r = x;
        while (Math.abs(r * r - x) / x > ERRORTHRESH) {
            r = (r + x / r) / 2.0;
        }
        return r;
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
        String userResponse = "y";
        /**
         * This allows for the user to input another number to square root
         * without having to rerun the program
         */
        while (userResponse.equals("y")) {
            out.println("Do you ant to calculate a square root? y or n");
            userResponse = in.nextLine();

            if (userResponse.equals("y")) {
                out.println("Enter a number to square root:");
                double x = in.nextDouble();
                if (x == 0) {
                    out.println("Error cannot divide by 0");
                } else {
                    double sqrt = sqrt(x);
                    out.println("The square root of " + x + " is " + sqrt);
                }
            }
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
