package hr.fer.zemris.java.hw16.jvdraw.objects.drawing;

/**
 * Interface {@link DrawingModelListener} specifies what should every listeners
 * must implement in order to communicate with the subject and to receive its
 * new state. It listens on changes in the {@link DrawingModel}.
 * 
 * @author dario
 *
 */
public interface DrawingModelListener {

    /**
     * Method notifies that the object in the given range were added.
     * 
     * @param source
     *            source of changes
     * @param index0
     *            first index of changes
     * @param index1
     *            last index of changes, exclusive
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Method notifies that the object in the given range were removed.
     * 
     * @param sourlce
     *            source of changes
     * @param index0
     *            first index of changes
     * @param index1
     *            last index of changes, exclusive
     */
    public void objectsRemoved(DrawingModel sourlce, int index0, int index1);

    /**
     * Method notifies that the object in the given range were changed.
     * 
     * @param source
     *            source of changes
     * @param index0
     *            first index of changes
     * @param index1
     *            last index of changes, exclusive
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);
}