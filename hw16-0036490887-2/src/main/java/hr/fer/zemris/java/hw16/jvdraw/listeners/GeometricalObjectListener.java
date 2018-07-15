package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Interface {@link GeometricalObjectListener} specifies what should every
 * listeners must implement in order to communicate with the subject and to
 * receive its new state.
 * 
 * @author dario
 *
 */
public interface GeometricalObjectListener {
    /**
     * Notifies that the new {@link GeometricalObject} has been added
     * @param o new {@link GeometricalObject}
     */
    public void geometricalObjectChanged(GeometricalObject o);
}
