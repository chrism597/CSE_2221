import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        // Base case: <number value="..." />
        if (exp.label().equals("number")) {
            NaturalNumber n = new NaturalNumber2(exp.attributeValue("value"));
            return n;
        }

        // Recursive case (operator node)
        // Assume two child nodes as no unary operator is expected.
        NaturalNumber left = evaluate(exp.child(0));
        NaturalNumber right = evaluate(exp.child(1));
        NaturalNumber result = new NaturalNumber2(left); // defensive copy

        String operator = exp.label();

        if (operator.equals("plus")) {
            result.add(right);
        } else if (operator.equals("minus")) {
            if (result.compareTo(right) < 0) {
                Reporter.fatalErrorToConsole("ERROR: NaturalNumber cannot be negative.");
            }
            result.subtract(right);
        } else if (operator.equals("times")) {
            result.multiply(right);
        } else if (operator.equals("divide")) {
            if (right.isZero()) {
                Reporter.fatalErrorToConsole("ERROR: Divide by zero.");
            }
            result.divide(right);
        } else if (operator.equals("mod")) {
            if (right.isZero()) {
                Reporter.fatalErrorToConsole("ERROR: Divide by zero.");
            }
            result = result.divide(right);
        } else if (operator.equals("power")) {
            if (right.toInt() < 0) {
                Reporter.fatalErrorToConsole("ERROR: Power cannot be negative.");
            }
            result.power(right.toInt());
        } else if (operator.equals("root")) {
            if (right.toInt() < 2) {
                Reporter.fatalErrorToConsole("ERROR: Index of root is less than 2.");
            }
            result.root(right.toInt());
        }

        return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
