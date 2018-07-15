package hr.fer.zemris.java.hw16.jvdraw.objects;

/**
 * Interface {@link GeometricalObjectVisitor} is a part of the design patter
 * visitor and it is its responsibility to list all objects which can call this
 * methods. One class have its own method.
 * 
 * @author dario
 *
 */
public interface GeometricalObjectVisitor {
    /**
     * Handles what will happen with the {@link Line}.
     * 
     * @param line
     *            line object
     */
    public void visit(Line line);

    /**
     * Handles what will happen with the {@link Line}.
     * 
     * @param circle
     *            circle object
     */
    public void visit(Circle circle);

    /**
     * Handles what will happen with the {@link FilledCircle}.
     * 
     * @param filledCircle
     *            filled circle object
     */
    public void visit(FilledCircle filledCircle);

    public void visit(Polygon polygon);

}
