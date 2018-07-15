package hr.fer.zemris.java.hw16.trazilica.processor.structures;

/**
 * Class {@link ResultDocument} implements {@link Comparable} and it compares
 * with each other looking at the result parameter. It is a encapsulated pair of
 * result value and the {@link Document} which perform that result. User can
 * access both of them using the get methods.
 * 
 * @author dario
 *
 */
public class ResultDocument implements Comparable<ResultDocument> {

    /**
     * result of comparison
     */
    private double result;
    /**
     * {@link Document} instance
     */
    private Document document;

    /**
     * Constructs {@link ResultDocument} with the {@link Document} instance and
     * result as the double value.
     * 
     * @param document
     *            , instance to the {@link Document}
     * @param result
     *            double value, used for comparison
     */
    public ResultDocument(Document document, double result) {

        this.document = document;
        this.result = result;
    }

    @Override
    public int compareTo(ResultDocument resultDocument) {
        return Double.compare(resultDocument.result, result);
    }

    /**
     * Returns the result value.
     * 
     * @return result value
     */
    public double getResult() {
        return this.result;
    }

    /**
     * Returns the {@link Document} value.
     * 
     * @return {@link Document} value
     */
    public Document getDocument() {
        return this.document;
    }

    @Override
    public String toString() {
        return String.format("(%.4f) %s", result, document.getFilePath().toAbsolutePath());
    }
}
