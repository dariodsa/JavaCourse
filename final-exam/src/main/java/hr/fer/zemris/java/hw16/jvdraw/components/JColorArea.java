package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;

/**
 * Class {@link JColorArea} extends {@link JComponent} and implements
 * {@link IColorProvider} in which user can select new color of the given color
 * provider. They can cancel their operation by simply clicking the exit or
 * cancel button.
 * 
 * @author dario
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * width of a component
     */
    private static final int WIDTH = 15;
    /**
     * height of a component
     */
    private static final int HEIGHT = 15;
    /**
     * title of a JOptionPane
     */
    private static final String TITLE = "Odaberi boju";

    /**
     * initial color
     */
    private static final Color INIT_COLOR = new Color(0, 255, 0);
    /**
     * list of listeners
     */
    private List<ColorChangeListener> listeners = new ArrayList<>();
    /**
     * current color
     */
    private Color selectedColor = INIT_COLOR;
    /**
     * old color
     */
    private Color oldColor;

    /**
     * Constructs {@link JColorArea} and adds mouse listeners to it.
     */
    public JColorArea() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(null, TITLE, INIT_COLOR);
                if (newColor == null)
                    return;

                oldColor = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue());
                selectedColor = newColor;
                paintComponent(getGraphics());
                fireListeners();
            }
        });
    }

    /**
     * Constructs {@link JColorArea} with the given initial color.
     * 
     * @param color
     *            initial color
     */
    public JColorArea(Color color) {
        this();
        this.selectedColor = color;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(selectedColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    @Override
    public Color getCurrentColor() {
        return new Color(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue());
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies all listeners about color changes.
     */
    private void fireListeners() {
        for (ColorChangeListener l : listeners) {
            l.newColorSelected(this, oldColor, selectedColor);
        }
    }
}
