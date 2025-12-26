import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * Test suite for the {@code NaturalNumberStaticOps.toStringWithCommas} method.
 *
 * Each test case includes a rationale explaining what it verifies.
 *
 * Homework: Design a Test Plan Prepared for lab: Recursion on NaturalNumber –
 * Static Methods
 */
public final class NaturalNumberStaticOpsTest {

    /**
     * Tests smallest possible input (boundary case): n = 0. Rationale: Ensures
     * method correctly returns "0" with no commas for zero.
     */
    @Test
    public void testToStringWithCommas_zero() {
        NaturalNumber n = new NaturalNumber1L(0);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("0", result);
    }

    /**
     * Tests number with fewer than 3 digits: n = 7. Rationale: No commas should
     * appear for single-digit values.
     */
    @Test
    public void testToStringWithCommas_singleDigit() {
        NaturalNumber n = new NaturalNumber1L(7);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("7", result);
    }

    /**
     * Tests number with exactly 3 digits: n = 123. Rationale: Verifies that
     * numbers below 1000 do not contain commas.
     */
    @Test
    public void testToStringWithCommas_threeDigits() {
        NaturalNumber n = new NaturalNumber1L(123);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("123", result);
    }

    /**
     * Tests number with 4 digits: n = 1234. Rationale: Verifies that a single
     * comma is correctly inserted before the last three digits (expected:
     * "1,234").
     */
    @Test
    public void testToStringWithCommas_fourDigits() {
        NaturalNumber n = new NaturalNumber1L(1234);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("1,234", result);
    }

    /**
     * Tests number with 6 digits: n = 123456. Rationale: Ensures commas appear
     * in the correct place for multiple three-digit groups (expected:
     * "123,456").
     */
    @Test
    public void testToStringWithCommas_sixDigits() {
        NaturalNumber n = new NaturalNumber1L(123456);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("123,456", result);
    }

    /**
     * Tests number with 7 digits: n = 1234567. Rationale: Checks that commas
     * are placed properly across multiple groups (expected: "1,234,567").
     */
    @Test
    public void testToStringWithCommas_sevenDigits() {
        NaturalNumber n = new NaturalNumber1L(1234567);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("1,234,567", result);
    }

    /**
     * Tests large number with 12 digits: n = 987654321012. Rationale: Ensures
     * correct placement of multiple commas for large values (expected:
     * "987,654,321,012").
     */
    @Test
    public void testToStringWithCommas_twelveDigits() {
        NaturalNumber n = new NaturalNumber1L("987654321012");
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("987,654,321,012", result);
    }

    /**
     * Tests number that ends with zeros: n = 1000000. Rationale: Checks that
     * commas appear correctly even when digits end with zeros (expected:
     * "1,000,000").
     */
    @Test
    public void testToStringWithCommas_trailingZeros() {
        NaturalNumber n = new NaturalNumber1L(1000000);
        String result = NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals("1,000,000", result);
    }

    /**
     * Tests that the input {@code NaturalNumber} is not modified. Rationale:
     * Ensures method restores n to its original value.
     */
    @Test
    public void testToStringWithCommas_inputRestored() {
        NaturalNumber n = new NaturalNumber1L(54321);
        NaturalNumber copy = new NaturalNumber1L(n);
        NaturalNumberStaticOps.toStringWithCommas(n);
        assertEquals(copy, n);
    }
}
