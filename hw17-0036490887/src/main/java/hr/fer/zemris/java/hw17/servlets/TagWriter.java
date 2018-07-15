package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONObject;

import hr.fer.zemris.java.hw17.model.Tag;

/**
 * Class {@link TagWriter} is responsible to return JSON representation of the
 * Tag object. It implements {@link MessageBodyWriter} parameterized with
 * {@link Tag}. It puts in under "tag" parameter.
 * 
 * @author dario
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class TagWriter implements MessageBodyWriter<Tag> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == Tag.class;
    }

    @Override
    public long getSize(Tag q, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return toData(q).length;
    }

    @Override
    public void writeTo(Tag q, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {

        entityStream.write(toData(q));
    }

    /**
     * Returns the byte array representation of JSON structure of {@link Tag}
     * object.
     * 
     * @param q
     *            {@link Tag}
     * @return byte array representation
     */
    private byte[] toData(Tag q) {
        String text;
        if (q == null) {
            text = "null;";
        } else {
            JSONObject result = new JSONObject();
            result.put("tag", q.getText());
            

            text = result.toString();
        }
        return text.getBytes(StandardCharsets.UTF_8);
    }
}
