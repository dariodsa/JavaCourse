package hr.fer.zemris.java.web;

<<<<<<< HEAD
import java.io.IOException;
=======
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
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

=======
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id == null) {
<<<<<<< HEAD
            resp.setStatus(500);
=======
            //error
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
            return;
        }
        try {
            int bandId = Integer.parseInt(id);
            //make a vote
<<<<<<< HEAD
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
=======
            
            String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
            Path file = Paths.get(fileName);
            List<String> lines = Files.readAllLines(file); 
            Path path = Paths.get(fileName);
            BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(path.toFile()));
            for(String line : lines) {
                String[] splitted = line.split("\\t");
                try {
                    int br = Integer.parseInt(splitted[0]);
                    int num = Integer.parseInt(splitted[1]);
                    if(bandId == br) {
                        ++num;
                        bis.write(new String(bandId +"\t" + (num) + "\n").getBytes("utf-8"));
                        continue;
                    }
                } catch(Exception ignorable) {}
                bis.write((line+"\n").getBytes("utf-8"));
            }
            
            bis.close();
            
            resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
            
        } catch(NumberFormatException ex) {
            //error
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
        }
    }
}
