package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class {@link RegisterServlet} extends {@link HttpServlet} and checks if the
 * register form was correct and adds user to database if it was.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        BlogUser newUser = new BlogUser(req);

        System.out.println(newUser.hasError());
        if (newUser.hasError()) {
            resp.sendRedirect("register?error=" + newUser.giveError());
        } else {

            long dupplicate = DAOProvider.getDAO().getUsers().stream()
                    .filter((u) -> u.getNick().compareTo(newUser.getNick()) == 0).count();

            if (dupplicate == 0) {
                DAOProvider.getDAO().addNewUser(newUser);
                resp.sendRedirect("main");
            } else {
                resp.sendRedirect("register?error=Korisnicko ime se vec koristi");
            }

        }
    }
}
