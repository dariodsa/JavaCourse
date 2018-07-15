package hr.fer.zemris.java.hw16.jvdraw.objects.drawing;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Interface {@link DrawingModel} specifies model in which user can add new
 * {@link GeometricalObject}, remove or change their order. It also support
 * public methods in which listeners can register on changes in the model's list
 * of the objects.
 * 
 * @author dario
 *
 */
public interface DrawingModel {

    /**
     * Returns the {@link GeometricalObject} at the given index.
     * 
     * @param index
     *            index location of the object
     * @return {@link GeometricalObject} at the given location
     */
    public GeometricalObject getObject(int index);

    /**
     * Returns the number of the object in the model.
     * 
     * @return length of the objects list
     */
    public int size();

    /**
     * Adds the {@link GeometricalObject} in the model.
     * 
     * @param object
     *            new {@link GeometricalObject}
     */
    public void add(GeometricalObject object);

    /**
     * Removes the given {@link GeometricalObject} from the model.
     * 
     * @param object
     *            which will be removed
     */
    public void remove(GeometricalObject object);

    /**
     * Changes the order of the given object with the given offset.
     * 
     * @param object
     *            which location will be changed
     * @param offset
     *            offset from the given location, can be positive and negative
     */
    public void changeOrder(GeometricalObject object, int offset);

    /**
     * Adds the {@link DrawingModelListener} to the list of listeners.
     * 
     * @param l
     *            new {@link DrawingModelListener} listener
     */
    public void addDrawingModelListener(DrawingModelListener l);

    /**
     * Removes the {@link DrawingModelListener} from the list of listeners.
     * 
     * @param l
     *            listener which will be removed
     */
    public void removeDrawingModelListener(DrawingModelListener l);
}
