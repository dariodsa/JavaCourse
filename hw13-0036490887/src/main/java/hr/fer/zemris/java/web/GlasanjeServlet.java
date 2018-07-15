package hr.fer.zemris.java.web;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
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
=======
import hr.fer.zemris.java.web.objects.Bend;

@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
    private String VOTE_DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath(VOTE_DEFINITION_FILE);
        Path file = Paths.get(fileName);
        
        if(!Files.exists(file)) {
            Files.createFile(file);
        }
        //dalje ...
        List<Bend> bends = new ArrayList<>();
        
        List<String> lines = Files.readAllLines(file);
        for(String line : lines) {
            String[] splitted = line.split("\\t");
            //System.out.println(splitted[0] + " " +splitted[1] + splitted[2]);
            int id = Integer.parseInt(splitted[0]);
            String name = splitted[1];
            String link = splitted[2];
            
            bends.add(new Bend(id, name, link));
        }
        
        req.setAttribute("bends", bends);
        
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
        
        //resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
        
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
    }
}
