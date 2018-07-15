package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.editing.GeometricalObjectEditor;

/**
 * Class {@link Line} extends {@link GeometricalObject} and implements all its
 * methods and have all of its necessary attributes like first point, last point,
 * color of the line. All attributes can be changed.
 * 
 * @author dario
 *
 */
public class Line extends GeometricalObject {

    /**
     * first point
     */
    private Point first;
    /**
     * last point
     */
    private Point last;
    /**
     * line color
     */
    private Color color;
    
    /**
     * Constructs the {@link Line} with all of it's attributes which can be later alter.
     * @param first first {@link Point}
     * @param last last {@link Point}
     * @param color {@link Color} of line
     */
    public Line(Point first, Point last, Color color) {
        this.first = first;
        this.last = last;
        this.color = color;
    }
    /**
     * Constructs {@link Line} from the {@link String} which is used in importing the geometrical data.
     * @param line line of String
     */
    public Line(String line) {
       //line = line.substring(0, line.length()-1);
        System.out.println("LINE: " + line);
       String[] comp = line.split(" +");
       
       int x1 = Integer.parseInt(comp[1]);
       int y1 = Integer.parseInt(comp[2]);
       int x2 = Integer.parseInt(comp[3]);
       int y2 = Integer.parseInt(comp[4]);
       
       int red = Integer.parseInt(comp[5]);
       int green = Integer.parseInt(comp[6]);
       int blue = Integer.parseInt(comp[7]);
       setFirst(new Point(x1, y1));
       setLast(new Point(x2, y2));
       setColor(new Color(red, green, blue));
    }
    
    /**
     * Returns the line's first point.
     * @return line's first point
     */
    public Point getFirst() {
        return this.first;
    }
    /**
     * Setst the line's first point
     * @param first first point, {@link Point}
     */
    public void setFirst(Point first) {
        this.first = first;
    }
    /**
     * Returns the line's last point. 
     * @return {@link Point}, last point
     */
    public Point getLast() {
        return this.last;
    }
    /**
     * Sets the last point of the line.
     * @param last line's last point
     */
    public void setLast(Point last) {
        this.last = last;
    }
    /**
     * Returns the line's color.
     * @return line's color
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * Sets the line color.
     * @param color new line color
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
        return new LineEditor();
    }
    
    @Override
    public String toString() {
        return String.format("Line (%d,%d)-(%d,%d)", first.x, first.y, last.x, last.y);
    }
    
    /**
     * Class {@link LineEditor} extends {@link GeometricalObjectEditor} 
     * which will render editing pane in which user will be able to change geometrical and visual 
     * characteristics of the object.
     * @author dario
     *
     */
    private class LineEditor extends GeometricalObjectEditor {


        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**
         * first point x component
         */
        private JTextField firstX = new JTextField();
        /**
         * first point y component
         */
        private JTextField firstY = new JTextField();
        /**
         * last point x component
         */
        private JTextField lastX = new JTextField();
        /**
         * last point y component
         */
        private JTextField lastY = new JTextField();
        
        /**
         * color component
         */
        private JColorArea color;
        /**
         * Constructs the {@link LineEditor} in which user can edit line's properties
         */
        public LineEditor() {
            setLayout(new GridLayout(0, 2));
            firstX.setText(String.valueOf(first.x));
            firstY.setText(String.valueOf(first.y));
            lastX.setText(String.valueOf(last.x));
            lastY.setText(String.valueOf(last.y));
            
            color = new JColorArea(Line.this.color);
            
            
            add(new JLabel("Prva točka x"));
            add(firstX);
            add(new JLabel("Prva točka y"));
            add(firstY);
            add(new JLabel("Zadnja točka x"));
            add(lastX);
            add(new JLabel("Zadnja točka y"));
            add(lastY);
            add(new JLabel("Boja linije"));
            add(color);
        }
        @Override
        public void checkEditing() {
            try {
                Integer.parseInt(firstX.getText());
                Integer.parseInt(firstY.getText());
                Integer.parseInt(lastX.getText());
                Integer.parseInt(lastY.getText());
                
            } catch(NumberFormatException ex) {
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
        }

        @Override
        public void acceptEditing() {
            int x1 = Integer.parseInt(firstX.getText());
            int y1 = Integer.parseInt(firstY.getText());
            int x2 = Integer.parseInt(lastX.getText());
            int y2 = Integer.parseInt(lastY.getText());
            
            Line.this.first = new Point(x1, y1);
            Line.this.last = new Point(x2, y2);
            
            Line.this.color = color.getCurrentColor();
            fireListeners();
        }
        
    }

    @Override
    public String export() {
        return String.format("LINE %d %d %d %d %d %d %d", 
                             first.x,
                             first.y,
                             last.x,
                             last.y, 
                             color.getRed(),
                             color.getGreen(),
                             color.getBlue());
    }

}
