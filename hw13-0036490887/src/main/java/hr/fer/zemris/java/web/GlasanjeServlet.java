package hr.fer.zemris.java.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.web.files.FileHandler;
import hr.fer.zemris.java.web.objects.Bend;

/**
 * Class {@link GlasanjeServlet} extends {@link HttpServlet} and sets it's link
 * to /glasanje. It reads bend definition file and send it to the
 * glasanjeIndex.jsp file.
 * 
 * @author dario
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
       
        List<Bend> bends = FileHandler.getInstance(req.getServletContext()).getBends();

        req.setAttribute("bends", bends);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
