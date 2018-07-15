package hr.fer.zemris.java.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.web.files.FileHandler;

/**
 * Class {@link ServerTimeListener} implements {@link ServletContextListener}
 * and sets current time in milliseconds  in the servlet context as 
 * attribute. 
 * @author dario
 *
 */
@WebListener
public class ServerTimeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        long currentTime = System.currentTimeMillis();
        FileHandler.getInstance(sce.getServletContext());
        sce.getServletContext().setAttribute("time", currentTime);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    

}
