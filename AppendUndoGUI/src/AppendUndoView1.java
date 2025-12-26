import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View implementation for the Append/Undo application.
 *
 * This class builds the GUI and forwards user events to the registered
 * {@code AppendUndoController}. It also implements all methods specified by
 * {@code AppendUndoView}, which itself extends {@code ActionListener}.
 *
 * @author YOUR_NAME
 */
public final class AppendUndoView1 extends JFrame implements AppendUndoView {

    /**
     * Serial version UID for JFrame.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Text field where the user types the next string to append.
     */
    private final JTextField inputField;

    /**
     * Text field that displays the accumulated output.
     */
    private final JTextField outputField;

    /**
     * "Reset" button.
     */
    private final JButton resetButton;

    /**
     * "Append" button.
     */
    private final JButton appendButton;

    /**
     * "Undo" button.
     */
    private final JButton undoButton;

    /**
     * The controller observing this view.
     */
    private AppendUndoController controller;

    /**
     * Constructs the GUI.
     */
    public AppendUndoView1() {
        super("Append / Undo");

        /*
         * Main window layout.
         */
        this.setLayout(new BorderLayout());

        /*
         * Top panel: input controls.
         */
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel inputLabel = new JLabel("Input:");
        this.inputField = new JTextField(15);
        this.appendButton = new JButton("Append");
        this.undoButton = new JButton("Undo");
        this.resetButton = new JButton("Reset");

        topPanel.add(inputLabel);
        topPanel.add(this.inputField);
        topPanel.add(this.appendButton);
        topPanel.add(this.undoButton);
        topPanel.add(this.resetButton);

        /*
         * Bottom panel: output display.
         */
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel outputLabel = new JLabel("Output:");
        this.outputField = new JTextField(20);
        this.outputField.setEditable(false);

        bottomPanel.add(outputLabel);
        bottomPanel.add(this.outputField);

        /*
         * Add panels to frame.
         */
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);

        /*
         * This view listens to its own widgets.
         */
        this.inputField.addActionListener(this);
        this.appendButton.addActionListener(this);
        this.undoButton.addActionListener(this);
        this.resetButton.addActionListener(this);

        /*
         * Standard window settings.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void registerObserver(AppendUndoController controller) {
        this.controller = controller;
        /*
         * Reasonable initial display state: empty input/output and undo not
         * allowed.
         */
        this.updateInputDisplay("");
        this.updateOutputDisplay("");
        this.updateUndoAllowed(false);
    }

    @Override
    public void updateInputDisplay(String input) {
        this.inputField.setText(input);
    }

    @Override
    public void updateOutputDisplay(String output) {
        this.outputField.setText(output);
    }

    @Override
    public void updateUndoAllowed(boolean allowed) {
        this.undoButton.setEnabled(allowed);
    }

    /**
     * Handles button presses and Enter in the input field, then calls the
     * appropriate controller method as specified by the interfaces.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (this.controller == null) {
            // registerObserver must be called first, per spec
            return;
        }

        Object source = event.getSource();

        if (source == this.resetButton) {
            this.controller.processResetEvent();
        } else if (source == this.appendButton || source == this.inputField) {
            String text = this.inputField.getText();
            this.controller.processAppendEvent(text);
        } else if (source == this.undoButton) {
            this.controller.processUndoEvent();
        }
    }
}
