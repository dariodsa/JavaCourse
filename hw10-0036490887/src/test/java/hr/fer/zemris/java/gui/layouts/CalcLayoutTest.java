package hr.fer.zemris.java.gui.layouts;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

public class CalcLayoutTest {
    @Test
    public void test() {
	//GridLayout
	JPanel p = new JPanel(new CalcLayout(2));
	JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
	JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
	p.add(l1, new RCPosition(2,2));
	p.add(l2, new RCPosition(3,3));
	Dimension dim = p.getPreferredSize();
	Assert.assertEquals(152, dim.width); //154 = 20 * 7 + 6*2
	Assert.assertEquals(158, dim.height); // 30 * 7 +
	
    }
    @Test
    public void test2() {
	JPanel p = new JPanel(new CalcLayout(2));
	JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
	JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
	p.add(l1, new RCPosition(1,1));
	p.add(l2, new RCPosition(3,3));
	Dimension dim = p.getPreferredSize();
	Assert.assertEquals(152, dim.width);
	Assert.assertEquals(158, dim.height);
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside1() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(1,0));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside2() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(-1,0));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside3() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(8,-1));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside4() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(7,9));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside5() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(1,2));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside6() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(1,3));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside7() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(1,4));
    }
    @Test (expected = CalcLayoutException.class)
    public void testOutside8() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(1,5));
    }
    @Test (expected = CalcLayoutException.class)
    public void testMultipleItemOnSamePlace() {
	JPanel p = new JPanel(new CalcLayout(2));
	p.add(new JLabel(""), new RCPosition(2,5));
	p.add(new JLabel(""), new RCPosition(2,5));
    }
}
