package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class {@link VotingResults} extends {@link HttpServlet} gets poll results
 * from {@link DAO} sets it as attribute so that user can see it later and then
 * makes redirect to the /WEB-INF/pages/glasanje-rezultati.jsp which will render
 * results of a poll.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class VotingResults extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pollId;
        
        if (req.getParameter("pollID") == null) {
            pollId = -1;
        } else {
            pollId = Long.parseLong(req.getParameter("pollID"));
        }
        
        List<PollOptions> results = DAOProvider.getDao().getPollOptions(pollId);

        Collections.sort(results);
        
        req.setAttribute("pollID", pollId);
        req.setAttribute("pollOptions", results);
        req.setAttribute("poll", DAOProvider.getDao().getPoll(pollId));

        req.getRequestDispatcher("/WEB-INF/pages/glasanje-rezultati.jsp").forward(req, resp);
    }
}
