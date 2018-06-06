package hr.fer.zemris.java.gui.prim;
import javax.swing.JList;

import org.junit.*;

public class PrimTest {
    @Test
    public void testFirstPrimeNum() {
	PrimListModel model = new PrimListModel();
	model.next();
	Assert.assertEquals(1, model.getElementAt(0).intValue());
    }
    @Test
    public void testSecondPrimeNum() {
	PrimListModel model = new PrimListModel();
	model.next();
	model.next();
	Assert.assertEquals(2, model.getElementAt(1).intValue());
    }
    @Test
    public void testThirdPrimeNum() {
	PrimListModel model = new PrimListModel();
	model.next();model.next();model.next();
	
	Assert.assertEquals(3, model.getElementAt(2).intValue());
    }
    @Test
    public void testFourthPrimeNum() {
	PrimListModel model = new PrimListModel();
	
	model.next();
	model.next();model.next();model.next();
	
	Assert.assertEquals(5, model.getElementAt(3).intValue());
    }
    @Test
    public void testListenerInteractionSameSize() {
	PrimListModel model = new PrimListModel();
	
	JList<Integer> list = new JList<>(model);
	for(int i=0;i<100;++i) model.next();
	
	Assert.assertEquals(100, list.getModel().getSize());
    }
    @Test
    public void testListenerInteraction() {
	PrimListModel model = new PrimListModel();
	
	JList<Integer> list = new JList<>(model);
	model.next();
	model.next();
	
	Assert.assertEquals(2, list.getModel().getSize());
	Assert.assertEquals(1, list.getModel().getElementAt(0).intValue());
	Assert.assertEquals(2, list.getModel().getElementAt(1).intValue());
    }
    
}
