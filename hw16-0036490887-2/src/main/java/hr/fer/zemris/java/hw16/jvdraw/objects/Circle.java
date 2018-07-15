package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.editing.GeometricalObjectEditor;

/**
 * Class {@link Circle} extends {@link GeometricalObject} and implements all its
 * methods and have all of its necessary attributes like center point, radius,
 * color of the line. All attributes can be changed.
 * 
 * @author dario
 *
 */
public class Circle extends GeometricalObject {

    /**
     * center point
     */
    private Point center;
    /**
     * radius , int value
     */
    private int radius;
    /**
     * foreground color of the circle
     */
    private Color color;

    /**
     * Constructs the {@link Circle} with all of its given properties which user can
     * later change.
     * 
     * @param center
     *            center point
     * @param radius
     *            radius length
     * @param color
     *            foreground color
     */
    public Circle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Constructs {@link Circle} from the {@link String} which is used in importing the geometric data.
     * @param line line of String
     */
    public Circle(String line) {
        String[] comp = line.split(" +");
        int x1 = Integer.parseInt(comp[1]);
        int y1 = Integer.parseInt(comp[2]);
        int radius = Integer.parseInt(comp[3]);
        
        
        int red = Integer.parseInt(comp[4]);
        int green = Integer.parseInt(comp[5]);
        int blue = Integer.parseInt(comp[6]);
        setCenter(new Point(x1, y1));
        setRadius(radius);
        setColor(new Color(red, green, blue));
    }
    
    
    /**
     * Returns the center poitn of the circle.
     * 
     * @return center {@link Point} of the circle
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Sets the circle's center point.
     * 
     * @param center
     *            new center point
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
     * Sets the circle's radius
     * 
     * @param radius
     *            circle's radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Returns the circle's color.
     * 
     * @return circle's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the circle's color.
     * 
     * @param color
     *            circle's color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor();
    }

    @Override
    public String toString() {
        return String.format("Circle (%d,%d), %d", center.x, center.y, radius);
    }

    /**
     * Class {@link CircleEditor} extends {@link GeometricalObjectEditor} which will
     * render editing pane in which user will be able to change geometrical and
     * visual characteristics of the object.
     * 
     * @author dario
     *
     */
    private class CircleEditor extends GeometricalObjectEditor {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**
         * center x component
         */
        private JTextField centerX = new JTextField();
        /**
         * center y component
         */
        private JTextField centerY = new JTextField();
        /**
         * radius component
         */
        private JTextField radiusText = new JTextField();

        /**
         * color component
         */
        private JColorArea color;

        /**
         * Constructs the {@link CircleEditor} in which user can edit circle properties
         */
        public CircleEditor() {
            setLayout(new GridLayout(0, 2));
            centerX.setText(String.valueOf(center.x));
            centerY.setText(String.valueOf(center.y));
            radiusText.setText(String.valueOf(radius));

            color = new JColorArea(Circle.this.color);

            add(new JLabel("Centar x"));
            add(centerX);
            add(new JLabel("Centar y"));
            add(centerY);
            add(new JLabel("Polumjer"));
            add(radiusText);
            add(new JLabel("Boja linije"));
            add(color);
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
            Circle.this.radius = radius;

            Circle.this.color = color.getCurrentColor();
            fireListeners();
        }

    }

    @Override
    public String export() {
        return String.format("CIRCLE %d %d %d %d %d %d", 
                             center.x,
                             center.y,
                             radius,
                             color.getRed(),
                             color.getGreen(),
                             color.getBlue());
    }

}
