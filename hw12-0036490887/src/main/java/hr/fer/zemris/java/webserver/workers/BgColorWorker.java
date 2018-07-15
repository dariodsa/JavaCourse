package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link BgColorWorker} implements {@link IWebWorker} which 
 * job is to say was the change of the background color was successful 
 * and it offers it link on which user can click and see it.
 * @author dario
 *
 */
public class BgColorWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String bgColor = context.getParameter("bgcolor");
        if(bgColor != null && check(bgColor)) {
            context.setPersistentParameter("bgcolor", bgColor);
            context.write("Color was updated!.<br>" +
                    "<a href=index2.html>Index2</a>");
        } else {
            context.write("Color was <b>not</b> updated!.<br>" +
                    "<a href=index2.html>Index2</a>");
        }
        
    }
    /**
     * Returns true if the string is in hex color specification.
     * @param bgColor string color
     * @return true if it is, false otherwise
     */
    private boolean check(String bgColor) {
        if(bgColor.length() != 6) {
            return false;
        }
        for(int i =0;i<bgColor.length(); ++i) {
            if(bgColor.charAt(i) >= '0' && bgColor.charAt(i) <= '9') {
                continue;
            }
            if(bgColor.charAt(i) >= 'a' && bgColor.charAt(i) <= 'f') {
                continue;
            }
            if(bgColor.charAt(i) >= 'A' && bgColor.charAt(i) <= 'F') {
                continue;
            }
            return false;
        }
        return true;
    }

}
