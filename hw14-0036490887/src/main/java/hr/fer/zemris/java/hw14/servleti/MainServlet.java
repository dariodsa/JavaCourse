package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * {@link MainServlet} extends {@link HttpServlet} gets list of available polls
 * from {@link DAO} sets it as attribute and redirects user to
 * WEB-INF/pages/index.jsp.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/index.html")
public class MainServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Poll> polls = DAOProvider.getDao().getPolls();

        System.out.println(polls.size());

        req.setAttribute("polls", polls);

        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }
}
