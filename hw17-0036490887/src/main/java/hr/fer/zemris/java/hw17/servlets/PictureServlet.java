package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.hw17.model.Picture;

/**
 * Class {@link PictureServlet} handles the requests about pictures. It returns
 * the image description if the image name is provided.
 * 
 * @author dario
 *
 */
@javax.ws.rs.Path("/picture")
public class PictureServlet {

    /**
     * Returns the title of a given image.
     * 
     * @param imageName
     *            name of the image
     * @return {@link Response} image's title or 400 error code
     */
    @javax.ws.rs.Path("/getTitle/{imageName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuote(@PathParam("imageName") String imageName) {
        
        List<Picture> pictures = getPictures();
        Optional<Picture> picture = pictures.stream().filter((t) -> t.getName().compareTo(imageName) == 0).findFirst();
        
        if (picture.isPresent()) {

            JSONObject result = new JSONObject();
            result.put("title", picture.get().getText());
            return Response.status(Status.OK).entity(result.toString()).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    /**
     * Returns the list of {@link Picture} loaded from the text file.
     * 
     * @return list of loaded pictures
     */
    private List<Picture> getPictures() {
        Path path = Paths.get(ServerInit.getServletContext().getRealPath("WEB-INF/opisnik.txt"));
        List<Picture> list = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 0, len = lines.size(); i < len; i += 3) {
                Picture picture = new Picture(lines.get(i), lines.get(i + 1));
                list.add(picture);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
