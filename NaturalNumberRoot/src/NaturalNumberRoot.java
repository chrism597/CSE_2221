import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * <p>
 * This method uses the interval halving (binary search) algorithm to find the
 * integer {@code r}-th root of a {@code NaturalNumber}. It satisfies the
 * postcondition:
 * </p>
 *
 * <pre>
 * n^r <= #n < (n + 1)^r
 * </pre>
 *
 * @author Put your name here
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is not null";
        assert r >= 2 : "Violation of: r >= 2";

        // Store the original value of n
        NaturalNumber original = new NaturalNumber2(n);

        // Set up lower and upper bounds: [low, high)
        NaturalNumber low = new NaturalNumber2(0);
        NaturalNumber high = new NaturalNumber2(original);
        high.increment();

        // Temporary variables
        NaturalNumber mid = new NaturalNumber2(0);
        NaturalNumber midToRth = new NaturalNumber2(0);
        NaturalNumber midPlusOneToRth = new NaturalNumber2(0);

        // Compute r-th root of n using interval halving (binary search)
        // Stop when low > high
        while (low.compareTo(high) <= 0) {
            mid.copyFrom(low);
            mid.add(high);
            mid.divide(new NaturalNumber2(2));

            // Compute r-th power of mid = mid ^ r
            midToRth.copyFrom(mid);
            midToRth.power(r);
            midPlusOneToRth.copyFrom(mid);
            midPlusOneToRth.increment();
            midPlusOneToRth.power(r);

            // Compare mid^r to original to reduce the interval by half
            if (midToRth.compareTo(original) > 0) {
                // mid^r > n → root is between low and (mid - 1)
                // set high = mid - 1
                mid.decrement();
                high.copyFrom(mid);
            } else if (original.compareTo(midPlusOneToRth) >= 0) {
                // (mid + 1)^r <= n → root is between (mid + 1) and high
                mid.increment();
                low.copyFrom(mid);
            } else {
                // at this point, mid^r <= n < (mid + 1)^r
                n.copyFrom(mid);
                break;
            }
        }
    }

    /**
     * Main method to test the {@code root} method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0", "1", "13",
                "4096", "189943527", "0", "1", "13", "1024", "189943527", "82", "82",
                "82", "82", "82", "9", "27", "81", "243", "143489073", "2147483647",
                "2147483648", "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111", "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };

        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15, 2, 3, 4,
                5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };

        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2", "16",
                "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1", "3", "3", "3",
                "3", "3", "46340", "46340", "2097151", "2097152", "4987896", "2767208",
                "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber expected = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(expected)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i] + ", "
                        + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root(" + numbers[i] + ", "
                        + roots[i] + ") expected <" + results[i] + "> but was <" + n
                        + ">");
            }
        }

        out.close();
    }

}
