package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link Sin} implements {@link Functions} which takes one
 * value from the stack, calculate sin value from it and push it 
 * on the stack.
 * @author dario
 *
 */
public class Sin implements Functions{

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
       
        double val = ((Number)stack.pop().getValue()).doubleValue();
        double result = Math.sin(val * (Math.PI / 180.0f));
        stack.push(new ValueWrapper(Double.valueOf(result)));
    }

}
