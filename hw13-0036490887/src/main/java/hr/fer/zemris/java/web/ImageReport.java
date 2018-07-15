package hr.fer.zemris.java.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Class {@link ImageReport} extends {@link HttpServlet} sets it's link to
 * /reportImage. After that it will make a {@link PieDataset} using dataset from
 * OS usage. It will set content type to image/png and send image of that pie
 * datasets.
 * 
 * @author dario
 *
 */
@WebServlet("/reportImage")
public class ImageReport extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = "OS usage";
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Android", 54.16);
        dataset.setValue("iOS/macOS", 12.37);
        dataset.setValue("Windows", 11.79);
        dataset.setValue("Other", 21.66);

        JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
        BufferedImage image = chart.createBufferedImage(900, 500);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bas);
        } catch (Exception ignorable) {
        }

        resp.setContentType("image/png");
        resp.getOutputStream().write(bas.toByteArray());
    }
}
