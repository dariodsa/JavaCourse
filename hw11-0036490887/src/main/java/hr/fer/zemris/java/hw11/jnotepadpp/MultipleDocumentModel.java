package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface {@link MultipleDocumentModel} specifies how should multiple
 * document handler handles all its document. It should provide methods like
 * {@link #createNewDocument()}, {@link #loadDocument(Path)},
 * {@link #saveDocument(SingleDocumentModel, Path)} other related with documents
 * like getting currently selected one, or number of them in the list. It is
 * also possible to add and remove listeners to the object.
 * 
 * @author dario
 *
 */
public interface MultipleDocumentModel {

    /**
     * Creates new document and adds it to list of existing one.
     * 
     * @return created new document
     */
    SingleDocumentModel createNewDocument();

    /**
     * Returns currently selected document.
     * 
     * @return currently selected document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads document from the given path and creates new tab with that document.
     * 
     * @param path
     *            path of the loaded document
     * @return document which was loaded from given path
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves document's content on given path and it changes model's path to the new
     * one.
     * 
     * @param model
     *            under which document will new file be saved, with its content
     * @param newPath
     *            new path to the document
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Removes given document from the list of documents, that tab with given
     * document will be closed.
     * 
     * @param model
     *            document which will be closed
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds {@link MultipleDocumentListener} listener to the list of all listeners.
     * 
     * @param l
     *            new listener, which will be added to the list
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes {@link MultipleDocumentListener} listener from the list of all
     * listeners.
     * 
     * @param l
     *            removed listeners
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Returns number of added document to the list.
     * 
     * @return number of document in the list
     */
    int getNumberOfDocuments();

    /**
     * Returns document which is located under specific index.
     * 
     * @param index
     *            index location in list of document
     * @return document at the given location
     */
    SingleDocumentModel getDocument(int index);
}
