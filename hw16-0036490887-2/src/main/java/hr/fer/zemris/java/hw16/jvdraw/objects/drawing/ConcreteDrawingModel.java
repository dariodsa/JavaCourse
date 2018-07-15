package hr.fer.zemris.java.hw16.jvdraw.objects.drawing;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * {@link ConcreteDrawingModel} implements {@link DrawingModel} and specifies all model's methods.
 * @author dario
 *
 */
public class ConcreteDrawingModel implements DrawingModel, GeometricalObjectListener {

    /**
     * list of objects
     */
    private List<GeometricalObject> objects = new ArrayList<>();
    /**
     * number of objects
     */
    private int size = 0;
    /**
     * list of listeners
     */
    private List<DrawingModelListener> listeners = new ArrayList<>();
    
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public GeometricalObject getObject(int index) {
        if( index < 0 || index >= size) {
            throw new RuntimeException("Wrong index position.");
        }
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        objects.add(object);
        object.addGeometricalObjectListener(this);
        ++size;
        fireListeners(ActionType.ADDED, size-1, size);
    }

    
    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }
    /**
     * Fire listeners.
     * @param type action type
     * @param index0 minimum index
     * @param index1 maximum index
     */
    private void fireListeners(ActionType type, int index0, int index1) {
        for(DrawingModelListener l : listeners) {
            switch (type) {
            case ADDED:
                l.objectsAdded(this, index0, index1);
                break;
            case REMOVED:
                l.objectsRemoved(this, index0, index1);
                break;
            case CHANGED:
                l.objectsChanged(this, index0, index1);
                break;
            default:
                throw new RuntimeException("Unsupported operation");
            }
        }
    }
    

    @Override
    public void geometricalObjectChanged(GeometricalObject g) {
        fireListeners(ActionType.CHANGED, 0, 0);
    }
    /**
     * Types of action that could be performed on the list of object in the {@link DrawingModel}
     * @author dario
     *
     */
    private enum ActionType {
        /**
         * adding element
         */
        ADDED,
        /**
         * removing element
         */
        REMOVED, 
        /**
         * changing elements
         */
        CHANGED
    }

    @Override
    public void remove(GeometricalObject object) {
        objects.remove(object);
        --size;
        fireListeners(ActionType.REMOVED, 0, size);
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        
        int newIndex = objects.indexOf(object) + offset;
        objects.remove(object);
        if(newIndex < 0) {
            newIndex = 0;
        } else if(newIndex >= size) {
            newIndex = size - 1;
        }
        
        objects.add(newIndex, object);
        
        fireListeners(ActionType.CHANGED, 0, size);
    }


}
