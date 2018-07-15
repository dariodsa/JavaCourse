package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link TparamDel} implements {@link Functions} which takes name from 
 * the stack and removes the temporary parameter with the given name.
 * 
 * @author dario
 *
 */
public class TparamDel implements Functions {

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        String name = stack.pop().getValue().toString();
        
        
        requestContext.removeTemporaryParameter(name.toString());
    }

}
