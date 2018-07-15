package hr.fer.zemris.java.hw16.servlets;

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

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

@WebServlet("/crtaj")
public class CrtajServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * parameter image name
     */
    private static final String IMAGE_NAME = "imageName";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String kod = req.getParameter("kod");
        if(kod == null) {
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
        
        JVDraw jvdraw = new JVDraw(1);
        System.out.println(kod);
        if(!jvdraw.importText(kod)) {
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
        
        Path bigPicture = jvdraw.getImage();
        
        String extension = "png";
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
