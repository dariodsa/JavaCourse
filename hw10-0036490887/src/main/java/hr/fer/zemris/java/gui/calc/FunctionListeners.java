package hr.fer.zemris.java.gui.calc;

/**
 * Interface {@link FunctionListeners} specifies what every listeners should
 * provide to the subject so that subject can interact with listener or to send
 * new state to them.
 * 
 * @author dario
 *
 */
public interface FunctionListeners {
    /**
     * Gives new subject's state to the listener.
     * 
     * @param function
     *            new state from subject
     */
    public void newState(Functions function);
}
