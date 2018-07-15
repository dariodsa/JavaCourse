package hr.fer.zemris.java.hw16.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModel;


/**
 * Abstract class which implements {@link Tool} and specifies what should every
 * state have in its constructor.
 * 
 * @author dario
 *
 */
public abstract class MainState implements Tool {

    /**
     * drawing model
     */
    protected DrawingModel model;
    /**
     * foreground color
     */
    protected IColorProvider foregroundColor;
    /**
     * background color
     */
    protected IColorProvider backgroundColor;

    /**
     * Constructs {@link MainState} with necessary references to all more explicit
     * states.
     * 
     * @param model
     *            drawing model
     * @param foregroundColor
     *            foreground color provider
     * @param backgroundColor
     *            background color provider
     */
    public MainState(DrawingModel model, IColorProvider foregroundColor, IColorProvider backgroundColor) {
        this.model = model;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void paint(Graphics2D g2d) {
    }
}
