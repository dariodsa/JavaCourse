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

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.hw17.model.Picture;
import hr.fer.zemris.java.hw17.model.Tag;

/**
 * Class {@link TagsServlet} returns the list of tags in the given text file.
 * User can also access picture data under given tag.
 * 
 * @author dario
 *
 */
@javax.ws.rs.Path("/tags")
public class TagsServlet {

    /**
     * Returns the list of all tags loaded from the text file.
     * 
     * @return list of {@link Tag}
     */
    @GET
    @Produces("application/json")
    public List<Tag> getListOfTags() {

        return getTags();
    }

    /**
     * Returns the {@link Picture} data under given tag name.
     * 
     * @param tagName
     *            tag name
     * @return {@link Response} picture data, or 400 http error code
     */
    @javax.ws.rs.Path("/pictures/{tagName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuote(@PathParam("tagName") String tagName) {
        List<Tag> tags = getTags();
        Optional<Tag> tag = tags.stream().filter((t) -> t.getText().compareTo(tagName) == 0).findFirst();
        if (tag.isPresent()) {

            JSONArray pictures = new JSONArray();

            for (Picture picture : tag.get().getPictures()) {
                pictures.put(picture.getName());
            }
            JSONObject result = new JSONObject();
            result.put("pictures", pictures);

            return Response.status(Status.OK).entity(result.toString()).build();

        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    /**
     * Returns the {@link List} of {@link Tag} which are loaded from
     * 
     * @return list of {@link Tag}
     */
    private List<Tag> getTags() {
        Path path = Paths.get(ServerInit.getServletContext().getRealPath("WEB-INF/opisnik.txt"));
        List<Tag> list = new ArrayList<>();

        List<String> lines;
        try {
            lines = Files.readAllLines(path);

            for (int i = 2, len = lines.size(); i < len; i += 3) {
                String[] line = lines.get(i).split(",");
                for (String tag : line) {
                    if (tag.trim().isEmpty()) {
                        continue;
                    }
                    Tag T = new Tag(tag.trim());
                    if (!list.contains(T)) {
                        list.add(T);
                    }
                    Picture picture = new Picture(lines.get(i - 2), lines.get(i - 1));
                    list.get(list.indexOf(T)).addPicture(picture);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
