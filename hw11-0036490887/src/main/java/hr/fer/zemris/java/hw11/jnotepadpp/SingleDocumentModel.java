package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import javax.swing.JTextArea;

/**
 * Interface {@link SingleDocumentModel} specifies what should every single
 * document model specify and implement. Such as changing the modification
 * property, or getting text component.
 * 
 * @see DefaultSingleDocumentModel
 * @author dario
 *
 */
public interface SingleDocumentModel {

    /**
     * Returns {@link JTextArea}, which is a text component of
     * {@link SingleDocumentModel}.
     * 
     * @return object's text component
     */
    JTextArea getTextComponent();

    /**
     * Returns file path from which text component reads its content and location on
     * which new content will be saved.
     * 
     * @return current file path, can be null if is a new document
     */
    Path getFilePath();

    /**
     * Sets new file path to the {@link SingleDocumentModel}. See more details in
     * {@link #getFilePath()}.
     * 
     * @param path
     *            new file path
     */
    void setFilePath(Path path);

    /**
     * Returns true if the object was modified, false otherwise.
     * 
     * @return true if an object was modified, false otherwise
     */
    boolean isModified();

    /**
     * Sets new value of the object's property modified.
     * 
     * @param modified
     *            new value of modified value
     */
    void setModified(boolean modified);

    /**
     * Adds new {@link SingleDocumentListener} to the list of all listeners.
     * 
     * @param l
     *            new {@link SingleDocumentListener} listener
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes given {@link SingleDocumentListener} listener from the list of all
     * listeners.
     * 
     * @param l
     *            listener which wants to unsubscribed from subject's notification.
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
