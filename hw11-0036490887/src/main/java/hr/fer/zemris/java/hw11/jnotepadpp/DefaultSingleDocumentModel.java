package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Font;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class {@link DefaultSingleDocumentModel} implements
 * {@link SingleDocumentModel} and specifies which listeners listen this object.
 * Allows user to change current path to the document and also to get it. You
 * can ask it if it was modified, or not, that is important when you change disk
 * colors from green to red. User can add or remove listeners to this object,
 * but firing to them is under objects responsibility so it is private method.
 * 
 * @author dario
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * path of the single document model, null by default, for new document
     */
    private Path path = null;

    /**
     * {@link JTextArea}, text component
     */
    private JTextArea area;

    /**
     * list of listeners to the {@link SingleDocumentModel}
     */
    private List<SingleDocumentListener> listeners = new ArrayList<>();

    /**
     * boolean value, is the document modified and unsaved
     */
    private boolean modified;

    /**
     * Constructs {@link DefaultSingleDocumentModel} with current path to the
     * document and String value which will be shown in the text component. If the
     * path is null, document is new, otherwise it was previously saved so it is
     * loaded from memory.
     * 
     * @param path
     *            path to the document memory location
     * @param text
     *            text value at the begining
     */
    public DefaultSingleDocumentModel(Path path, String text) {
        this.path = path;
        this.area = new JTextArea(text);
        this.area.setFont(new Font("monospaced", Font.PLAIN, 17));
        // TODO
        area.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent event) {
                setModified(true);
                fireModificationChanged();
            }

            @Override
            public void insertUpdate(DocumentEvent event) {
                setModified(true);
                fireModificationChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                setModified(true);
                fireModificationChanged();
            }
        });
    }

    @Override
    public JTextArea getTextComponent() {
        return area;
    }

    @Override
    public Path getFilePath() {
        return path;
    }

    @Override
    public void setFilePath(Path path) {
        Objects.requireNonNull(path);

        this.path = path;
        fireDocumentFilePathChanged();
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        fireModificationChanged();
    }

    /**
     * Notifies all listeners that the current document's text component was
     * changed.
     */
    private void fireModificationChanged() {
        for (SingleDocumentListener listener : listeners) {
            listener.documentModifyStatusUpdated(this);
        }
    }

    /**
     * Notifies all listeners that the current document's path was changed.
     */
    private void fireDocumentFilePathChanged() {
        for (SingleDocumentListener listener : listeners) {
            listener.documentFilePathUpdated(this);
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        Objects.requireNonNull(l);

        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        Objects.requireNonNull(l);

        this.listeners = new ArrayList<>(listeners);
        listeners.remove(l);
    }

}
