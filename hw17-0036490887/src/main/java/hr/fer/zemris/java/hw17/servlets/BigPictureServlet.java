package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
 * Class {@link BigPictureServlet} extends {@link HttpServlet} and loads the
 * picture under given name in their normal size.
 * 
 * @author dario
 *
 */
@WebServlet("/getbigpicture")
public class BigPictureServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * parameter image name
     */
    private static final String IMAGE_NAME = "imageName";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = req.getParameter(IMAGE_NAME).toString();
        Path bigPicture = Paths.get(getServletContext().getRealPath("WEB-INF/slike"), imageName);
        String extension = imageName.substring(imageName.lastIndexOf('.') + 1);
        if (!Files.exists(bigPicture)) {
            resp.setStatus(404);
            return;
        }
        resp.setStatus(200);
        resp.setContentType("image/" + extension);
        BufferedImage buffIm = ImageIO.read(bigPicture.toFile());
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        ImageIO.write(buffIm, extension, baos);
        resp.getOutputStream().write(baos.toByteArray());
    }

}
