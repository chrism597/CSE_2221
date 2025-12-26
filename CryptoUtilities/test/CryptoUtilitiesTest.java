import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Chris Ma
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    // added test for reduceToGCD
    @Test
    public void testReduceToGCD_761_1093() {
        NaturalNumber n = new NaturalNumber2(761);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber m = new NaturalNumber2(1093);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    // added test for isEven
    @Test
    public void testIsEven_512() {
        NaturalNumber n = new NaturalNumber2(512);
        NaturalNumber nExpected = new NaturalNumber2(512);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    // added test for powerMod
    @Test
    public void testPowerMod_174_55_221() {
        NaturalNumber n = new NaturalNumber2(174);
        NaturalNumber nExpected = new NaturalNumber2(47);
        NaturalNumber p = new NaturalNumber2(55);
        NaturalNumber pExpected = new NaturalNumber2(55);
        NaturalNumber m = new NaturalNumber2(221);
        NaturalNumber mExpected = new NaturalNumber2(221);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    // added tests for isWitnessToCompositeness
    /*
     * Tests of isWitnessToCompositeness
     */
    @Test
    public void testisWitnessToCompositeness_38_221() {
        NaturalNumber w = new NaturalNumber2(38);
        NaturalNumber wExpected = new NaturalNumber2(w);
        NaturalNumber n = new NaturalNumber2(221);
        NaturalNumber nExpected = new NaturalNumber2(n);
        boolean resultExpected = false;
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    @Test
    public void testisWitnessToCompositeness_24_221() {
        NaturalNumber w = new NaturalNumber2(24);
        NaturalNumber wExpected = new NaturalNumber2(w);
        NaturalNumber n = new NaturalNumber2(221);
        NaturalNumber nExpected = new NaturalNumber2(n);
        boolean resultExpected = true;
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    // added test for isPrime1
    /*
     * Test for isPrime1
     */
    @Test
    public void testIsPrime1_340561() {
        NaturalNumber n = new NaturalNumber2(340561);
        NaturalNumber nExpected = new NaturalNumber2(n);
        boolean resultExpected = true;
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    // added tests for isPrime2
    /*
     * Tests of isPrime2
     */

    @Test
    public void testIsPrime2_1009() {
        NaturalNumber n = new NaturalNumber2(1009);
        NaturalNumber nExpected = new NaturalNumber2(n);
        boolean resultExpected = true;
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    @Test
    public void testIsPrime2_6601() {
        NaturalNumber n = new NaturalNumber2(6601);
        NaturalNumber nExpected = new NaturalNumber2(n);
        boolean resultExpected = false;
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    @Test
    public void testIsPrime2_340561() {
        NaturalNumber n = new NaturalNumber2(340561);
        NaturalNumber nExpected = new NaturalNumber2(n);
        boolean resultExpected = false;
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    // added tests for isPrime3
    /*
     * Tests of isPrime3
     */

    @Test
    public void testIsPrime3_65537_16() {
        NaturalNumber n = new NaturalNumber2(65537);
        NaturalNumber nExpected = new NaturalNumber2(65537);
        int k = 16;
        boolean resultExpected = true;
        boolean result = CryptoUtilities.isPrime3(n, k);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    @Test
    public void testIsPrime3_Carmichael_40() {
        String largeCarmichael = "6553130926752006031481761";
        NaturalNumber n = new NaturalNumber2(largeCarmichael);
        NaturalNumber nExpected = new NaturalNumber2(n);
        int k = 40;
        boolean resultExpected = false;
        boolean result = CryptoUtilities.isPrime3(n, k);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

    @Test
    public void testIsPrime3_Leyland_40() {
        String largeLeyland = "523347633027360537213687137";
        NaturalNumber n = new NaturalNumber2(largeLeyland);
        NaturalNumber nExpected = new NaturalNumber2(n);
        int k = 40;
        boolean resultExpected = true;
        boolean result = CryptoUtilities.isPrime3(n, k);
        assertEquals(nExpected, n);
        assertEquals(resultExpected, result);
    }

}
