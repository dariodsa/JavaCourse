package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Functions interface encapsulate all function which can be found in the .smsrc
 * file. All function must implements this interface so that they can be called
 * thanks to java reflection api.
 * 
 * @author dario
 *
 */
public interface Functions {
    /**
     * Applies function's specification on the stack and on the request context.
     * 
     * @param stack
     *            stack which will be used
     * @param requestContext
     *            request context, for setting mime, status, and others
     */
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext);

}
