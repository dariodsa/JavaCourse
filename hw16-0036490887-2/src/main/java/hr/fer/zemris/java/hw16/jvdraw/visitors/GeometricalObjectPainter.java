package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

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

}
