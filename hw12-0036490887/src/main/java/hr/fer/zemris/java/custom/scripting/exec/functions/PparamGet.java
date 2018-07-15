package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link PparamGet} implements {@link Functions} which takes 
 * defValue and name from the stack. If there is persistent parameter with the given 
 * name it will print given result otherwise it will print defValue.
 * @author dario
 *
 */
public class PparamGet implements Functions{

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        Object defValue = stack.pop().getValue();
        String name = stack.pop().getValue().toString();
        
        
        String value = requestContext.getPersistentParameter(name);
        
        stack.push(new ValueWrapper(value == null? defValue : value));
    }

}
