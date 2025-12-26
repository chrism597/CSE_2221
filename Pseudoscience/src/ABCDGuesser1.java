import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Chris Ma
 *
 */
public final class ABCDGuesser1 {

    /**
     * Possible exponents allowed in the de Jager formula.
     */
    private static final double[] EXPONENTS = { -5, -4, -3, -2, -1, -0.5, -1.0 / 3.0,
            -0.25, 0, 0.25, 1.0 / 3.0, 0.5, 1, 2, 3, 4, 5 };

    /**
     * Multiplier to convert to a percentage.
     */
    private static final int PERCENTAGECONSTANT = 100;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Asks the user for a positive real number, and will ask continuously until
     * valid response is provided.
     *
     * @param in
     * @param out
     * @return user inputed positive real number
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double value = -1;
        boolean isValid = false;
        while (!isValid) {
            out.print("Enter a positive real number: ");
            String input = in.nextLine();
            if (FormatChecker.canParseDouble(input)) {
                value = Double.parseDouble(input);
                if (value > 0) {
                    isValid = true;
                } else {
                    out.println("Error: number must be positive.");
                }
            } else {
                out.println("Error: not a valid real input.");
            }
        }
        return value;
    }

    /**
     * Asks the user for a positive real number not equal to 1.0, ad will ask
     * continuously until valid response is provided.
     *
     * @param in
     * @param out
     * @return user inputed positive real number not equal to 1.0
     */
    private static double getPositiveDoubleNotOne(SimpleReader in, SimpleWriter out) {
        double value = -1;
        boolean isValid = false;
        while (!isValid) {
            out.println("Enter a positive real number not equal to 1.0: ");
            String input = in.nextLine();
            if (FormatChecker.canParseDouble(input)) {
                value = Double.parseDouble(input);
                if (value > 0 && value != 1.0) {
                    isValid = true;
                } else {
                    out.println("Error: number must be > 0 and/or not equal to 1.0");
                }
            } else {
                out.println("Error: not a valid real input.");
            }
        }
        return value;
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

        out.println("Enter constant μ to approximate:");
        double mu = getPositiveDouble(in, out);

        out.println("Enter four numbers:");
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);

        double bestError = Double.MAX_VALUE;
        double bestVal = 0;
        double bestA = 0, bestB = 0, bestC = 0, bestD = 0;

        int i = 0;
        while (i < EXPONENTS.length) {
            int j = 0;
            while (j < EXPONENTS.length) {
                int k = 0;
                while (k < EXPONENTS.length) {
                    int l = 0;
                    while (l < EXPONENTS.length) {
                        double a = EXPONENTS[i], b = EXPONENTS[j], c = EXPONENTS[k],
                                d = EXPONENTS[l];

                        double value = Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                                * Math.pow(z, d);
                        double error = Math.abs(value - mu) / mu;

                        if (error < bestError) {
                            bestError = error;
                            bestVal = value;
                            bestA = a;
                            bestB = b;
                            bestC = c;
                            bestD = d;
                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }

        // Print results
        out.println("\nBest exponents found:");
        out.println(
                "a = " + bestA + ", b = " + bestB + ", c = " + bestC + ", d = " + bestD);
        out.println("Best approximation: " + bestVal);
        out.print("Relative error: ");
        out.print(bestError * PERCENTAGECONSTANT, 2, false);
        out.println("%");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
