package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link EchoParams} implements {@link IWebWorker} and prints in table
 * parameters which page received in get method.
 * @author dario
 *
 */
public class EchoParams implements IWebWorker{

    @Override
    public void processRequest(RequestContext context) throws Exception {
        Set<String> paramsName = context.getParameterNames();
        context.write("<html><body>");
        context.write("<table border=1>");
        for(String name : paramsName) {
            context.write("<tr><td>"+name+"</td><td>"+context.getParameter(name) + "</td></tr>");
        }
        context.write("</table>");
        context.write("</body></html>");
    }

}
