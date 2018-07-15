package hr.fer.zemris.java.hw17.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@link SmallPictureServlet} extends {@link HttpServlet} and 
 * creates the small image if they are not exist. Dimension of a small image 
 * will be 150 pixel times 150 pixels and servlet will save them in the thumbnail folder 
 * located in the WEB-INF folder. 
 * @author dario
 *
 */
@WebServlet("/createsmallpicture")
public class SmallPictureServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * parameter name image name
     */
    private static final String IMAGE_NAME = "imageName";
    
    /**
     * height of a smaller image
     */
    private static final int HEIGHT = 150;
    
    /**
     * width of a smaller image 
     */
    private static final int WIDTH = 150;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Path folderPath = Paths.get(getServletContext().getRealPath("WEB-INF/thumbnails"));
        if(!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        
        String imageName = req.getParameter(IMAGE_NAME).toString();
        
        if(imageName.isEmpty() || req.getParameter(IMAGE_NAME) == null) {
            resp.setStatus(404);
            return;
        }
        
        Path imagePath = Paths.get(folderPath.toString(), imageName.toString());
        String extension = imageName.substring(imageName.lastIndexOf('.')+1);
        
        if(!Files.exists(imagePath)) {
            Path bigPicture = Paths.get(getServletContext().getRealPath("WEB-INF/slike"), imageName);
            System.out.println(bigPicture.toString());
            
            BufferedImage bim = ImageIO.read(bigPicture.toFile());
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2d = image.createGraphics();
            
            g2d.drawImage(bim, 0, 0, WIDTH, HEIGHT, null);
            
            g2d.dispose();
            ImageIO.write(image, extension, imagePath.toFile());
        }
        resp.setStatus(200);
        resp.setContentType("image/" + extension);
        resp.getOutputStream().write(Files.readAllBytes(imagePath));
        resp.getOutputStream().flush();
    }
}
