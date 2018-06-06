package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link Swap} implements {@link Functions} which takes two values
 * from top of the stack and replaces them, or replace their at the 
 * top of the stack.
 * 
 * @author dario
 *
 */
public class Swap implements Functions {

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        ValueWrapper a = stack.pop();
        ValueWrapper b = stack.pop();
        stack.push(a);
        stack.push(b);
    }

}
