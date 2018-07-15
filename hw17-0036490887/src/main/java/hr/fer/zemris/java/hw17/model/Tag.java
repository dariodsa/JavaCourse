package hr.fer.zemris.java.hw17.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class {@link Tag} encapsulate tag which will be used in sending JSON object
 * in the communication. User can access object's attributes with the standard
 * get methods. User can add {@link Picture} under this tag with
 * {@link #addPicture(Picture)}.
 * 
 * @author dario
 *
 */
public class Tag {
    
    /**
     * value of the text
     */
    private String text;
    
    /**
     * list of the pictures
     */
    private List<Picture> pictures;

    /**
     * Constructs the Tag with the given tag value.
     * 
     * @param text
     *            tag name, {@link String} value
     */
    public Tag(String text) {
        
        text = com.coverity.security.Escape.jsString(text);
        text = com.coverity.security.Escape.htmlText(text);
        this.text = text;
        this.pictures = new ArrayList<>();
    }

    /**
     * Returns the tag value.
     * 
     * @return tag value
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the list of {@link Picture} under this tag.
     * 
     * @return list of {@link Picture}
     */
    public List<Picture> getPictures() {
        return pictures;
    }

    /**
     * Adds one picture to the list of current pictures under current tag.
     * 
     * @param picture
     *            new picture
     */
    public void addPicture(Picture picture) {
        Objects.requireNonNull(picture);

        pictures.add(picture);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tag other = (Tag) obj;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

}
