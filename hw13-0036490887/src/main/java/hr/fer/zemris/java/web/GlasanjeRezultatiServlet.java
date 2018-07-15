package hr.fer.zemris.java.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.web.files.FileHandler;
import hr.fer.zemris.java.web.objects.BendResult;

/**
 * Class {@link GlasanjeRezultatiServlet} extends {@link HttpServlet}
 * ands sets its link to glasanje-rezultati. It loads the bend results 
 * and sets them as the attribute to the glasanjeRez.jsp so 
 * that jsp can shown them to the user in browser.
 * @author dario
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        List<BendResult> results = FileHandler.getInstance(req.getServletContext()).getBendsResults();
        Collections.sort(results);
        req.setAttribute("results", results);
        
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
    
}
