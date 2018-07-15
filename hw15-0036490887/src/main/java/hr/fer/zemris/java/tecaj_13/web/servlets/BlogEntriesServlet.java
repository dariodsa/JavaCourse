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
 * Class {@link BlogEntriesServlet} extends {@link HttpServlet} and loads list of 
 * entries and redirects to the jsp which will show it.
 * @author dario
 *
 */
@WebServlet("/servleti/authors")
public class BlogEntriesServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getAttribute("user").toString();
        
        BlogUser blogUser = DAOProvider.getDAO().getUser(userName);
        
        req.setAttribute("entries", blogUser.getEntries());
        
        req.setAttribute("user", userName);
        req.getRequestDispatcher("/WEB-INF/pages/entries-list.jsp").forward(req, resp);
        
    }
}
