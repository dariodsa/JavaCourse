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
 * Class {@link EditPanelServlet} extends {@link HttpServlet} and shows list of
 * entries available for editing.
 * 
 * @author dario
 *
 */

@WebServlet("/servleti/editing")
public class EditPanelServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String user = req.getAttribute("user").toString();

        if (req.getSession().getAttribute("current.user.nick") == null) {
            resp.setStatus(401);
        }
        if (user == null || user.compareTo(req.getSession().getAttribute("current.user.nick").toString()) != 0) {
            System.out.println("Nije usao.");
            resp.setStatus(401);
        } else {
            BlogUser blogUser = DAOProvider.getDAO().getUser(user);
            req.setAttribute("user", user);
            req.setAttribute("entries", blogUser.getEntries());

            req.getRequestDispatcher("/WEB-INF/pages/editList.jsp").forward(req, resp);
        }
    }
}
