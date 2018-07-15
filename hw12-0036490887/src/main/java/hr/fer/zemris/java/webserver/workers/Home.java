package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * @author dario
 *
 */
public class Home implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        
        String bgColor = context.getPersistentParameter("bgcolor");
        if(bgColor == null) {
            bgColor = "7F7F7F";
        }
        context.setTemporaryParameter("background", bgColor);
        
        context.getDispatcher().dispatchRequest("/private/home.smscr");
    }

}
