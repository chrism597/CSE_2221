import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Monte Carlo Estimate: compute percentage of pseudo-random points in [0.0,1.0)
 * interval that fall in the left half subinterval [0.0,0.5).
 */
public final class MonteCarlo {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private MonteCarlo() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();
        /*
         * Constant to convert a ratio into a percentage
         */
        final double percentageFactor = 100.0;

        output.print("Number of points: ");
        int n = input.nextInteger();

        output.print("Radius of Target: ");
        int radius = input.nextInteger();

        output.print("X Center of Target: ");
        int h = input.nextInteger();

        output.print("Y Center of Target: ");
        int k = input.nextInteger();

        output.print("Side Length of Square: ");
        int l = input.nextInteger();

        /*
         * Declare counters and initialize them
         */
        int ptsInInterval = 0, ptsInSubinterval = 0;
        /*
         * Create pseudo-random number generator
         */
        Random rnd = new Random1L();
        /*
         * Generate points and count how many fall in [0.0,0.5) interval
         */
        while (ptsInInterval < n) {
            /*
             * Generate pseudo-random number in [0.0,1.0) interval
             */
            double x = l * rnd.nextDouble();
            double y = l * rnd.nextDouble();

            /*
             * Increment total number of generated points
             */
            ptsInInterval++;
            /*
             * Check if point is in [0.0,0.5) interval and increment counter if
             * it is
             */
            if ((x - h) * (x - h) + (y - k) * (y - k) <= radius * radius) {
                ptsInSubinterval++;
            }
        }
        /*
         * Estimate percentage of points generated in [0.0,1.0) interval that
         * fall in the [0.0,0.5) subinterval
         */
        double estimate = (percentageFactor * ptsInSubinterval) / ptsInInterval;
        output.println("Estimate of percentage: " + estimate + "%");

        double sqreArea = l * l;
        double area = (ptsInSubinterval / (double) ptsInInterval) * sqreArea;
        output.println("Estimated area of circle: " + area);

        /*
         * Close input and output streams
         */
        input.close();
        output.close();
    }

}
