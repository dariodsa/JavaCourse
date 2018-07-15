package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

/**
 * Interface {@link ColorChangeListener} specifies what should every
 * listeners must implement in order to communicate with the subject and to
 * receive its new state. It will notify about color changes.
 * 
 * @author dario
 *
 */
public interface ColorChangeListener {
    
    /**
     * Method notifies the listener about color changes in the given source.
     * @param source {@link IColorProvider} source of the changes
     * @param oldColor old color
     * @param newColor new color
     */
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
