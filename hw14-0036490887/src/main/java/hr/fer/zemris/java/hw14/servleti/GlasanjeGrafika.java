package hr.fer.zemris.java.hw14.servleti;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
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

import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.p12.dao.DAOProvider;


/**
 * Class {@link GlasanjeGrafika} extends {@link HttpServlet} and
 * sets its link to glasanje-grafika. It uses org.jree.chart.jFreeChart 
 * package to create charts from the bends results which will be shown in the 
 * image type, content type will be image/png. 
 * @author dario
 *
 */
@WebServlet("/servleti/glasanje-grafika")
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
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        long pollID = Long.parseLong(req.getParameter("pollID"));
        
        List<PollOptions> results = DAOProvider.getDao().getPollOptions(pollID);
        
        Collections.sort(results);
        DefaultKeyedValues2DDataset dataset = new DefaultKeyedValues2DDataset();
        for(PollOptions result : results) {
            
            
            dataset.setValue(result.getVotesCount(),X_AXIS_TITLE,result.getOptionTitle());
        }
        
        JFreeChart chart = ChartFactory.createBarChart( 
                CHART_TITLE,
                Y_AXIS_TITLE,
                X_AXIS_TITLE,
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
