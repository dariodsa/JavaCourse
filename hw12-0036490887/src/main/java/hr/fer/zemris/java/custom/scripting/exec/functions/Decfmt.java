package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link Decfmt} implements {@link Functions} which 
 * takes decimal format and double value from the stack. It 
 * prepares double value and like that pushes it on the 
 * stack like that.
 * @author dario
 *
 */
public class Decfmt implements Functions {

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        String format = (String) stack.pop().getValue();
        Object x = stack.pop().getValue();
        Double value = 0.0;
        if(x instanceof Number) {
            value = ((Number)x).doubleValue();
        } else if(x instanceof String){
            value = Double.parseDouble((String)x);
        }
        
        DecimalFormat decimalFormat = new DecimalFormat(format);

        String valueString = decimalFormat.format(value);
        
        stack.push(new ValueWrapper(valueString));
    }

}
