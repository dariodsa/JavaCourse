package hr.fer.zemris.java.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
<<<<<<< HEAD
import java.util.Collections;
=======
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultKeyedValues2DDataset;

<<<<<<< HEAD
import hr.fer.zemris.java.web.files.FileHandler;
import hr.fer.zemris.java.web.objects.BendResult;


/**
 * Class {@link GlasanjeGrafika} extends {@link HttpServlet} and
 * sets its link to glasanje-grafika. It uses org.jree.chart.jFreeChart 
 * package to create charts from the bends results which will be shown in the 
 * image type, content type will be image/png. 
 * @author dario
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * chart title
     */
    private static final String CHART_TITLE = "Rezultati glasanja";
    /**
     * y axis title
     */
    private static final String Y_AXIS_TITLE = "Bendovi";
    /**
     * x axis title
     */
    private static final String X_AXIS_TITLE = "Broj glasova";
=======
import hr.fer.zemris.java.web.objects.BendResult;
import hr.fer.zemris.java.web.objects.Bend;

@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
    
    private static final String VOTING_RESULT = "/WEB-INF/glasanje-rezultati.txt";
    private static final String VOTE_DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
<<<<<<< HEAD
        List<BendResult> results = FileHandler.getInstance(req.getServletContext()).getBendsResults();
        
        Collections.sort(results);
=======
        List<BendResult> results = new ArrayList<>();
        List<Bend> bends = new ArrayList<>();
        
        String bendDef = req.getServletContext().getRealPath(VOTE_DEFINITION_FILE);
        Path bendDefPath = Paths.get(bendDef);
        
        
        List<String> lines = Files.readAllLines(bendDefPath);
        for(String line : lines) {
            String[] splitted = line.split("\\t");
            
            int id = Integer.parseInt(splitted[0]);
            String name = splitted[1];
            String link = splitted[2];
            
            bends.add(new Bend(id, name, link));
        }
        
        String fileName = req.getServletContext().getRealPath(VOTING_RESULT);
        
        Path file = Paths.get(fileName);
        
        lines = Files.readAllLines(file);
        
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
                //error
            }
        }
        
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
        DefaultKeyedValues2DDataset dataset = new DefaultKeyedValues2DDataset();
        for(BendResult result : results) {
            
            
<<<<<<< HEAD
            dataset.setValue(result.getNumOfVotes(),X_AXIS_TITLE,result.getBend().getName());
        }
        
        JFreeChart chart = ChartFactory.createBarChart( 
                CHART_TITLE,
                Y_AXIS_TITLE,
                X_AXIS_TITLE,
=======
            dataset.setValue(result.getNumOfVotes(),"broj glasova",result.getBend().getName());
        }
        
        JFreeChart chart = ChartFactory.createBarChart( 
                "Rezultati glasanja",
                "Bendovi",
                "Broj glasova",
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false);
        
        BufferedImage image = chart.createBufferedImage(900, 500);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bas);
        } catch(Exception ignorable) {}
        
        resp.setContentType("image/png");
        resp.getOutputStream().write(bas.toByteArray());
    }
}
