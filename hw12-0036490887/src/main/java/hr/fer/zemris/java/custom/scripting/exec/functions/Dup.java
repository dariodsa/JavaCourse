package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link Dup} implements {@link Functions} and takes object from stack
 * and push it twice on the top.
 * 
 * @author dario
 *
 */
public class Dup implements Functions {

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        ValueWrapper top = stack.pop();

        stack.push(top);
        stack.push(top);
    }

}
