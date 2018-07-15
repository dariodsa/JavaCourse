package hr.fer.zemris.java.web;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
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
=======
import hr.fer.zemris.java.web.objects.Bend;
import hr.fer.zemris.java.web.objects.BendResult;

@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
    /**
     * voting result path
     */
    private static final String VOTING_RESULT = "/WEB-INF/glasanje-rezultati.txt";
    /**
     * voting definition path
     */
    private static final String VOTE_DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath(VOTING_RESULT);
        
        Path file = Paths.get(fileName);
        if(!Files.exists(file)) {
            Files.createFile(file);
        }
        
        String bendDef = req.getServletContext().getRealPath(VOTE_DEFINITION_FILE);
        Path bendDefPath = Paths.get(bendDef);
        
        if(!Files.exists(bendDefPath)) {
            Files.createFile(bendDefPath);
        }
       
        
        List<Bend> bends = new ArrayList<>();
        
        List<String> liness = Files.readAllLines(bendDefPath);
        for(String line : liness) {
            String[] splitted = line.split("\\t");
            
            int id = Integer.parseInt(splitted[0]);
            String name = splitted[1];
            String link = splitted[2];
            
            bends.add(new Bend(id, name, link));
        }
        
        List<String> lines = Files.readAllLines(file);
        List<BendResult> results = new ArrayList<BendResult>();
        for(String line : lines) {
            String[] splitted = line.split("\\t");
            int id = Integer.parseInt(splitted[0]);
            int numOfVotes = Integer.parseInt(splitted[1]);
            boolean found = false;
            for(Bend bend : bends) {
                if(bend.getId() == id) {
                    results.add(new BendResult(bend, numOfVotes));
                    found = true;
                }
            }
            if(!found) {
                
            }
        }
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
        Collections.sort(results);
        req.setAttribute("results", results);
        
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
    
}
