import components.stack.Stack;

/**
 * Controller implementation for the Append/Undo application.
 *
 * This class follows the exact behavior specified by the
 * {@code AppendUndoController} Javadoc:
 *
 * - processResetEvent: this.model.input = "" this.model.output = <> view
 * updated to match model
 *
 * - processAppendEvent(input): this.model.input = "" this.model.output =
 * <input> * #this.model.output view updated to match model
 *
 * - processUndoEvent(): requires this.model.output /= <> #this.model.output =
 * <this.model.input> * this.model.output view updated to match model
 *
 * @author YOUR_NAME
 */
public final class AppendUndoController1 implements AppendUndoController {

    /**
     * The underlying model.
     */
    private final AppendUndoModel model;

    /**
     * The associated view.
     */
    private final AppendUndoView view;

    /**
     * Constructor.
     *
     * @param model
     *            model to use
     * @param view
     *            view to use
     * @ensures this.model = model and this.view = view
     */
    public AppendUndoController1(AppendUndoModel model, AppendUndoView view) {
        this.model = model;
        this.view = view;
        /*
         * Make sure the view starts consistent with the model.
         */
        this.updateViewFromModel();
    }

    /**
     * Builds a String representing the current output.
     *
     * The model's output is a stack (top = most recent), and the documentation
     * calls it "a stack of the strings appended to the output in reverse
     * order". Here we simply concatenate the stack contents in LIFO order (top
     * first).
     *
     * @return concatenation of all strings in the output stack
     */
    private String buildOutputString() {
        Stack<String> out = this.model.output();
        Stack<String> temp = out.newInstance();

        StringBuilder result = new StringBuilder();

        /*
         * Pop everything from the stack, remember it in temp, and build the
         * display string.
         */
        while (out.length() > 0) {
            String top = out.pop();
            result.append(top);
            temp.push(top);
        }

        /*
         * Restore original contents.
         */
        while (temp.length() > 0) {
            out.push(temp.pop());
        }

        return result.toString();
    }

    /**
     * Syncs the view to the current model state.
     */
    private void updateViewFromModel() {
        this.view.updateInputDisplay(this.model.input());
        this.view.updateOutputDisplay(this.buildOutputString());
        this.view.updateUndoAllowed(this.model.output().length() > 0);
    }

    @Override
    public void processResetEvent() {
        this.model.setInput("");
        this.model.output().clear();
        this.updateViewFromModel();
    }

    @Override
    public void processAppendEvent(String input) {
        if (input == null) {
            input = "";
        }
        /*
         * Spec: this.model.output = <input> * #this.model.output
         * this.model.input = ""
         */
        if (!input.isEmpty()) {
            this.model.output().push(input);
        }
        this.model.setInput("");
        this.updateViewFromModel();
    }

    @Override
    public void processUndoEvent() {
        /*
         * Requires: this.model.output /= <> We defensively check this and
         * simply do nothing if violated.
         */
        if (this.model.output().length() > 0) {
            String undone = this.model.output().pop();
            /*
             * Ensures says the popped value becomes this.model.input.
             */
            this.model.setInput(undone);
        }
        this.updateViewFromModel();
    }
}
