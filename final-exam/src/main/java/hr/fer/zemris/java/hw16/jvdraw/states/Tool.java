package hr.fer.zemris.java.hw16.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface {@link Tool} specifies what should each states have as its method
 * which can be called from the subject.
 * 
 * @author dario
 *
 */
public interface Tool {
    /**
     * It is called when the mouse is pressed.
     * @param e mouse event details 
     */
    public void mousePressed(MouseEvent e);

    /**
     * It is called when the mouse is released.
     * @param e mouse event details 
     */
    public void mouseReleased(MouseEvent e);

    /**
     * It is called when the mouse is clicked.
     * @param e mouse event details 
     */
    public void mouseClicked(MouseEvent e);

    /**
     * It is called when the mouse is moved.
     * @param e mouse event details 
     */
    public void mouseMoved(MouseEvent e);

    /**
     * It is called when the mouse is dragged.
     * @param e mouse event details 
     */
    public void mouseDragged(MouseEvent e);

    /**
     * It will be called after all components will be drawn on the {@link Graphics2D}. So after that
     * each state can draw its own thing on it.
     * @param g2d {@link Graphics2D} component on which will component be drawn 
     */
    public void paint(Graphics2D g2d);
}
