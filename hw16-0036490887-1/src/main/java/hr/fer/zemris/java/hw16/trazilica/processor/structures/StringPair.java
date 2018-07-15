package hr.fer.zemris.java.hw16.trazilica.processor.structures;

/**
 * Class {@link StringPair} encapsulate pair of the string value and it's
 * frequency of the occurrence in the text. User can get text and increase
 * frequency using the appropriate methods.
 * 
 * @author dario
 *
 */
public class StringPair {
    /**
     * text value
     */
    private String text;

    /**
     * frequency of the text
     */
    private int frequency;

    /**
     * Constructs {@link StringPair} with the text as it0s main property and sets
     * frequency to the one.
     * 
     * @param text
     *            text property
     */
    public StringPair(String text) {

        this.text = text.toLowerCase();
        this.frequency = 1;
    }

    /**
     * Returns the text property.
     * 
     * @return text property
     */
    public String getText() {
        return text;
    }

    /**
     * Increase frequency of the object by one.
     */
    public void addFrequency() {
        this.frequency++;
    }

    /**
     * Returns the object's frequency.
     * 
     * @return object's frequency
     */
    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return text + String.format(" Frequency: %3d", frequency);
    }
}
