import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Chris Ma
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (NaturalNumber.RADIX * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */

        if (!m.isZero()) {
            NaturalNumber remainder = n.divide(m);
            n.copyFrom(m);
            reduceToGCD(n, remainder);
        }
        m.clear();
    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {

        int lastDigit = n.divideBy10();
        n.multiplyBy10(lastDigit);
        return (lastDigit % 2 == 0);
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p, NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";

        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */

        final NaturalNumber two = new NaturalNumber2(2);

        // Base case: if p = 0, result of n = #n ^ (p) mod m = 1
        if (p.isZero()) {
            n.setFromInt(1);
        } else {
            // Recursive case: if p != 0, compute the powerMod by (n * n) ^ (p / 2) mod m
            NaturalNumber nOrig = new NaturalNumber2(n);
            NaturalNumber half = new NaturalNumber2(p);
            NaturalNumber powerParity = half.divide(two);

            powerMod(n, half, m);

            // the square of n
            NaturalNumber nCopy = new NaturalNumber2(n);
            n.multiply(nCopy);

            // n = n^2 mod m
            NaturalNumber remainder = n.divide(m);
            n.copyFrom(remainder);

            // if p is odd, multiply once more by original n
            if (!powerParity.isZero()) {
                n.multiply(nOrig);
                remainder = n.divide(m);
                n.copyFrom(remainder);
            }
        }
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w, NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);

        // Make copies of w as powerMod modifies input parameter
        NaturalNumber w1 = w.newInstance();
        w1.copyFrom(w);
        NaturalNumber w2 = w.newInstance();
        w2.copyFrom(w);

        // Check (w ^ 2 mod n = 1)
        powerMod(w1, two, n);
        boolean expression1 = w1.equals(one);

        // Check (w ^ (n-1) mod n /= 1)
        NaturalNumber nMinusOne = new NaturalNumber2(n);
        nMinusOne.decrement();
        powerMod(w2, nMinusOne, n);
        boolean expression2 = w2.compareTo(one) != 0;

        return (expression1 || expression2);
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */

        // TODO - fill in body
        boolean isPrime = true;
        NaturalNumber one = new NaturalNumber2(1);
        // random w < n-1 (w <= n-2)
        NaturalNumber wBound = new NaturalNumber2(n);
        wBound.subtract(new NaturalNumber2(2));

        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            // generate 50 random numbers in [2, n-2] to check primality of n
            // if cannot find a witness that n is composite, n is probably a prime
            final int numberOfCandidates = 50;
            for (int i = 0; i < numberOfCandidates; i++) {
                NaturalNumber candidate = randomNumber(wBound);

                /*
                 * Use the random generator to produce a random number uniformly
                 * distributed in [0, n - 2]. Need to exclude 0's and 1's to
                 * prevent violation of asserts in isWitnessToCompositeness.
                 *
                 */
                while (candidate.compareTo(one) <= 0) {
                    candidate = randomNumber(wBound);
                }
                // Check primality of n
                if (isWitnessToCompositeness(candidate, n)) {
                    isPrime = false;
                }

            }
        }
        return isPrime;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only (why?), until n is likely prime
         */

        final NaturalNumber two = new NaturalNumber2(2);
        /*
         * if n is even, increment it to an odd number, any even number greater
         * than 2 is a composite as 2 is a factor other than 1 and n
         */
        if (isEven(n)) {
            n.increment();
        }

        /*
         * loop through odd numbers greater than or equal to n until a likely
         * prime is found
         */
        while (!isPrime2(n)) {
            n.add(two);
        }

    }

    /**
     * Reports whether n is a prime based on multiple rounds of the Miller–Rabin
     * primality test.
     *
     * @param n
     *            number to be checked
     * @param k
     *            number of rounds of Miller–Rabin test performed
     * @return true means n is a prime with at most 4 ^ (-k) probability of n
     *         being a composite but declared as a prime; false means n is
     *         definitely a composite
     * @requires n > 1 and k > 2
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with the probability of error = 4^(-k)
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime3(NaturalNumber n, int k) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        assert k > 2 : "Violation of: n > 2";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate k witness candidates for k
         * rounds of Miller–Rabin test.
         */

        boolean isPrime = true;

        final NaturalNumber one = new NaturalNumber2(1);
        final NaturalNumber two = new NaturalNumber2(2);

        NaturalNumber nMinusOne = new NaturalNumber2(n);
        nMinusOne.decrement();
        NaturalNumber nMinusTwo = new NaturalNumber2(nMinusOne);
        nMinusTwo.decrement();

        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * Factors out powers of 2 from (n - 1) to find s and d such that n
             * - 1 = 2^s * d, where d is an odd positive integer and s is a
             * positive integer.
             */
            NaturalNumber s = new NaturalNumber2(0);
            NaturalNumber d = new NaturalNumber2(nMinusOne);
            NaturalNumber remainder = d.divide(two);
            while (remainder.isZero()) {
                s.increment();
                remainder = d.divide(two);
            }
            d.multiply(two);
            d.add(remainder);

            /*
             * Performs k rounds of Miller–Rabin primality test using random
             * base a in [2, n-2].
             */
            for (int i = 0; i < k; i++) {
                // pick a random base a in [2, n-2]
                NaturalNumber a = randomNumber(nMinusOne);
                while (a.compareTo(one) <= 0) {
                    a = randomNumber(nMinusOne);
                }
                // compute x = a^d mod n
                NaturalNumber x = new NaturalNumber2(a);
                powerMod(x, d, n);

                NaturalNumber sCopy = new NaturalNumber2(s);
                NaturalNumber y = new NaturalNumber2(x);

                while (!sCopy.isZero()) {
                    // compute y = x^2 mod n
                    powerMod(y, two, n);
                    // test if the only square roots of 1 modulo n are 1 and −1
                    if (y.compareTo(one) == 0 && x.compareTo(one) != 0
                            && x.compareTo(nMinusOne) != 0) {
                        // nontrivial square root of 1 mod n
                        // found a witness that n is a composite
                        isPrime = false;
                    }
                    x.copyFrom(y);
                    sCopy.decrement();
                }
                // test if a^(n-1) is congruent to 1 mod n
                if (y.compareTo(one) != 0) {
                    isPrime = false;
                }
            }
        }
        return isPrime;
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

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println(
                "  expected value = " + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        boolean done = false;
        while (!done) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                done = true;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number" + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number" + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
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
