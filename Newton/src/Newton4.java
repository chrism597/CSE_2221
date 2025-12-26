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
public final class Newton4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
    }

    /**
     * This method approximates the square root using Newton iteration.
     *
     * @param x
     * @param epsilon
     * @return returns the approximated square root
     */
    private static double sqrt(double x, double epsilon) {
        if (x == 0) {
            return 0;
        }
        double r = x;
        double errorThresh = epsilon;
        while (Math.abs(r * r - x) / x > errorThresh) {
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

        out.println("Enter a number to square root:");
        double x = in.nextDouble();

        // Ask for allowed error
        out.println("Enter a number for the allowed error (ε):");
        double epsi = in.nextDouble();

        // Calculate and display the first square root
        double newtSqrt = sqrt(x, epsi);
        out.println("The square root of " + x + " is " + newtSqrt);

        /**
         * This allows for the user to input another number to square root
         * without having to rerun the program
         */
        while (true) {
            out.println("Enter a number to square root: ");
            x = in.nextDouble();

            if (x < 0) {
                break;
            }

            double sqrt = sqrt(x, epsi);
            out.println("The square root of " + x + " is " + sqrt);
        }
        /**
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
