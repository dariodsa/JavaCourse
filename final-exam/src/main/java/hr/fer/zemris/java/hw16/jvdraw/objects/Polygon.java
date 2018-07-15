package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.editing.GeometricalObjectEditor;

public class Polygon extends GeometricalObject {

    private int numberOfPoints;
    private List<Point> points;
    private Color lineColor;
    private Color fillColor;
    
    
    public Polygon(int numberOfPoints, List<Point> points, Color lineColor, Color fillColor) {
        super();
        this.numberOfPoints = numberOfPoints;
        this.points = points;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }
private int distance(Point f, Point s) {
        
        return (int)Math.sqrt((f.x-s.x)*(f.x-s.x) + (f.y-s.y)*(f.y-s.y));
    }
    public Polygon(String line) {
        //line = line.substring(0, line.length()-1);
        
        points = new ArrayList<>();
        String[] comp = line.split(" +");
        numberOfPoints = Integer.parseInt(comp[1]);
        if(numberOfPoints < 3) throw new RuntimeException("error");
        int k =2;
        for(int i=0;i<numberOfPoints;++i) {
            int x = Integer.parseInt(comp[k]);
            ++k;
            int y = Integer.parseInt(comp[k]);
            points.add(new Point(x,y));
            ++k;
        }
        for(int i=0;i<points.size()-1;++i) {
            
            if(distance(points.get(i), points.get(i+1)) <= 3) {
                throw new RuntimeException("Error");
            }
               
            
        }
        if(distance(points.get(0), points.get(points.size()-1)) <= 3) {
            throw new RuntimeException("Error");
        }
        
        int red = Integer.parseInt(comp[k]);
        int green = Integer.parseInt(comp[k+1]);
        int blue = Integer.parseInt(comp[k+2]);

        setLineColor(new Color(red, green, blue));
        
        red = Integer.parseInt(comp[k+3]);
        green = Integer.parseInt(comp[k+4]);
        blue = Integer.parseInt(comp[k+5]);
        setFillColor(new Color(red, green, blue));
    }
    
    public int getNumberOfPoints() {
        return numberOfPoints;
    }



    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Color getLineColor() {
        return lineColor;
    }



    public Color getFillColor() {
        return fillColor;
    }



    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        
        return new PolygonEditor();
    }

    
    @Override
    public String toString() {
        StringBuilder bob = new StringBuilder();
        for(int i=0;i<numberOfPoints;++i) {
            bob.append(points.get(i).x +" " + points.get(i).y + " ");
        }
        
        return String.format("Polygon %d %s , %s %s", numberOfPoints, bob.toString(),
                String.format("#%02x%02x%02x", lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue()),
                String.format("#%02x%02x%02x", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
    }
    
    
    
    @Override
    public String export() {
        StringBuilder bob = new StringBuilder();
        bob.append("FPOLY " + numberOfPoints +" ");
        for(Point point : points) {
            bob.append(point.x +" " + point.y +" ");
        }
        bob.append(String.format("%d %d %d ", lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue()));
        bob.append(String.format("%d %d %d", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
        return bob.toString();
    }
    
    private class PolygonEditor extends GeometricalObjectEditor {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private JTextField[] pointsX;
        private JTextField[] pointsY;
        
        private JColorArea lineColor;
        private JColorArea fillColor;

        /**
         * Constructs the {@link CircleEditor} in which user can edit circle properties
         */
        public PolygonEditor() {
            setLayout(new GridLayout(0, 2));
            
            
            pointsX = new JTextField[numberOfPoints];
            pointsY = new JTextField[numberOfPoints];
            
            for(int i=0;i<numberOfPoints;++i) {
                pointsX[i] = new JTextField(Integer.valueOf(points.get(i).x).toString());
                pointsY[i] = new JTextField(Integer.valueOf(points.get(i).y).toString());
            }
            
            lineColor = new JColorArea(Polygon.this.lineColor);
            fillColor = new JColorArea(Polygon.this.fillColor);

            for(int i=0;i<numberOfPoints;++i) {
                add(new JLabel("Točka x " +(i+1) +"."));
                add(pointsX[i]);
                add(new JLabel("Točka y " +(i+1) +"."));
                add(pointsY[i]);
            }
            
            
            add(new JLabel("Boja linije"));
            add(lineColor);
            add(new JLabel("Boja punjenja"));
            add(fillColor);
        }
        private List<Point> points2 = new ArrayList<>();
        @Override
        public void checkEditing() {
            points2.clear();
            try {
                for(int i=0;i<pointsX.length;++i) {
                    Integer.parseInt(pointsX[i].getText());
                    Integer.parseInt(pointsY[i].getText());
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
            for(int i=0;i<pointsX.length;++i) {
                int x = Integer.parseInt(pointsX[i].getText());
                int y = Integer.parseInt(pointsY[i].getText());
                points2.add(new Point(x, y));
            }
            if(!valid(points2.get(0))) {
                throw new RuntimeException("Nije konveksan");
            }
            for(int i=0;i<points2.size()-1;++i) {
                
                if(distance(points2.get(i), points2.get(i+1)) <= 3) {
                    throw new RuntimeException("Error");
                }
                   
                
            }
            if(distance(points2.get(0), points2.get(points2.size()-1)) <= 3) {
                throw new RuntimeException("Error");
            }
            
        }

        @Override
        public void acceptEditing() {
            for(int i=0;i<numberOfPoints;++i) {
                
                int x = Integer.parseInt(pointsX[i].getText());
                int y = Integer.parseInt(pointsY[i].getText());
                
                Polygon.this.points.set(i, new Point(x,y));
            }
            
            Polygon.this.fillColor = fillColor.getCurrentColor();
            Polygon.this.lineColor = lineColor.getCurrentColor();
            fireListeners();
        }
        
        
        private boolean valid(Point newPoint) {
            
            if(points2.size() < 3) return true;
            int val = 0;
            for(int i=0;i<points2.size();++i) {
                int a1 = points2.get((i+1)%points2.size()).x - points2.get(i).x;
                int a2 =points2.get((i+1)%points2.size()).y - points2.get(i).y;
                int b1 = points2.get((i+2)%points2.size()).x - points2.get(i).x;
                int b2 =points2.get((i+2)%points2.size()).y - points2.get(i).y;
                
                if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
                else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
                else return false;
            }
            
            int a1 = (newPoint.x - points2.get(0).x);
            int a2 = (newPoint.y - points2.get(0).y);
            int b1 = (newPoint.x - points2.get(1).x);
            int b2 = (newPoint.y - points2.get(1).y);
            
            if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
            else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
            else return false;
            
            a1 = (-points2.get(points2.size()-1).x + newPoint.x);
            a2 = (-points2.get(points2.size()-1).y + newPoint.y);
            b1 = (-points2.get(points2.size()-1).x + points2.get(0).x);
            b2 = (-points2.get(points2.size()-1).y + points2.get(0).y);
            
            if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
            else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
            else return false;
            
            b1 = (-points2.get(points2.size()-2).x + newPoint.x);
            b2 = (-points2.get(points2.size()-2).y + newPoint.y);
            a1 = (-points2.get(points2.size()-2).x + points2.get(points2.size()-1).x);
            a2 = (-points2.get(points2.size()-2).y + points2.get(points2.size()-1).y);
            
            if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
            else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
            else return false;
            
            
            return true;
        }
        private int distance(Point f, Point s) {
            
            return (int)Math.sqrt((f.x-s.x)*(f.x-s.x) + (f.y-s.y)*(f.y-s.y));
        }
    }
}
