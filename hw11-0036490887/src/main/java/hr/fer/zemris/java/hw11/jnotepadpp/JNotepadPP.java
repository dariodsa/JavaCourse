package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.component.LJMenu;

/**
 * Class {@link JNotepadPP} creates and rearrange graphical interface through
 * the users. It sets initial size and location of a window with a default
 * title. It allows user to dynamically change language of a GUI. Currently
 * there are four languages in offer. User can add new document and edit it.
 * After that it can be saved or disposed. If user tries to overwrite others
 * file, program will warn a user. User can do almost every other action, which
 * can be done in any standard text editor, like cutting, copying, pasting or
 * inverting a case of letters. User can see basic statistic about its document,
 * like number of lines and number of characters and non-blank characters. If
 * user tries to close application and some of the documents or just one is not
 * saved, application will warn user about that. Tabs, or documents will be
 * added in {@link MultipleDocumentModel} as {@link SingleDocumentModel}. So
 * {@link DefaultMultipleDocumentModel} is a singleton and it will be created
 * only once, even though it is not construct in that way. Every new document
 * will be encapsulate in {@link SingleDocumentModel} with default path set to
 * null and title to be set new and some id.
 * 
 * @author dario
 *
 */
public class JNotepadPP extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * title of a frame
     */
    private static final String TITLE = "JNotepad++";

    /**
     * form localization provider
     */
    private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

    /**
     * tabbed pane, used for showing the texts
     */
    private DefaultMultipleDocumentModel model = new DefaultMultipleDocumentModel();

    /**
     * list of available languages
     */
    private String[] languages = { "hr", "en", "de", "ru" };

    /**
     * toolbar
     */
    private JToolBar toolBar = new JToolBar();

    /**
     * Creates {@link JNotepadPP} window, sets size, title and adds all necessary
     * listeners to the window.
     */
    public JNotepadPP() {
        setLocation(0, 0);
        setSize(1000, 700);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle(TITLE);
        initGUI();

        model.addMultipleDocumentListener(new MultipleDocumentListener() {

            @Override
            public void documentRemoved(SingleDocumentModel singleModel) {
                if (model.getNumberOfDocuments() == 0) {
                    saveDocumentAction.setEnabled(false);
                    saveAsDocumentAction.setEnabled(false);
                    closeDocumentAction.setEnabled(false);
                    statisticalAction.setEnabled(false);
                    ascendingAction.setEnabled(false);
                    descendingAction.setEnabled(false);
                    uniqueAction.setEnabled(false);
                    JNotepadPP.this.setTitle(" - " + TITLE);
                }

            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                statisticalAction.setEnabled(true);
            }

            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                if (model.getCurrentDocument().getFilePath() == null) {
                    saveAsDocumentAction.setEnabled(true);
                    closeDocumentAction.setEnabled(true);

                } else {
                    saveDocumentAction.setEnabled(true);
                    saveAsDocumentAction.setEnabled(true);
                    closeDocumentAction.setEnabled(true);
                    JNotepadPP.this.setTitle(currentModel.getFilePath().toString() + " - " + TITLE);
                }
            }
        });

        closingAction();
    }

    /**
     * closing action, when close button is pressed
     */
    private void closingAction() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {

                int num = 0;
                int closed = 0;
                for (int i = 0, len = model.getNumberOfDocuments(); i < len; ++i) {
                    if (model.getDocument(i).isModified()) {
                        ++closed;
                        int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
                                flp.getString("EXITMES") + " --> " + model.getTitleAt(i), flp.getString("EXITTITLE"),
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (result == JOptionPane.NO_OPTION) {
                            return;
                        } else if (result == JOptionPane.YES_OPTION) {
                            ++num;
                        }

                    }
                }
                if (num == closed) {
                    dispose();
                }

            }
        });

    }

    /**
     * Initialize graphical user interface.
     */
    private void initGUI() {
        Container cp = getContentPane();
        LocalizationProvider.getInstance().setLanguage("hr");
        JButton button = new JButton();
        button.setText(LocalizationProvider.getInstance().getString("LOGIN"));
        button.addActionListener((e) -> {
            if (LocalizationProvider.getInstance().getLanguage().equals("hr")) {
                LocalizationProvider.getInstance().setLanguage("en");
            } else {
                LocalizationProvider.getInstance().setLanguage("hr");
            }
        });

        titleInit();
        createMenus();

        setAcceleratorAndMnemonic();
        initToolbar();

        cp.add(model, BorderLayout.CENTER);
        setUpStatusBar();
        LocalizationProvider.getInstance().setLanguage("hr");
        flp.fire();
    }

    /**
     * Methods which creates new daemon thread which updates clock on clockLabel,
     * which will we located in the south east corner.
     * 
     * @param clockLabel
     *            label on which date and time will be shown
     */
    private void addClock(JLabel clockLabel) {
        Thread T = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
                String data = format.format(new Date());
                clockLabel.setText(data);
            }
        });
        T.setDaemon(true);
        T.start();
    }

    /**
     * Sets up the status bar, which indicates length of the document, caret
     * location and current time and date.
     */
    private void setUpStatusBar() {
        getContentPane().add(toolBar, BorderLayout.SOUTH);
        toolBar.setBorder(new Border() {

            @Override
            public void paintBorder(Component component, Graphics g, int x, int y, int width, int height) {
                g.fillRect(x, y, width, 1);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }

            @Override
            public Insets getBorderInsets(Component container) {
                return new Insets(5, 0, 0, 0);
            }
        });
        toolBar.setLayout(new BorderLayout());

        JLabel labelLength = new JLabel("Loading ...");
        JLabel labelPosition = new JLabel("Loading ...");
        JLabel labelClock = new JLabel("Loading ...");

        toolBar.add(labelLength, BorderLayout.WEST);
        toolBar.add(labelPosition, BorderLayout.CENTER);
        toolBar.add(labelClock, BorderLayout.EAST);

        labelPosition.setHorizontalAlignment(SwingConstants.CENTER);

        CaretListener caretListener = new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent event) {
                int dot = event.getDot();
                int mark = event.getMark();
                JTextArea area = (JTextArea) event.getSource();
                int row = getRow(dot, area.getText());
                int col = getCol(dot, area.getText());
                int sel = Math.abs(dot - mark);
                labelPosition.setText("Ln:" + row + " " + "Col:" + col + " Sel:" + sel);
                labelLength.setText("Length: " + area.getText().length());
                if (sel > 0) {
                    upperCaseAction.setEnabled(true);
                    lowerCaseAction.setEnabled(true);
                    invertCaseAction.setEnabled(true);
                    ascendingAction.setEnabled(true);
                    descendingAction.setEnabled(true);
                    uniqueAction.setEnabled(true);
                } else {
                    upperCaseAction.setEnabled(false);
                    lowerCaseAction.setEnabled(false);
                    invertCaseAction.setEnabled(false);
                    ascendingAction.setEnabled(false);
                    descendingAction.setEnabled(false);
                    uniqueAction.setEnabled(false);
                }
            }
        };

        model.addMultipleDocumentListener(new MultipleDocumentListener() {

            @Override
            public void documentRemoved(SingleDocumentModel model) {
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                model.getTextComponent().addCaretListener(caretListener);

            }

            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                labelLength.setText("Length: " + currentModel.getTextComponent().getText().length());
            }
        });

        addClock(labelClock);
    }

    /**
     * Returns the number of row in which caret is located in a given text.
     * 
     * @param dot
     *            caret location
     * @param text
     *            given text
     * @return row number
     */
    private int getRow(int dot, String text) {
        int ans = 0;
        for (int i = 0; i < dot; ++i) {
            if (text.charAt(i) == '\n') {
                ++ans;
            }
        }
        return ans;
    }

    /**
     * Returns the number of columns in which caret is located in a given text.
     * 
     * @param dot
     *            caret location
     * @param text
     *            given text
     * @return column number
     */
    private int getCol(int dot, String text) {
        int ans = 0;
        for (int i = 0; i < dot; ++i) {
            if (text.charAt(i) == '\n') {
                ans = 0;
            } else {
                ++ans;
            }
        }
        return ans;
    }

    /**
     * Sets initial title of a window and sets listener to its title.
     */
    private void titleInit() {

        model.addChangeListener((e) -> {
            setTitle("" + " - JNotepad++");
        });

    }

    /**
     * Initialize toolbar which is located in the upper part of a window. It is set
     * to be floatable.
     */
    private void initToolbar() {
        
        JToolBar toolbar = new JToolBar("Alati");
        
        toolbar.setFloatable(true);
        toolbar.add(new JButton(newDocumentAction));
        toolbar.add(new JButton(openDocumentAction));
        toolbar.add(new JButton(cutAction));
        toolbar.add(new JButton(copyAction));
        toolbar.add(new JButton(pasteAction));
        toolbar.add(new JButton(upperCaseAction));
        toolbar.add(new JButton(lowerCaseAction));
        
        getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * cut action
     */
    private Action cutAction = new LocalizableAction("CUT", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            Action privateAction = new DefaultEditorKit.CutAction();
            privateAction.actionPerformed(event);
        }
    };
    /**
     * copy action
     */
    private Action copyAction = new LocalizableAction("COPY", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            Action privateAction = new DefaultEditorKit.CopyAction();
            privateAction.actionPerformed(event);
        }
    };
    /**
     * paste action
     */
    private Action pasteAction = new LocalizableAction("PASTE", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            Action privateAction = new DefaultEditorKit.PasteAction();
            privateAction.actionPerformed(event);
        }
    };
    /**
     * new document action
     */
    private Action newDocumentAction = new LocalizableAction("NEW", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            model.createNewDocument();
        }

    };
    /**
     * open document action
     */
    private Action openDocumentAction = new LocalizableAction("OPEN", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle(flp.getString("OPENTITLE"));
            if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("OPENTEXT"), flp.getString("OPENTITLE"),
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            for (int i = 0; i < model.getNumberOfDocuments(); ++i) {
                if(model.getDocument(i).getFilePath() == null) {
                    continue;
                }
                if (model.getDocument(i).getFilePath().equals(jfc.getSelectedFile().toPath())) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("OPENERRORTEXT"),
                            flp.getString("OPENTITLE"), JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            Path openFile = jfc.getSelectedFile().toPath();
            if(!Files.exists(openFile)) {
                JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("OPENERRORTEXT2"),
                        flp.getString("OPENTITLE"), JOptionPane.WARNING_MESSAGE);
                return;
            }
            model.loadDocument(openFile);
        }

    };
    /**
     * save document action
     */
    private Action saveDocumentAction = new LocalizableAction("SAVE", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            Path newPath = model.getCurrentDocument().getFilePath();
            if (newPath == null) {
                JFileChooser jfc = new JFileChooser();
                if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("SAVETITLE"),
                            flp.getString("SAVETEXT"), JOptionPane.WARNING_MESSAGE);
                    return;
                }
                model.getCurrentDocument().setFilePath(jfc.getSelectedFile().toPath());
            }
            model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
        }

    };
    /**
     * save as action
     */
    private Action saveAsDocumentAction = new LocalizableAction("SAVEAS", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            
            
            JFileChooser jfc = new JFileChooser();
            if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("SAVETITLE"),
                        flp.getString("SAVETEXT"), JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (Files.exists(jfc.getSelectedFile().toPath())) {
                if (JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("SAVEOVERWRITE"),
                        flp.getString("SAVE"), JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
                    return;
                }

            }

            model.getCurrentDocument().setFilePath(jfc.getSelectedFile().toPath());
            
            model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
        }

    };
    /**
     * close document, close tab action
     */
    private Action closeDocumentAction = new LocalizableAction("CLOSE", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            if (model.getCurrentDocument().isModified()) {
                if (JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("EXITMES"), flp.getString("EXITTITLE"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            model.closeDocument(model.getCurrentDocument());
        }

    };

    /**
     * Mathematical function identity, returns value which was given as the
     * parameter. f(x) = x
     * 
     * @param value
     *            x
     * @return x
     */
    public static int identityFunction(int value) {
        return value;
    }

    /**
     * Mathematical function property, returns value -1 if the x is positive and 1
     * if is negative f(x) = -1 f(-x) = 1
     * 
     * @param value
     *            x
     * @return x
     */
    public static int propertyFunction(int value) {
        return value >= 0 ? -1 : 1;
    }

    /**
     * upper case action
     */
    private Action upperCaseAction = new ToolsAction("UPPER", flp, Character::toUpperCase, model);

    /**
     * lower case action
     */
    private Action lowerCaseAction = new ToolsAction("LOWER", flp, Character::toLowerCase, model);

    /**
     * invert case action
     */
    private Action invertCaseAction = new ToolsAction("INVERT", flp,
            (x) -> Character.isLowerCase(x) ? Character.toUpperCase(x) : Character.toLowerCase(x), model);

    /**
     * ascending action
     */
    private Action ascendingAction = new SortAction("ASCENDING", flp, JNotepadPP::propertyFunction, model);

    /**
     * descending action
     */
    private Action descendingAction = new SortAction("DESCENDING", flp, JNotepadPP::identityFunction, model);

    /**
     * unique action
     */
    private Action uniqueAction = new UniqueActions("UNIQUE", flp, model);

    /**
     * statistical action
     */
    private Action statisticalAction = new LocalizableAction("STAT", flp) {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            String text = model.getCurrentDocument().getTextComponent().getText();
            int len = text.length();
            int lines = 0;
            int nonBlank = 0;
            for (int i = 0; i < len; ++i) {
                if (text.charAt(i) == '\n') {
                    ++lines;
                }
                if (isNonBlankChar(text.charAt(i))) {
                    ++nonBlank;
                }
            }
            JOptionPane.showMessageDialog(JNotepadPP.this,
                    String.format(flp.getString("STATMES"), len, nonBlank, lines));
        }
    };

    /**
     * creates menu for main frame
     */
    private void createMenus() {

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu fileMenu = new LJMenu("FILE", flp);
        JMenu editMenu = new LJMenu("EDIT", flp);
        JMenu toolsMenu = new LJMenu("TOOLS", flp);
        JMenu setMenu = new LJMenu("SET", flp);

        saveAsDocumentAction.setEnabled(false);
        closeDocumentAction.setEnabled(false);

        // file menu
        initFileMenu(fileMenu);

        // edit menu
        initEditMenu(editMenu);

        // tools menu
        initToolMenu(toolsMenu);

        // menu set
        createSetMenu(setMenu);

        // adds menus to the menubar
        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(toolsMenu);
        menubar.add(setMenu);

    }

    /**
     * Initialize file menu on main menu bar.
     * 
     * @param fileMenu
     *            file menu
     */
    private void initFileMenu(JMenu fileMenu) {

        fileMenu.add(newDocumentAction);
        fileMenu.add(openDocumentAction);
        fileMenu.add(saveDocumentAction);
        fileMenu.add(saveAsDocumentAction);
        fileMenu.addSeparator();
        fileMenu.add(closeDocumentAction);

    }

    /**
     * Initialize edit menu on main menu bar.
     * 
     * @param editMenu
     *            edit menu
     */
    private void initEditMenu(JMenu editMenu) {

        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);

    }

    /**
     * Initialize tool menu on main menu bar.
     * 
     * @param toolsMenu
     *            tool menu
     */
    private void initToolMenu(JMenu toolsMenu) {

        JMenu sort = new LJMenu("SORT", flp);
        sort.add(ascendingAction);
        sort.add(descendingAction);

        toolsMenu.add(upperCaseAction);
        toolsMenu.add(lowerCaseAction);
        toolsMenu.add(invertCaseAction);

        toolsMenu.addSeparator();
        toolsMenu.add(sort);
        toolsMenu.addSeparator();
        toolsMenu.add(uniqueAction);
        toolsMenu.addSeparator();
        toolsMenu.add(statisticalAction);
        statisticalAction.setEnabled(false);

    }

    /**
     * Returns true if the given character is non-blank, false otherwise.
     * 
     * @param ch
     *            char which will be checked
     * @return true if the given char is non-blank, false otherwise
     */
    private boolean isNonBlankChar(char ch) {

        return !(ch == '\n' || ch == '\t' || ch == ' ');
    }

    /**
     * Sets accelerator and mnemonic keys for all actions which usually have those
     * actions in similar programs.
     */
    private void setAcceleratorAndMnemonic() {
        // FILE
        newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

        openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
        saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

        closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        // EDIT
        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
        
        upperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
        upperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        
        lowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
        lowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        
        ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
        ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        
        descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift B"));
        descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

        // TOOLS
        upperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
        upperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        lowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
        lowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
        invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
        statisticalAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift alt S"));
        statisticalAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F5);
        uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift alt U"));
        uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F4);

    }

    /**
     * Returns {@link ImageIcon} which will be read from the given input stream.
     * 
     * @param is
     *            input stream
     * @return {@link ImageIcon} icon read from input stream
     * @throws IOException
     *             Reading bytes results in an exception
     */
    private ImageIcon getImageFromInputStream(InputStream is) throws IOException {

        Objects.requireNonNull(is);

        byte[] bytes = is.readAllBytes();
        is.close();
        return new ImageIcon(bytes);
    }

    /**
     * Creates setMenu in which user can choose in which language show all component
     * be printed.
     * 
     * @param setMenu
     *            set menu on which menu component will be added
     */
    private void createSetMenu(JMenu setMenu) {

        int br = 1;
        for (String languageName : languages) {
            InputStream is = JNotepadPP.class.getClassLoader()
                    .getResourceAsStream("hr/fer/zemris/java/hw11/jnotepadpp/icons/" + languageName + ".png");
            ImageIcon icon = null;
            try {
                icon = getImageFromInputStream(is);
                Image image = icon.getImage();
                Image newimg = image.getScaledInstance(100, 70, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);
            } catch (IOException e) {
                throw new RuntimeException("Check your images of languages.");
            }
            Action language = new LocalizableAction(languageName, flp) {

                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    LocalizationProvider.getInstance().setLanguage(languageName);
                }

            };

            language.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt " + br++));
            JMenuItem item = new JMenuItem(language);
            item.setIcon(icon);
            setMenu.add(item);
        }

    }

    /**
     * Main method which creates graphical thread and in which creates
     * {@link JNotepadPP}.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JNotepadPP::new);
    }
}
