package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link SumWorker} implements {@link IWebWorker} and prints html code in
 * which is the summation of the parameters a and b which was received in the
 * get method.
 * 
 * @author dario
 *
 */
public class SumWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        ValueWrapper a = new ValueWrapper(Integer.valueOf(1));
        ValueWrapper b = new ValueWrapper(Integer.valueOf(2));
        
        System.out.println("uso");
        try {
            String strValueA = context.getParameter("a");
            System.out.println("value a" + strValueA);
            if(strValueA != null) {
                Double.parseDouble(strValueA);
                a.setValue(a.perform(strValueA));
            }
            
        } catch (NumberFormatException ex) {
            
        }
        
        try {
            String strValueB = context.getParameter("b");
            if(strValueB != null) {
                Double.parseDouble(strValueB);
                b.setValue(b.perform(strValueB));
            }
        } catch (NumberFormatException ex) {
           
        }
        
        
        context.setTemporaryParameter("a", a.getValue().toString());
        context.setTemporaryParameter("b", b.getValue().toString());
        try {
            a.add(b.getValue());
        } catch(Exception e) {
            e.printStackTrace();
            
        }
        System.out.println(a.getValue());
        context.setTemporaryParameter("zbroj", a.getValue().toString());

        context.getDispatcher().dispatchRequest("/private/calc.smscr");
    }

}
