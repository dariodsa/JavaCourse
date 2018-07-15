package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.editing.GeometricalObjectEditor;

/**
 * Class {@link FilledCircle} extends {@link GeometricalObject} and implements
 * all its methods and have all of its necessary attributes like center point,
 * radius, color of the line and its background. All attributes can be changed.
 * 
 * @author dario
 *
 */
public class FilledCircle extends GeometricalObject {

    /**
     * center point
     */
    private Point center;
    /**
     * radius, int value
     */
    private int radius;
    /**
     * foreground color
     */
    private Color foreground;
    /**
     * background color
     */
    private Color background;

    /**
     * Constructs the {@link FilledCircle} with all of it's attributes.
     * 
     * @param center
     *            center point
     * @param radius
     *            radius of the circle
     * @param foreground
     *            foreground color
     * @param background
     *            background color
     */
    public FilledCircle(Point center, int radius, Color foreground, Color background) {
        this.center = center;
        this.radius = radius;
        this.foreground = foreground;
        this.background = background;
    }

    /**
     * Constructs {@link FilledCircle} from the {@link String} which is used in importing the geometric data.
     * @param line line of String
     */
    public FilledCircle(String line) {
        String[] comp = line.split(" +");
        int x1 = Integer.parseInt(comp[1]);
        int y1 = Integer.parseInt(comp[2]);
        int radius = Integer.parseInt(comp[3]);
        
        int red = Integer.parseInt(comp[4]);
        int green = Integer.parseInt(comp[5]);
        int blue = Integer.parseInt(comp[6]);
        setCenter(new Point(x1, y1));
        setRadius(radius);
        setForeground(new Color(red, green, blue));
        
        red = Integer.parseInt(comp[7]);
        green = Integer.parseInt(comp[8]);
        blue = Integer.parseInt(comp[9]);
        setBackground(new Color(red, green, blue));
    }
    
    /**
     * Returns the circle's center point.
     * 
     * @return circle's center point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Sets the circle's center point.
     * 
     * @param center
     *            circle's center point
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Returns the circle's radius.
     * 
     * @return circle's radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the circle's radius.
     * 
     * @param radius
     *            circle's radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Returns the foreground color.
     * 
     * @return foreground color
     */
    public Color getForeground() {
        return foreground;
    }

    /**
     * Setst the foreground color.
     * 
     * @param foreground
     *            foreground color
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    /**
     * Returns the background color.
     * 
     * @return background color
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Sets the background color.
     * 
     * @param background
     *            background color
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor();
    }

    @Override
    public String toString() {
        return String.format("Filled circle (%d,%d), %d, %s", center.x, center.y, radius,
                String.format("#%02x%02x%02x", background.getRed(), background.getGreen(), background.getBlue()));
    }

    /**
     * Class {@link FilledCircleEditor} extends {@link GeometricalObjectEditor}
     * which will render editing pane in which user will be able to change
     * geometrical and visual characteristics of the object.
     * 
     * @author dario
     *
     */
    private class FilledCircleEditor extends GeometricalObjectEditor {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**
         * center point x component
         */
        private JTextField centerX = new JTextField();
        /**
         * center point y component
         */
        private JTextField centerY = new JTextField();
        /**
         * radius component
         */
        private JTextField radiusText = new JTextField();
        /**
         * foreground component
         */
        private JColorArea foregroundColor;
        /**
         * background component
         */
        private JColorArea backgroundColor;

        /**
         * Constructs the {@link FilledCircleEditor} in which user can edit circle
         * properties
         */
        public FilledCircleEditor() {
            setLayout(new GridLayout(0, 2));
            centerX.setText(String.valueOf(center.x));
            centerY.setText(String.valueOf(center.y));
            radiusText.setText(String.valueOf(radius));

            foregroundColor = new JColorArea(FilledCircle.this.foreground);
            backgroundColor = new JColorArea(FilledCircle.this.background);

            add(new JLabel("Centar x"));
            add(centerX);
            add(new JLabel("Centar y"));
            add(centerY);
            add(new JLabel("Polumjer"));
            add(radiusText);
            add(new JLabel("Boja linije"));
            add(foregroundColor);
            add(new JLabel("Boja punjenja"));
            add(backgroundColor);
        }

        @Override
        public void checkEditing() {
            try {
                Integer.parseInt(centerX.getText());
                Integer.parseInt(centerY.getText());
                Integer.parseInt(radiusText.getText());
            } catch (NumberFormatException ex) {
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
        }

        @Override
        public void acceptEditing() {
            int x1 = Integer.parseInt(centerX.getText());
            int y1 = Integer.parseInt(centerY.getText());
            int radius = Integer.parseInt(radiusText.getText());

            center = new Point(x1, y1);
            FilledCircle.this.radius = radius;

            FilledCircle.this.foreground = foregroundColor.getCurrentColor();
            FilledCircle.this.background = backgroundColor.getCurrentColor();
            fireListeners();
        }
    }

    @Override
    public String export() {
        return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d",
                             center.x,
                             center.y,
                             radius,
                             foreground.getRed(),
                             foreground.getGreen(),
                             foreground.getBlue(),
                             background.getRed(),
                             background.getGreen(),
                             background.getBlue());
    }

}
