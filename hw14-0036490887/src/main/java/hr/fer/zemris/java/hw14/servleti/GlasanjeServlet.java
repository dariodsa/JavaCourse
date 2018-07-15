package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class {@link GlasanjeServlet} extends {@link HttpServlet} gets list of poll
 * option with given pollId from {@link DAO}, sets it as attributes and
 * redirects user to poll.jsp.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pollID = Long.parseLong(req.getParameter("pollID"));

        Poll poll = DAOProvider.getDao().getPoll(pollID);
        List<PollOptions> pollOptions = DAOProvider.getDao().getPollOptions(pollID);

        req.setAttribute("poll", poll);

        req.setAttribute("polloptions", pollOptions);

        req.getRequestDispatcher("/WEB-INF/pages/poll.jsp").forward(req, resp);
    }

}
