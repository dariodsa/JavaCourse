package hr.fer.zemris.java.hw17.model;

/**
 * Class {@link Picture} encapsulate picture entity which has {@link #name} and
 * {@link #text} attributes. Attributes are read-only and user can access them
 * with standard get methods.
 * 
 * @author dario
 *
 */
public class Picture {
    /**
     * name of the picture
     */
    private String name;

    /**
     * text below the picture, description
     */
    private String text;

    /**
     * Constructs the {@link Picture} with the {@link String} name and
     * {@link String} text as the parameters.
     * 
     * @param name
     *            name of the picture
     * @param text
     *            text, description of the picture
     */
    public Picture(String name, String text) {
        
        text = com.coverity.security.Escape.jsString(text);
        text = com.coverity.security.Escape.htmlText(text);
        
        name = com.coverity.security.Escape.jsString(name);
        name = com.coverity.security.Escape.htmlText(name);
        
        this.name = name;
        this.text = text;
    }

    /**
     * Returns the picture's name.
     * 
     * @return {@link String} picture's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the picture's description.
     * 
     * @return {@link String} picture's description
     */
    public String getText() {
        return text;
    }

}
