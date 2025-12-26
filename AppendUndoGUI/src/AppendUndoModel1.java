import components.stack.Stack;
import components.stack.Stack1L;

/**
 * Model implementation for the Append/Undo application.
 *
 * The model state is: - this.input: current input string - this.output: stack
 * of strings that have been appended, with the most recently appended string at
 * the top of the stack.
 *
 * @author YOUR_NAME
 */
public final class AppendUndoModel1 implements AppendUndoModel {

    /**
     * Current input string.
     */
    private String input;

    /**
     * Stack of strings appended to the output (top = most recent).
     */
    private Stack<String> output;

    /**
     * Default constructor.
     *
     * @ensures this.input = "" and this.output = <>
     */
    public AppendUndoModel1() {
        this.input = "";
        this.output = new Stack1L<String>();
    }

    @Override
    public String input() {
        return this.input;
    }

    @Override
    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public Stack<String> output() {
        return this.output;
    }
}
