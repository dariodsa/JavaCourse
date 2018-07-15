package hr.fer.zemris.java.hw16.jvdraw.models;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModelListener;

/**
 * Class {@link DrawingObjectListModel} extends {@link AbstractListModel} and 
 * implements {@link DrawingModelListener} 
 * @author dario
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * drawing model of objects
     */
    private DrawingModel model;

    /**
     * Constructs the {@link DrawingObjectListModel} with the given
     * {@link DrawingModel} from which will get all objects.
     * 
     * @param model
     *            from which will get object which will be shown in the
     *            {@link JList}
     */
    public DrawingObjectListModel(DrawingModel model) {
        this.model = model;
    }

    @Override
    public GeometricalObject getElementAt(int index) {
        return model.getObject(index);
    }

    @Override
    public int getSize() {
        return model.size();
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(model, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel sourlce, int index0, int index1) {
        fireIntervalRemoved(model, index0, index1);

    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(model, index0, index1);

    }

}
