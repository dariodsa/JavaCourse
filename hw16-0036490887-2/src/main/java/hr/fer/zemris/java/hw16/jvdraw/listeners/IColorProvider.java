package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

/**
 * Interface {@link IColorProvider} specifies what should every color provider
 * must implement. User will be able to get it current color and also to
 * register on possible changes in the current color.
 * 
 * @author dario
 *
 */
public interface IColorProvider {

    /**
     * Returns the current color.
     * 
     * @return current color
     */
    public Color getCurrentColor();

    /**
     * Adds new {@link ColorChangeListener} to the list of listeners.
     * 
     * @param l
     *            new listener
     */
    public void addColorChangeListener(ColorChangeListener l);

    /**
     * Removes the {@link ColorChangeListener} from the list of listeners
     * 
     * @param l
     *            listener which will be removed
     */
    public void removeColorChangeListener(ColorChangeListener l);
}
