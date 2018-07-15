package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link SetMimeType} implements {@link Functions} which takes
 * mime type from the top of the stack and sets the mime type of the 
 * current {@link RequestContext}.
 * @author dario
 *
 */
public class SetMimeType implements Functions{

    @Override
    public void apply(Stack<ValueWrapper> stack, RequestContext requestContext) {
        String mimeType = (String) stack.pop().getValue();
        requestContext.setMimeType(mimeType);
    }

}
