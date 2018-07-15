package hr.fer.zemris.java.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

<<<<<<< HEAD
import hr.fer.zemris.java.web.files.FileHandler;

=======
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
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
<<<<<<< HEAD
        FileHandler.getInstance(sce.getServletContext());
=======
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
        sce.getServletContext().setAttribute("time", currentTime);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    

}
