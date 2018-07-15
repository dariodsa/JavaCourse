package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorLabel;
import hr.fer.zemris.java.hw16.jvdraw.editing.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.ConcreteDrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.states.AddingCircle;
import hr.fer.zemris.java.hw16.jvdraw.states.AddingFilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.states.AddingLine;
import hr.fer.zemris.java.hw16.jvdraw.states.AddingPolygon;
import hr.fer.zemris.java.hw16.jvdraw.states.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Main class {@link JVDraw} which is responsible for running all components and
 * starting the graphical thread.
 * 
 * @author dario
 *
 */
public class JVDraw extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * title of the window
     */
    private static final String TITLE = "JVDraw";

    /**
     * width of the window
     */
    private static final int WIDTH = 700;

    /**
     * height of the window
     */
    private static final int HEIGHT = 500;

    /**
     * line, name of the state
     */
    private static final String LINE = "Line";

    /**
     * circle, name of the state
     */
    private static final String CIRCLE = "Circle";

    /**
     * filled circle, name of the state
     */
    private static final String FILLED_CIRCLE = "Filled circle";

    /**
     * file, for JMenu
     */
    private static final String FILE = "File";

    /**
     * drawing model
     */
    private DrawingModel model = new ConcreteDrawingModel();
    /**
     * drawing canvas
     */
    private JDrawingCanvas canvas;
    /**
     * map of states and it's name
     */
    private Map<String, Tool> states;

    /**
     * path of the file location of the current drawing
     */
    private Path currentPath;

    /**
     * indicates was the current file changed, used in exiting the application
     */
    private boolean changed = false;

    /**
     * open action
     */
    private Action openAction = new AbstractAction("Open") {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (changed && JOptionPane.showConfirmDialog(JVDraw.this,
                    "You have non saved components. Are you sure do you want to open another draw?", "Exit",
                    JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE) == JOptionPane.CANCEL_OPTION) {
                return;
            }

            JFileChooser jfc = new JFileChooser();
            jfc.setFileFilter(new FileNameExtensionFilter("JVD files", "jvd"));
            if (jfc.showOpenDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {
                Path selectedPath = jfc.getSelectedFile().toPath();

                if (!selectedPath.getFileName().toString().endsWith(".jvd")) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Choose .jvd file", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    List<String> lines = Files.readAllLines(selectedPath);
                    List<GeometricalObject> newObjects = new ArrayList<>();
                    clearModel();
                    for (String line : lines) {
                        try {
                            GeometricalObject newObject = GeometricalObject.construct(line);
                            newObjects.add(newObject);
                        } catch(Exception ex) {
                            JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
                        }
                        
                    }
                    System.out.println(model.size());
                    System.out.println("Adding new " + newObjects.size());
                    for (GeometricalObject object : newObjects) {
                        model.add(object);
                    }
                    changed = false;
                    currentPath = selectedPath;

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };
    /**
     * save action
     */
    private Action saveAction = new AbstractAction("Save") {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPath == null) {
                JFileChooser jfc = new JFileChooser();
                jfc.addChoosableFileFilter(new FileFilter() {

                    @Override
                    public String getDescription() {
                        return "*.jvd";
                    }

                    @Override
                    public boolean accept(File f) {
                        return f.getName().endsWith(".jvd");
                    }
                });
                jfc.setFileFilter(new FileNameExtensionFilter("JVD files", "jvd"));
                if (jfc.showSaveDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {
                    currentPath = jfc.getSelectedFile().toPath();
                }

            }
            if (currentPath != null) {

                if (!currentPath.getFileName().toString().endsWith(".jvd")) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Choose .jvd file", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<String> exportObject = new ArrayList<>();
                for (int i = 0, len = model.size(); i < len; ++i) {
                    exportObject.add(model.getObject(i).export());
                }
                try {
                    Files.write(currentPath, exportObject);
                    changed = false;
                    JOptionPane.showMessageDialog(JVDraw.this, "Saved", "Saving", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    };
    /**
     * save as action
     */
    private Action saveAsAction = new AbstractAction("Save as") {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileFilter() {

                @Override
                public String getDescription() {
                    return "*.jvd";
                }

                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".jvd");
                }
            });

            if (jfc.showSaveDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {
                Path currentPath = jfc.getSelectedFile().toPath();

                if (!currentPath.getFileName().toString().endsWith(".jvd")) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Choose .jvd file", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                List<String> exportObject = new ArrayList<>();
                for (int i = 0, len = model.size(); i < len; ++i) {
                    exportObject.add(model.getObject(i).expo
                            rt());
                }
                try {
                    Files.write(currentPath, exportObject);
                    changed = false;
                    JOptionPane.showMessageDialog(JVDraw.this, "Saved", "Saving", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    };

    /**
     * export action
     */
    private Action exportAction = new AbstractAction("Export") {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif"));
            jfc.addChoosableFileFilter(new FileFilter() {

                @Override
                public String getDescription() {
                    return "*.gif, *.png, *.jpg";
                }

                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".jpg") || f.getName().endsWith(".png") || f.getName().endsWith(".gif");
                }

            });

            if (jfc.showSaveDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {

                Path selectedPath = jfc.getSelectedFile().toPath();

                if (!selectedPath.getFileName().toString().endsWith(".jpg")
                        && !selectedPath.getFileName().toString().endsWith(".png")
                        && !selectedPath.getFileName().toString().endsWith(".gif")) {
                    JOptionPane.showMessageDialog(JVDraw.this, "Choose .png, .jpg or gif file", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int strLen = selectedPath.getFileName().toString().length();
                String extension = selectedPath.getFileName().toString().substring(strLen - 3);

                GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
                for (int i = 0, len = model.size(); i < len; ++i) {
                    model.getObject(i).accept(bbcalc);
                }
                Rectangle box = bbcalc.getBoundingBox();
                BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
                Graphics2D g = image.createGraphics();
                g.transform(AffineTransform.getTranslateInstance(-box.x, -box.y));

                GeometricalObjectPainter painter = new GeometricalObjectPainter(g);

                for (int i = 0, len = model.size(); i < len; ++i) {
                    model.getObject(i).accept(painter);
                }
                g.dispose();

                try {
                    ImageIO.write(image, extension, selectedPath.toFile());
                    JOptionPane.showMessageDialog(JVDraw.this, "Export is done.", "Export", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    };

    /**
     * exit action, can be found in the menu
     */
    private Action exitAction = new AbstractAction("Exit") {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (changed && JOptionPane.showConfirmDialog(JVDraw.this,
                    "You have non saved components. Are you sure do you want to exit the application?", "Exit",
                    JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE) == JOptionPane.CANCEL_OPTION) {
                return;
            }

            System.exit(0);
        }
    };

    /**
     * Constructor of the JDraw which sets up all basic things and after that it
     * calls {@link #initGUI()}.
     */
    public JVDraw() {
        super(TITLE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLocation(0, 0);

        states = new HashMap<>();

        initGUI();
    }
    public JVDraw(int i) {

    }

    public Path getImage() {
        Path selectedPath = Paths.get("slika.png");

        int strLen = selectedPath.getFileName().toString().length();
        String extension = selectedPath.getFileName().toString().substring(strLen - 3);

        GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
        System.out.println(model.size());
        for (int i = 0, len = model.size(); i < len; ++i) {
            model.getObject(i).accept(bbcalc);
        }
        Rectangle box = bbcalc.getBoundingBox();
        System.out.println(box);
        BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        g.transform(AffineTransform.getTranslateInstance(-box.x, -box.y));

        GeometricalObjectPainter painter = new GeometricalObjectPainter(g);

        for (int i = 0, len = model.size(); i < len; ++i) {
            model.getObject(i).accept(painter);
        }
        g.dispose();

        try {
            ImageIO.write(image, extension, selectedPath.toFile());

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return selectedPath;
    }
    
    public boolean importText(String text) {
        String[] lines = text.split("\\n");
        
        List<GeometricalObject> newObjects = new ArrayList<>();
        clearModel();
        String line2 = "";
        System.out.println(lines.length);
        try {
            int i =0;
            for (String line : lines) {
                if(i!=lines.length-1) {
                    line = line.substring(0, line.length()-1);
                }
                ++i;
                line2 = line;
                
                GeometricalObject newObject = GeometricalObject.construct(line);
                System.out.println(newObject);
                newObjects.add(newObject);
            }
            System.out.println(model.size());
            System.out.println("Adding new " + newObjects.size());
            for (GeometricalObject object : newObjects) {
                model.add(object);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("GREŠKA");
            return false;
        }
        return true;
    }
    
    
    /**
     * Removes all {@link GeometricalObject} from the drawing model.
     */
    private void clearModel() {
        List<GeometricalObject> objects = new ArrayList<>();
        for (int i = 0, len = model.size(); i < len; ++i) {
            objects.add(model.getObject(i));
        }
        for (GeometricalObject obj : objects) {
            model.remove(obj);
        }
    }

    /**
     * Initialize graphical user interface.
     */
    private void initGUI() {

        Container cp = getContentPane();

        JColorArea fgColorArea = new JColorArea();
        JColorArea bgColorArea = new JColorArea();
        JColorLabel colorLabel = new JColorLabel(fgColorArea, bgColorArea);

        states.put(LINE, new AddingLine(model, fgColorArea, bgColorArea));
        states.put(CIRCLE, new AddingCircle(model, fgColorArea, bgColorArea));
        states.put(FILLED_CIRCLE, new AddingFilledCircle(model, fgColorArea, bgColorArea));
        states.put("POLYGON", new AddingPolygon(model, fgColorArea, bgColorArea));

        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setAlignmentX(LEFT_ALIGNMENT);

        northPanel.add(fgColorArea);
        northPanel.add(bgColorArea);
        ButtonGroup group = new ButtonGroup();

        JToggleButton line = new JToggleButton(new AbstractAction(LINE) {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentState(states.get(LINE));
            }
        });
        line.setSelected(true);
        JToggleButton circle = new JToggleButton(new AbstractAction(CIRCLE) {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentState(states.get(CIRCLE));
            }
        });
        JToggleButton filledCircle = new JToggleButton(new AbstractAction(FILLED_CIRCLE) {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentState(states.get(FILLED_CIRCLE));
            }
        });
        
        JToggleButton poylgon = new JToggleButton(new AbstractAction("Filled polygon") {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentState(states.get("POLYGON"));
            }
        });
        
        group.add(line);
        group.add(circle);
        group.add(filledCircle);
        group.add(poylgon);
        northPanel.add(line);
        northPanel.add(circle);
        northPanel.add(filledCircle);
        northPanel.add(poylgon);

        cp.add(northPanel, BorderLayout.NORTH);
        cp.add(initEastPanel(), BorderLayout.EAST);
        canvas = new JDrawingCanvas(model);

        canvas.setCurrentState(states.get(LINE));

        cp.add(canvas, BorderLayout.CENTER);
        cp.add(colorLabel, BorderLayout.SOUTH);
        initMenus();

        setUpChangesListener();
    }

    /**
     * Sets up the listener on the changes on the drawing canvas.
     */
    private void setUpChangesListener() {
        model.addDrawingModelListener(new DrawingModelListener() {

            @Override
            public void objectsRemoved(DrawingModel sourlce, int index0, int index1) {
                changed = true;

            }

            @Override
            public void objectsChanged(DrawingModel source, int index0, int index1) {
                changed = true;
            }

            @Override
            public void objectsAdded(DrawingModel source, int index0, int index1) {
                changed = true;
            }
        });

    }

    /**
     * Initialize east {@link JPanel} which will consist of a one JList in which
     * user will be able to see added geometrical object, edit them, change order of
     * the list and alsp to delete them.
     * 
     * @return {@link Component} JPanel
     */
    private Component initEastPanel() {
        JPanel panel = new JPanel();

        DrawingObjectListModel listModel = new DrawingObjectListModel(model);

        JList<GeometricalObject> list = new JList<>(listModel);
        model.addDrawingModelListener(listModel);

        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    GeometricalObject selectedObject = list.getSelectedValue();
                    GeometricalObjectEditor editor = selectedObject.createGeometricalObjectEditor();
                    if (JOptionPane.showConfirmDialog(null, editor, "",
                            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            editor.checkEditing();
                            editor.acceptEditing();
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
                        }
                    }
                }
            }
        });

        list.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (list.getSelectedValue() == null)
                    return;

                GeometricalObject selectedObject = list.getSelectedValue();

                if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                    model.changeOrder(selectedObject, +1);
                } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    model.changeOrder(selectedObject, -1);
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    model.remove(selectedObject);
                }
            }
        });

        panel.add(list);

        return panel;
    }

    /**
     * Initialize menus which will be located at the top and which will be shown to
     * the user.
     */
    private void initMenus() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(FILE);
        JMenuItem open = new JMenuItem(openAction);
        JMenuItem save = new JMenuItem(saveAction);
        JMenuItem saveAs = new JMenuItem(saveAsAction);

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);

        fileMenu.addSeparator();

        fileMenu.add(exportAction);
        fileMenu.addSeparator();

        fileMenu.add(exitAction);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Runs a graphical thread in which will create GUI.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JVDraw::new);
    }
}