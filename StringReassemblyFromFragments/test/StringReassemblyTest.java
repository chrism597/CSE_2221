import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Tests for the StringReassembly class.
 */

public class StringReassemblyTest {

    /**
     * Tests of combination method.
     */

    @Test
    public void combinationTest1() {
        String str1 = "corner";
        String str2 = "case";
        int overlap = 0;
        String expectedResult = "cornercase";
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals(result, expectedResult);
    }

    @Test
    public void combinationTest2() {
        String str1 = "shall have a new birth of freedom";
        String str2 = "birth of freedom, and that";
        int overlap = 16;
        String expectedResult = "shall have a new birth of freedom, and that";
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals(result, expectedResult);
    }

    @Test
    public void combinationTest3() {
        String str1 = "not perish from the earth.";
        String str2 = "from the earth.";
        int overlap = 15;
        String expectedResult = "not perish from the earth.";
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals(result, expectedResult);
    }

    /**
     * Tests of addToSetAvoidingSubstrings method.
     */

    @Test
    public void addToSetAvoidingSubstringsTest1() {
        Set<String> testStrSet = new Set1L<>();
        testStrSet.add("cattle");
        testStrSet.add("dog");
        testStrSet.add("horse");
        testStrSet.add("goat");
        testStrSet.add("rabbit");

        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "cat");
        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "doggie");
        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "mouse");

        assertFalse(testStrSet.contains("cat"));
        assertTrue(testStrSet.contains("doggie"));
        assertFalse(testStrSet.contains("dog"));
        assertTrue(testStrSet.contains("mouse"));
    }

    @Test
    public void addToSetAvoidingSubstringsTest2() {
        Set<String> testStrSet = new Set1L<>();
        testStrSet.add("abcde");
        testStrSet.add("defgh");
        testStrSet.add("lmnop");
        testStrSet.add("rstuv");
        testStrSet.add("uvxyz");

        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "bc");
        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "ijklm");
        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "xyz");

        assertFalse(testStrSet.contains("bc"));
        assertTrue(testStrSet.contains("ijklm"));
        assertFalse(testStrSet.contains("xyz"));
    }

    @Test
    public void addToSetAvoidingSubstringsTest3() {
        Set<String> testStrSet = new Set1L<>();
        testStrSet.add("12345");
        testStrSet.add("98765");
        testStrSet.add("~abc~");
        testStrSet.add("abxyz");
        testStrSet.add("abc12");

        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "765");
        StringReassembly.addToSetAvoidingSubstrings(testStrSet, "x~abc~y");

        assertFalse(testStrSet.contains("765"));
        assertTrue(testStrSet.contains("x~abc~y"));
        assertFalse(testStrSet.contains("~abc~"));
    }

    /**
     * Tests of linesFromInput method.
     */

    @Test
    public void linesFromInputTest1() {
        Set<String> testStrSet;
        SimpleReader inputFile = new SimpleReader1L("data/cheer-8-2.txt");

        testStrSet = StringReassembly.linesFromInput(inputFile);

        assertTrue(testStrSet.contains("Bucks -- Beat"));
        assertTrue(testStrSet.contains("Go Bucks"));
        assertTrue(testStrSet.contains("o Bucks -- B"));
        assertTrue(testStrSet.contains("Beat Mich"));
        assertTrue(testStrSet.contains("Michigan~"));
        assertEquals(testStrSet.size(), 5);
    }

    @Test
    public void linesFromInputTest2() {
        Set<String> testStrSet;
        SimpleReader inputFile = new SimpleReader1L("data/test.txt");

        testStrSet = StringReassembly.linesFromInput(inputFile);

        assertTrue(testStrSet.contains("Two roads diverged in a yellow wood"));
        assertEquals(testStrSet.size(), 8);
        assertTrue(testStrSet.contains("To where it bent in the undergrowth"));
    }

    /**
     * Tests of printWithLineSeparators method.
     */

    @Test
    public void printWithLineSeparatorsTest1() {

        SimpleWriter out = new SimpleWriter1L("data/printtest.txt");
        String testString = "line 1~line 2~line 3~line 4~line 5~";
        StringReassembly.printWithLineSeparators(testString, out);
        out.close();

        SimpleReader in = new SimpleReader1L("data/printtest.txt");
        StringBuilder sb = new StringBuilder();
        while (!in.atEOS()) {
            String line = in.nextLine();
            sb.append(line);
            sb.append("\n");
        }
        in.close();
        String readback = sb.toString();

        String expectedString = "line 1" + "\n" + "line 2" + "\n" + "line 3" + "\n"
                + "line 4" + "\n" + "line 5" + "\n";

        assertEquals(expectedString, readback);
    }

}
