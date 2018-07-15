package hr.fer.zemris.java.hw16.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.objects.Polygon;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

public class AddingPolygon extends MainState{

    public AddingPolygon(DrawingModel model, IColorProvider foregroundColor, IColorProvider backgroundColor) {
        super(model, foregroundColor, backgroundColor);
    }
    
    
    private List<Point> points = new ArrayList<>();
    
    private Point currentPoint;
    
    
    @Override
    public void mousePressed(MouseEvent e) {

        System.out.println("MOUSE PRESSED");
        if(SwingUtilities.isRightMouseButton(e)) {
            points.clear();
            return;
        }
        if(points.size() >= 3 && distance(currentPoint, points.get(points.size()-1)) <= 3) {
            model.add(new Polygon(points.size(), new ArrayList<>(points), foregroundColor.getCurrentColor(), backgroundColor.getCurrentColor()));
            points.clear();
            return;
        }
        
        if(!valid(e.getPoint())) {
            JOptionPane.showMessageDialog(null, "Poligon nije konveksan, pokušajte ponovno.");
            //points.remove(points.size()-1);
            return;
        }
        
        currentPoint = e.getPoint();
        
        
        
        if(points.size() > 0 && points.size() <= 3) {
            if(distance(points.get(points.size()-1), currentPoint) <= 3) {
            } else {
                points.add(currentPoint);
            }
        } else {
            points.add(currentPoint);
        }
        System.out.println(points.size());
        
    }
    private boolean valid(Point newPoint) {
        
        if(points.size() < 3) return true;
        int val = 0;
        for(int i=0;i<points.size();++i) {
            int a1 = points.get((i+1)%points.size()).x - points.get(i).x;
            int a2 =points.get((i+1)%points.size()).y - points.get(i).y;
            int b1 = points.get((i+2)%points.size()).x - points.get(i).x;
            int b2 =points.get((i+2)%points.size()).y - points.get(i).y;
            
            if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
            else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
            else return false;
        }
        
        int a1 = (newPoint.x - points.get(0).x);
        int a2 = (newPoint.y - points.get(0).y);
        int b1 = (newPoint.x - points.get(1).x);
        int b2 = (newPoint.y - points.get(1).y);
        
        if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
        else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
        else return false;
        
        a1 = (-points.get(points.size()-1).x + newPoint.x);
        a2 = (-points.get(points.size()-1).y + newPoint.y);
        b1 = (-points.get(points.size()-1).x + points.get(0).x);
        b2 = (-points.get(points.size()-1).y + points.get(0).y);
        
        if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
        else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
        else return false;
        
        
        b1 = (-points.get(points.size()-2).x + newPoint.x);
        b2 = (-points.get(points.size()-2).y + newPoint.y);
        a1 = (-points.get(points.size()-2).x + points.get(points.size()-1).x);
        a2 = (-points.get(points.size()-2).y + points.get(points.size()-1).y);
        
        if(a1*b2 - a2*b1 >= 0 && val >= 0) { val = a1*b2 - a2*b1;}
        else if(a1*b2 - a2*b1 <= 0 && val <= 0) {val = a1*b2 - a2*b1;}
        else return false;
        
        return true;
    }
    private int distance(Point f, Point s) {
        
        return (int)Math.sqrt((f.x-s.x)*(f.x-s.x) + (f.y-s.y)*(f.y-s.y));
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        currentPoint = e.getPoint();
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (points.size() >= 0) {
            List<Point> points2 = new ArrayList<>(points);
            points2.add(currentPoint);
            GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);
            Polygon polygon = new Polygon(points2.size(), points2, foregroundColor.getCurrentColor(), backgroundColor.getCurrentColor());
            painter.visit(polygon);
        }
    }

}
