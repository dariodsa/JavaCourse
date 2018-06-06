package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface {@link MultipleDocumentListener} specifies what should every
 * listeners on the {@link MultipleDocumentModel} as the subject implement and
 * what method should subject fire when it gets new state.
 * 
 * @author dario
 *
 */
public interface MultipleDocumentListener {

    /**
     * Method from which subject says to the listener that the current document was
     * changed and it gives the previous one in the parameter.
     * 
     * @param previousModel
     *            previous {@link SingleDocumentModel}
     * @param currentModel
     *            current {@link SingleDocumentModel}
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Method from which subject says to the listener that the new document was
     * added.
     * 
     * @param model
     *            new {@link SingleDocumentModel} added
     */

    void documentAdded(SingleDocumentModel model);

    /**
     * Method from which subject says to the listener that the document was removed.
     * 
     * @param model
     *            {@link SingleDocumentModel} removed
     */
    void documentRemoved(SingleDocumentModel model);

}
