package hr.fer.zemris.java.hw16.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Class {@link AddingCircle} extends {@link MainState} and it represents the
 * state in which user can add new circles to the drawing model which will be
 * drawn on the drawing canvas.
 * 
 * @author dario
 *
 */
public class AddingCircle extends MainState {

    /**
     * current internal state
     */
    private int stanje = 0;
    /**
     * center {@link Point}
     */
    private Point center;
    /**
     * current mouse point
     */
    private Point currentPoint;

    /**
     * Default constructor which delegates all to its parent class.
     * 
     * @param model
     *            drawing model
     * @param foregroundColor
     *            foreground color provider
     * @param backgroundColor
     *            background color provider
     */
    public AddingCircle(DrawingModel model, IColorProvider foregroundColor, IColorProvider backgroundColor) {
        super(model, foregroundColor, backgroundColor);

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (stanje == 0) {
            center = e.getPoint();
            currentPoint = e.getPoint();
            stanje = 1;
        } else if (stanje == 1) {
            Point point = e.getPoint();

            int radius = (int) Math
                    .sqrt((point.x - center.x) * (point.x - center.x) + (point.y - center.y) * (point.y - center.y));
            model.add(new Circle(center, radius, foregroundColor.getCurrentColor()));

            stanje = 0;

        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        currentPoint = e.getPoint();
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (stanje == 1) {
            GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);
            int radius = (int) Math.sqrt((currentPoint.x - center.x) * (currentPoint.x - center.x)
                    + (currentPoint.y - center.y) * (currentPoint.y - center.y));
            Circle circle = new Circle(center, radius, foregroundColor.getCurrentColor());
            painter.visit(circle);
        }
    }

}
