package hr.fer.zemris.java.hw16.jvdraw.objects.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.states.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Class {@link JDrawingCanvas} extends {@link JComponent} and implements
 * {@link DrawingModelListener} because it listens the changes on the drawing
 * model's and calls repaint method from the {@link JComponent}.
 * 
 * @author dario
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * current state
     */
    private Tool currentState;
    /**
     * drawing model
     */
    private DrawingModel model;
    /**
     * drawing visitor
     */
    private GeometricalObjectVisitor drawingVisitor;

    /**
     * Constructs {@link JDrawingCanvas} with the given {@link DrawingModel} model.
     * 
     * @param model
     *            drawing model
     */
    public JDrawingCanvas(DrawingModel model) {
        model.addDrawingModelListener(this);
        this.model = model;
        setVisible(true);
        setUpStates();
    }

    /**
     * Returns current state.
     * 
     * @return current state
     */
    public Tool getCurrentState() {
        return this.currentState;
    }

    /**
     * Sets the new current state
     * 
     * @param currentState
     *            new current state
     */
    public void setCurrentState(Tool currentState) {
        this.currentState = currentState;
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel sourlce, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }

    /**
     * Adds mouse listeners.
     */
    private void setUpStates() {

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                currentState.mouseReleased(e);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentState.mousePressed(e);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                currentState.mouseClicked(e);
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                currentState.mouseMoved(e);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                currentState.mouseDragged(e);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        this.drawingVisitor = new GeometricalObjectPainter((Graphics2D) g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1500, 1500);

        for (int i = 0; i < model.size(); ++i) {
            model.getObject(i).accept(drawingVisitor);
        }
        currentState.paint((Graphics2D) g);

    }
}
