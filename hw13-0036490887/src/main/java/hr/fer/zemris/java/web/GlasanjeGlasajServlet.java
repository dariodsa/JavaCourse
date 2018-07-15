package hr.fer.zemris.java.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.web.files.FileHandler;
import hr.fer.zemris.java.web.objects.BendResult;

/**
 * Class {@link GlasanjeGlasajServlet} extends {@link HttpServlet}
 * and sets it's link to glasanje-glasaj. It checks the bend id and increase
 * number of votes for it and resets it in the file. If the id is wrong or 
 * broken it will set status code 500.
 * @author dario
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id == null) {
            resp.setStatus(500);
            return;
        }
        try {
            int bandId = Integer.parseInt(id);
            //make a vote
            boolean found = false;
            List<BendResult> bendResult = FileHandler.getInstance(req.getServletContext()).getBendsResults();
            for(BendResult result : bendResult) {
                if(result.getBend().getId() == bandId) {
                    result.addOneVote();
                    found = true;
                }
            }
            if(!found) {
                resp.setStatus(500);
            }
            FileHandler.getInstance(req.getServletContext()).setNewResults(bendResult);
            resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
            
        } catch(NumberFormatException ex) {
            resp.sendError(500);
        }
    }
}
