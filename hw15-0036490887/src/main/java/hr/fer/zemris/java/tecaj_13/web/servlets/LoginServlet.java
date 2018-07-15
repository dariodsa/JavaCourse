package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.util.Util;

/**
 * Class {@link LoginServlet} extends {@link HttpServlet} and shows main page to
 * the user on which one can login in or register.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/main")
public class LoginServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<BlogUser> blogUsers = DAOProvider.getDAO().getUsers();

        if (req.getParameter("error") != null) {
            req.setAttribute("error", req.getParameter("error"));
        }
        System.out.println("DOSAO");
        req.setAttribute("blogUsers", blogUsers);
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("Primio parametre.");
        System.out.println(Util.calculateHash("pero"));
        System.out.println("Ispis tablice");
        /*
         * List<BlogUser> users = DAOProvider.getDAO().getUsers(); for(BlogUser user:
         * users) { System.out.println("tablica " + user.getNick() +" " +
         * user.getPasswordHash()); }
         */

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            String error = "Nisi unio korisnicko ime ili lozinku.";

            resp.sendRedirect(("main?error=" + error));
        }

        String newPassword = Util.calculateHash(password);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        BlogUser user = DAOProvider.getDAO().getBlogUser(username, newPassword);
        if (user != null) {
            req.getSession().setAttribute("current.user.id", user.getId());
            req.getSession().setAttribute("current.user.fn", user.getFirstName());
            req.getSession().setAttribute("current.user.ln", user.getLastName());
            req.getSession().setAttribute("current.user.nick", user.getNick());
            System.out.println("Session set.");
            System.out.println(req.getSession().getAttribute("current.user.nick"));
            resp.sendRedirect("main");

        } else {
            resp.setStatus(400);
            resp.sendRedirect("main?error=Prijava nije uspjela.");
        }

    }

}
