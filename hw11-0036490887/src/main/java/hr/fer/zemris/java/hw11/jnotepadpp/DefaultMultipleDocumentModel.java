package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class {@link DefaultMultipleDocumentModel} extends {@link JTabbedPane} amd
 * implements {@link MultipleDocumentModel} so it means that you can add it to
 * the JFrame because it is a {@link JComponent} also. User can add document
 * {@link #createNewDocument()}, {@link #saveDocument(SingleDocumentModel, Path)
 * save} or it can {@link #closeDocument(SingleDocumentModel) remove} from the
 * list. User can also ask how many there are documents in the list, or which
 * document is currently under focus.
 * 
 * @author dario
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * list of documents in the model
     */
    private List<SingleDocumentModel> documents = new ArrayList<>();
    /**
     * list of listeners to the object's state
     */
    private List<MultipleDocumentListener> listeners = new ArrayList<>();

    /**
     * Constructs {@link DefaultMultipleDocumentModel} object and adds
     * {@link ContainerListener} and {@link ChangeListener} to it.
     */
    public DefaultMultipleDocumentModel() {

        this.addContainerListener(new ContainerListener() {

            @Override
            public void componentRemoved(ContainerEvent event) {
                fireDocumentRemoved(null);
            }

            @Override
            public void componentAdded(ContainerEvent event) {

                SingleDocumentModel model = documents.get(documents.size() - 1);
                fireDocumentAdded(model);
            }
        });
        this.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent event) {
                SingleDocumentModel currentModel = null;
                if (getNumberOfDocuments() != 0) {
                    currentModel = getCurrentDocument();
                }
                SingleDocumentModel previousModel = null;
                if (getNumberOfDocuments() != 0) {
                    fireDocumentChanged(previousModel, currentModel);
                }
            }
        });
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
        documents.add(model);
        addNewTab(model);
        setTitleAt(getNumberOfDocuments() - 1, "New " + getNumberOfDocuments());
        model.getTextComponent().setText(" ");
        model.getTextComponent().setCaretPosition(1);
        model.getTextComponent().setCaretPosition(0);
        model.getTextComponent().setText("");

        return model;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return documents.get(getSelectedIndex());
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = new String(data, StandardCharsets.UTF_8);

        SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
        documents.add(model);

        addNewTab(model);
        setTitleAt(getNumberOfDocuments() - 1, path.getFileName().toString());
        setToolTipTextAt(getNumberOfDocuments() - 1, path.toString());

        return model;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if (newPath == null) {
            newPath = model.getFilePath();
        }
        try {
            Files.write(newPath, model.getTextComponent().getText().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.setModified(false);
        model.setFilePath(newPath);
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        int index = documents.indexOf(model);
        documents.remove(index);
        remove(index);
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        this.listeners = new ArrayList<>(this.listeners);
        this.listeners.remove(l);

    }

    /**
     * Notifies all listeners that the current document in focus was changed.
     * 
     * @param previousModel
     *            previous model in focus
     * @param currentModel
     *            current model in focus
     */
    private void fireDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        for (MultipleDocumentListener l : listeners) {
            l.currentDocumentChanged(previousModel, currentModel);
        }
    }

    /**
     * Notifies all listeners that the document was removed.
     * 
     * @param model
     *            removed document
     */
    private void fireDocumentRemoved(SingleDocumentModel model) {
        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(model);
        }
    }

    /**
     * Notifies all listeners that the new document was added.
     * 
     * @param model
     *            new document
     */
    private void fireDocumentAdded(SingleDocumentModel model) {
        for (MultipleDocumentListener l : listeners) {
            l.documentAdded(model);
        }
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    /**
     * Adds new tab to the model, as well as new document to list. It sets focus and
     * sets all listeners which should wait for changes in the model state.
     * 
     * @param model
     *            new {@link SingleDocumentModel} model
     */
    private void addNewTab(SingleDocumentModel model) {

        JScrollPane pane = new JScrollPane(model.getTextComponent());

        add(pane);
        JPanel panel = new JPanel();

        JButton btnClose = new JButton("x");
        btnClose.setBorderPainted(false);
        btnClose.addActionListener((e) -> {
            remove(pane);
        });
        add(btnClose);

        panel.add(btnClose);
        // setTabComponentAt(getNumberOfDocuments() - 1, panel);
        try {
            ImageIcon icon = getImageFromInputStream(JNotepadPP.class.getClassLoader()
                    .getResourceAsStream("hr/fer/zemris/java/hw11/jnotepadpp/icons/green.png"));
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            setIconAt(getIndex(model), icon);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        setSelectedIndex(getNumberOfDocuments() - 1);
        model.getTextComponent().requestFocusInWindow();

        model.addSingleDocumentListener(new SingleDocumentListener() {

            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                ImageIcon icon = null;
                if (model.isModified()) {
                    try {
                        icon = getImageFromInputStream(JNotepadPP.class.getClassLoader()
                                .getResourceAsStream("hr/fer/zemris/java/hw11/jnotepadpp/icons/red.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        icon = getImageFromInputStream(JNotepadPP.class.getClassLoader()
                                .getResourceAsStream("hr/fer/zemris/java/hw11/jnotepadpp/icons/green.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Image image = icon.getImage();
                Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);

                setIconAt(getIndex(model), icon);
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                setTitleAt(getIndex(model), model.getFilePath().getFileName().toString());
                setToolTipTextAt(getIndex(model), model.getFilePath().toString());
            }

        });
    }

    /**
     * Returns {@link ImageIcon} loaded from given input stream.
     * 
     * @param is
     *            input stream from which is icon loaded
     * @return {@link ImageIcon} from given input stream
     * @throws IOException
     *             File is not found, or reading is not allowed
     */

    private ImageIcon getImageFromInputStream(InputStream is) throws IOException {

        Objects.requireNonNull(is);

        byte[] bytes = is.readAllBytes();
        is.close();
        return new ImageIcon(bytes);
    }

    /**
     * It returns on which index is given model located. If given model is not in
     * the model's list it will <b>return -1</b>.
     * 
     * @param model
     *            model which will be searched
     * @return position of the model in the list
     */
    private int getIndex(SingleDocumentModel model) {
        int i = 0;
        for (SingleDocumentModel document : documents) {
            if (model.equals(document)) {
                return i;
            }
            ++i;
        }
        return -1;
    }
}
