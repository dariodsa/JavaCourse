package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link CircleWorker} draws green circle on the screen, and writes it 
 * on the context output stream.
 * @author dario
 *
 */
public class CircleWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
        System.out.println("Circle");
        Graphics2D g2d = bim.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillOval(100, 100, 100, 100);
        g2d.dispose();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bim, "png", bos);
            context.setMimeType("image/png");
            context.write(bos.toByteArray());
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
