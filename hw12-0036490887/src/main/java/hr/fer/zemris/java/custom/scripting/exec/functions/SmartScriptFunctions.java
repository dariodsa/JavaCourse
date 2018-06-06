package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link SmartScriptFunctions} prepares a function and runs one which
 * name will be got in apply method. It uses Java reflection API to run those
 * functions.
 * 
 * @author dario
 *
 */
public class SmartScriptFunctions {

    /**
     * Static function apply which executes given function.
     * 
     * @param functionName
     *            class name which will be created and called
     * @param stack
     *            stack
     * @param requestContext
     *            request context
     */
    public static void apply(String functionName, Stack<ValueWrapper> stack, RequestContext requestContext) {

        functionName = functionName.substring(0, 1).toUpperCase() + functionName.substring(1);

        try {
            @SuppressWarnings("unchecked")
            Class<Functions> clazz = (Class<Functions>) Class
                    .forName("hr.fer.zemris.java.custom.scripting.exec.functions." + functionName);
            Functions function = (Functions) clazz.getConstructor().newInstance();
            function.apply(stack, requestContext);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}
