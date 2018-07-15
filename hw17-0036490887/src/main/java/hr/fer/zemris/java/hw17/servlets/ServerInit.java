package hr.fer.zemris.java.hw17.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class {@link ServerInit} is a web listener that runs when the server is
 * starting. It will set servlet context that will user need when he will try to 
 * access text file with tags and picture information.
 * 
 * @author dario
 *
 */
@WebListener
public class ServerInit implements ServletContextListener {

    /**
     * servlet context
     */
    private static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        servletContext = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    /**
     * Returns the current {@link ServletContext servlet context}.
     * 
     * @return {@link ServletContext}
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }
}