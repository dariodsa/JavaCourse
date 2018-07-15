package hr.fer.zemris.java.hw16.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Class {@link AddingLine} extends {@link MainState} and represents state in
 * which user can add new lines to the drawing model and see them on the drawing
 * canvas. User can see dynamically draw components.
 * 
 * @author dario
 *
 */
public class AddingLine extends MainState {

    /**
     * current internal state
     */
    private int state = 0;
    /**
     * first point
     */
    private Point firstPoint;

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
    public AddingLine(DrawingModel model, IColorProvider foregroundColor, IColorProvider backgroundColor) {
        super(model, foregroundColor, backgroundColor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (state == 0) {
            firstPoint = e.getPoint();
            currentPoint = e.getPoint();
            state = 1;
        } else if (state == 1) {
            Point lastPoint = e.getPoint();

            model.add(new Line(firstPoint, lastPoint, foregroundColor.getCurrentColor()));
            state = 0;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentPoint = e.getPoint();
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (state == 1) {
            GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);

            Line line = new Line(firstPoint, currentPoint, foregroundColor.getCurrentColor());
            painter.visit(line);
        }
    }

}
