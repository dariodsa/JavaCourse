package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.objects.Polygon;

/**
 * Class {@link GeometricalObjectBBCalculator} implements
 * {@link GeometricalObjectVisitor} and it will calculate bounding box of all
 * geometrical object which will call method visit. method visit.
 * 
 * @author dario
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * minimal x value
     */
    private int minimalX = Integer.MAX_VALUE;
    /**
     * minimal y value
     */
    private int minimalY = Integer.MAX_VALUE;
    /**
     * maximal x value
     */
    private int maximalX = 0;
    /**
     * maximal y value
     */
    private int maximalY = 0;
    
    @Override
    public void visit(Line line) {
        calculate(line.getFirst(), line.getLast());
    }

    @Override
    public void visit(Circle circle) {
        Point point1 = new Point(
                circle.getCenter().x - circle.getRadius(), 
                circle.getCenter().y - circle.getRadius()
                );
        Point point2 = new Point(
                circle.getCenter().x + circle.getRadius(), 
                circle.getCenter().y + circle.getRadius()
                );
        calculate(point1, point2);
        
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        Point point1 = new Point(
                filledCircle.getCenter().x - filledCircle.getRadius(), 
                filledCircle.getCenter().y - filledCircle.getRadius()
                );
        Point point2 = new Point(
                filledCircle.getCenter().x + filledCircle.getRadius(), 
                filledCircle.getCenter().y + filledCircle.getRadius()
                );
        calculate(point1, point2);
    }
    /**
     * Updates the minimal and maximal values of the x and y component according to the 
     * given points
     * @param first first point
     * @param last second point
     */
    private void calculate(Point first, Point last) {
        minimalX = Math.min(minimalX, first.x);
        minimalY = Math.min(minimalY, first.y);
        minimalX = Math.min(minimalX, last.x);
        minimalY = Math.min(minimalY, last.y);
        
        maximalX = Math.max(maximalX, first.x);
        maximalY = Math.max(maximalY, first.y);
        maximalX = Math.max(maximalX, last.x);
        maximalY = Math.max(maximalY, last.y);
    }
    
    /**
     * Returns the calculated bounding box from called visit methods.
     * 
     * @return {@link Rectangle} bounding box
     */
    public Rectangle getBoundingBox() {
        int width = maximalX - minimalX;
        int height = maximalY - minimalY;
        return new Rectangle(minimalX, minimalY, width, height);
    }

    @Override
    public void visit(Polygon polygon) {
        for(Point point : polygon.getPoints()) {
            minimalX = Math.min(minimalX, point.x);
            minimalY = Math.min(minimalY, point.y);
            
            maximalX = Math.max(maximalX, point.x);
            maximalY = Math.max(maximalY, point.y);
        }
    }

}
