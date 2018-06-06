package hr.fer.zemris.java.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import hr.fer.zemris.java.web.objects.BendResult;
import hr.fer.zemris.java.web.objects.Bend;

@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
    
    private static final String VOTING_RESULT = "/WEB-INF/glasanje-rezultati.txt";
    private static final String VOTE_DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
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
        
        DefaultKeyedValues2DDataset dataset = new DefaultKeyedValues2DDataset();
        for(BendResult result : results) {
            
            
            dataset.setValue(result.getNumOfVotes(),"broj glasova",result.getBend().getName());
        }
        
        JFreeChart chart = ChartFactory.createBarChart( 
                "Rezultati glasanja",
                "Bendovi",
                "Broj glasova",
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
