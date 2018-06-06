package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link TparamSet} implements {@link Functions} which takes name and
 * value from the stack and sets the new temporary parameter with the given
 * name and value.
 * 
 * @author dario
 *
 */
public class TparamSet implements Functions {

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        String name = stack.pop().getValue().toString();
        String value = stack.pop().getValue().toString();
        
        
        requestContext.setTemporaryParameter(name.toString(), value.toString());
        
    }

}
