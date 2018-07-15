package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class {@link AddOneVote} extends {@link HttpServlet} which adds one vote to
 * given option and forward the user to the glasanje-rezultati.
 * 
 * @author dario
 *
 */
@WebServlet("/servleti/vote")
public class AddOneVote extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long optionId = Long.parseLong(req.getParameter("optionID"));
        long pollId = Long.parseLong(req.getParameter("pollID"));
        DAOProvider.getDao().addOneVote(optionId);

        req.setAttribute("pollID", pollId);
        resp.sendRedirect("glasanje-rezultati?pollID=" + pollId);
        req.getRequestDispatcher("glasanje-rezultati").forward(req, resp);
    }

}
