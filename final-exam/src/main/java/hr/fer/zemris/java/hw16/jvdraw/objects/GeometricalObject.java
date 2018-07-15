package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.editing.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;

/**
 * Class {@link GeometricalObject} is an abstract class which offers adding and
 * removing its listeners as well as firing notifications to them. It also
 * specifies that {@link GeometricalObjectEditor} should be created in the given
 * method as well as the method accept which is the part of the visitor design
 * pattern.
 * 
 * @author dario
 *
 */
public abstract class GeometricalObject {

    /**
     * line keyword, for importing from .jvd
     */
    private static final String LINE = "LINE";
    /**
     * circle keyword, for importing from .jvd
     */
    private static final String CIRCLE = "CIRCLE";
    /**
     * filled circle keyword, for importing from .jvd
     */
    private static final String FCIRCLE = "FCIRCLE";
    /**
     * list of listeners
     */
    private List<GeometricalObjectListener> listeners = new ArrayList<>();
    
    /**
     * Accept method used in Visitor design pattern which accepts
     * {@link GeometricalObjectVisitor} visitor.
     * 
     * @param v
     *            {@link GeometricalObjectVisitor} visitor
     */
    public abstract void accept(GeometricalObjectVisitor v);

    /**
     * Creates new {@link GeometricalObjectEditor} of the current
     * {@link GeometricalObject}.
     * 
     * @return new {@link GeometricalObjectEditor} editor.
     */
    public abstract GeometricalObjectEditor createGeometricalObjectEditor();

    /**
     * Adds new {@link GeometricalObjectListener} to list of listeners.
     * 
     * @param l
     *            new listener
     */
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    /**
     * Removes the given {@link GeometricalObjectListener} listener from the list of
     * listeners.
     * 
     * @param l
     *            listener which will be removed
     */
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.remove(l);
    }

    /**
     * Send notification to all of its listeners.
     */
    protected void fireListeners() {
        for (GeometricalObjectListener l : listeners) {
            l.geometricalObjectChanged(this);
        }
    }

    /**
     * Method returns the {@link String} representation of the
     * {@link GeometricalObject} which will be used in exporting and importing the
     * objects.
     * 
     * @return {@link String} representation of the {@link GeometricalObject}
     */
    public abstract String export();
    
    /**
     * Constructs the {@link GeometricalObject} from the line.
     * @param line line from jvd file
     * @return {@link GeometricalObject} imported
     */
    public static GeometricalObject construct(String line) {
        
        if(line.startsWith(LINE)) {
            return new Line(line);
        } else if(line.startsWith(CIRCLE)){
            return new Circle(line);
        } else if(line.startsWith(FCIRCLE)) {
            return new FilledCircle(line);
        } else if(line.startsWith("FPOLY")) {
            return new Polygon(line);
        }else throw new RuntimeException("Error");
        
        
    }

}
