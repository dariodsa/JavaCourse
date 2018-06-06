package hr.fer.zemris.java.gui.calc;

/**
 * Interface {@link CalcValueListener} specifies which method should every
 * listener of a value in calculator should have.
 * 
 * @author dario
 *
 */
public interface CalcValueListener {
    /**
     * Method says that the value was changed and give us model from which we can
     * get new state.
     * 
     * @param model
     *            model of calculator, state
     */
    void valueChanged(CalcModel model);
}