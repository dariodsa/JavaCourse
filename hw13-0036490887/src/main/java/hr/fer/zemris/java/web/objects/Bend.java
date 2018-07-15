package hr.fer.zemris.java.web.objects;

/**
 * Class {@link Bend} encapsulate values specific for bends.
 * It consists of bend's id, name and some link to their song.
 * @author dario
 *
 */
public class Bend implements Comparable<Bend> {
    /**
     * bend id
     */
    private int id;
    /**
     * bend name
     */
    private String name;
    /**
     * link on some song
     */
    private String link;
    /**
     * 
     * @param id bend's id
     * @param name name of the bend
     * @param link link on the song
     */
    public Bend(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }
    /**
     * Returns id of the bend.
     * @return bend's id
     */
    public int getId() {
        return id;
    }
    /**
     * Returns name of the bend.
     * @return bend's name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns link of the bend.
     * @return bend's link
     */
    public String getLink() {
        return link;
    }
    @Override
    public int compareTo(Bend bend) {
        return Integer.compare(id, bend.id);
    }
    
}
