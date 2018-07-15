package hr.fer.zemris.java.hw16.jvdraw.editing;

import javax.swing.JPanel;

/**
 * Abstract class {@link GeometricalObjectEditor} will render components to
 * enable user to change properties of the objects. It also extends JPanel to do
 * so.
 * 
 * @author dario
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Checks the editing components and it will throw {@link RuntimeException} if
     * there was something wrong.
     */
    public abstract void checkEditing();

    /**
     * It will update all changed properties to the object.
     */
    public abstract void acceptEditing();

}
