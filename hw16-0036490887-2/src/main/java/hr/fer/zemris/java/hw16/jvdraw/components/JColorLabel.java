package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;

/**
 * Class {@link JColorLabel} extends {@link JLabel} and implements
 * {@link ColorChangeListener} which listens to the given color provider and
 * sets text to show the current status.
 * 
 * @author dario
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * foreground color provider
     */
    private JColorArea fgColorArea;
    
    /**
     * background color provider
     */
    private JColorArea bgColoArea;

    /**
     * current foreground color
     */
    private Color foregroundColor = new Color(0, 0, 0);
    
    /**
     * current background color
     */
    private Color backgroundColor = new Color(0, 0, 0);

    /**
     * Constructs {@link JColorLabel} with the given color provider which will this
     * object listen.
     * 
     * @param fgColorArea
     *            foreground color provider
     * @param bgColorArea
     *            background color provider
     */
    public JColorLabel(JColorArea fgColorArea, JColorArea bgColorArea) {

        this.fgColorArea = fgColorArea;
        this.bgColoArea = bgColorArea;
        
        foregroundColor = fgColorArea.getCurrentColor();
        backgroundColor = bgColorArea.getCurrentColor();
        
        fgColorArea.addColorChangeListener(this);
        bgColorArea.addColorChangeListener(this);
        
        setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).",
                foregroundColor.getRed(), foregroundColor.getGreen(), foregroundColor.getBlue(),
                backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
    }

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {

        if (source == fgColorArea) {
            foregroundColor = newColor;
        } else if (source == bgColoArea) {
            backgroundColor = newColor;
        }
        setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).",
                foregroundColor.getRed(), foregroundColor.getGreen(), foregroundColor.getBlue(),
                backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
    }
}
