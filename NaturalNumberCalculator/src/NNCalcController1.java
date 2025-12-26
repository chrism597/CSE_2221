import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Chris Ma
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model, NNCalcView view) {

        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        // Update displayed numbers
        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);

        // Enable/disable subtract (allowed if top >= bottom)
        view.updateSubtractAllowed(bottom.compareTo(top) <= 0);

        // Enable/disable divide (bottom ≠ 0)
        view.updateDivideAllowed(!bottom.isZero());

        // Enable/disable power (exponent ≤ INT_LIMIT)
        view.updatePowerAllowed(bottom.compareTo(INT_LIMIT) <= 0);

        // Enable/disable root (2 ≤ root ≤ INT_LIMIT)
        view.updateRootAllowed(
                bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) <= 0);
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.copyFrom(bottom);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.add(bottom);
        bottom.transferFrom(top);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSubtractEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.subtract(bottom);
        bottom.transferFrom(top);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processMultiplyEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.multiply(bottom);
        bottom.transferFrom(top);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processDivideEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        NaturalNumber remainder = top.divide(bottom);
        bottom.transferFrom(top);
        top.copyFrom(remainder);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processPowerEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        int exponent = bottom.toInt();
        top.power(exponent);
        bottom.transferFrom(top);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRootEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        int root = bottom.toInt();
        top.root(root);
        bottom.transferFrom(top);

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        NaturalNumber bottom = this.model.bottom();

        bottom.multiplyBy10(digit);

        updateViewToMatchModel(this.model, this.view);
    }

}
