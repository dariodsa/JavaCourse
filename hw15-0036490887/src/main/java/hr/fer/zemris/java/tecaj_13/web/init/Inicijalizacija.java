package hr.fer.zemris.java.tecaj_13.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.util.Util;

/**
 * Class {@link Inicijalizacija} extends {@link ServletContextListener} and sets
 * all necessary things when the server is booting, like init of thread pool and
 * connections.
 * 
 * @author dario
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
        sce.getServletContext().setAttribute("my.application.emf", emf);
        JPAEMFProvider.setEmf(emf);

        BlogUser mainUser = new BlogUser();
        mainUser.setFirstName("Dario");
        mainUser.setLastName("Sindicic");
        mainUser.setPasswordHash(Util.calculateHash("pero"));
        mainUser.setEmail("dario.sindicic@fer.hr");
        mainUser.setNick("dario-dsa");
        // DAOProvider.getDAO().addNewUser(mainUser);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAEMFProvider.setEmf(null);
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
        if (emf != null) {
            emf.close();
        }
    }
}