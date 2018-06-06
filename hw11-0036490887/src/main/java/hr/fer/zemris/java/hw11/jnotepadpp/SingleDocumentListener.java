package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface {@link SingleDocumentModel} specifies what should every listeners
 * on the {@link SingleDocumentModel} as the subject implement and what method
 * should subject fire when it gets new state.
 * 
 * @author dario
 *
 */
public interface SingleDocumentListener {

    /**
     * Method from which subject says to the listener that the current document's
     * modified status was changed.
     * 
     * @param model
     *            modified {@link SingleDocumentModel} document
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Method from which subject says to the listener that the current document's
     * path was changed.
     * 
     * @param model
     *            modified {@link SingleDocumentModel} document
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
