package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.objects.Polygon;

/**
 * Class {@link GeometricalObjectPainter} implements
 * {@link GeometricalObjectVisitor} and it will drawn a each object on calling
 * method visit.
 * 
 * @author dario
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    /**
     * graphics component
     */
    private Graphics2D g2d;

    /**
     * Constructs {@link GeometricalObjectPainter} with the {@link Graphics2D}
     * reference on which it will draw geometrical objects given in the methods
     * below.
     * 
     * @param g2d
     *            reference to canvas graphics
     */
    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {

        this.g2d.setColor(line.getColor());
        this.g2d.drawLine(line.getFirst().x, line.getFirst().y, line.getLast().x, line.getLast().y);
    }

    @Override
    public void visit(Circle circle) {

        g2d.setColor(circle.getColor());
        g2d.drawOval(circle.getCenter().x - circle.getRadius(), circle.getCenter().y - circle.getRadius(),
                circle.getRadius() * 2, circle.getRadius() * 2);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        g2d.setColor(filledCircle.getForeground());
        g2d.drawOval(filledCircle.getCenter().x - filledCircle.getRadius(),
                filledCircle.getCenter().y - filledCircle.getRadius(), filledCircle.getRadius() * 2,
                filledCircle.getRadius() * 2);

        g2d.setColor(filledCircle.getBackground());
        g2d.fillOval(filledCircle.getCenter().x - filledCircle.getRadius(),
                filledCircle.getCenter().y - filledCircle.getRadius(), filledCircle.getRadius() * 2,
                filledCircle.getRadius() * 2);
    }

    @Override
    public void visit(Polygon polygon) {
        
        int[] xPoints = new int[polygon.getNumberOfPoints()];
        int[] yPoints = new int[polygon.getNumberOfPoints()];
        
        for(int i=0;i<xPoints.length;++i) {
            xPoints[i] = polygon.getPoints().get(i).x;
            yPoints[i] = polygon.getPoints().get(i).y;
        }
        g2d.setColor(polygon.getLineColor());
        g2d.drawPolygon(xPoints, yPoints, polygon.getNumberOfPoints());
        g2d.setColor(polygon.getFillColor());
        g2d.fillPolygon(xPoints, yPoints, polygon.getNumberOfPoints());
    }

}
