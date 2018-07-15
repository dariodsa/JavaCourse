package hr.fer.zemris.java.web.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;


import hr.fer.zemris.java.web.objects.Bend;
import hr.fer.zemris.java.web.objects.BendResult;

/**
 * Class {@link FileHandler} handles users request to the "database". It makes sure
 * that all requests are atomic and that they are served in one line. It is responsible 
 * to make sure all request multi-thread safe.
 * @author dario
 *
 */
public class FileHandler {
    
    /**
     * context
     */
    private ServletContext context;
    /**
     * singleton handler
     */
    private static FileHandler handler;
    
    /**
     * voting result path
     */
    private static String VOTING_RESULT = "/WEB-INF/glasanje-rezultati.txt";
    /**
     * voting definition path
     */
    private static String VOTE_DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";
    
    /**
     * Private constructor, this is a singleton.
     * @param context context, object which will be used in synchronized blocks
     */
    private FileHandler(ServletContext context) {
        this.context = context;
        
        FileHandler.VOTING_RESULT = context.getRealPath(VOTING_RESULT);
        FileHandler.VOTE_DEFINITION_FILE = context.getRealPath(VOTE_DEFINITION_FILE);
    }
    /**
     * Returns the singleton {@link FileHandler}.
     * @param context object used for synchronized.
     * @return {@link FileHandler} object
     */
    public static FileHandler getInstance(ServletContext context) {
        if(handler == null) {
            handler = new FileHandler(context);
        }
        return handler;
    }
    /**
     * Returns the list of bends.
     * @return list of {@link Bend}
     */
    public List<Bend> getBends() {
        List<Bend> bends = new ArrayList<>();
        
        Path file = Paths.get(VOTE_DEFINITION_FILE);

        if (!Files.exists(file)) {
            createFile(file);
        }
        
        List<String> lines = allLines(file);
        for (String line : lines) {
            String[] splitted = line.split("\\t");

            int id = Integer.parseInt(splitted[0]);
            String name = splitted[1];
            String link = splitted[2];

            bends.add(new Bend(id, name, link));
        }
            
        
        return bends;
    }
    /**
     * Returns the bend results.
     * @return bend results
     */
    public List<BendResult> getBendsResults() {
        Path file = Paths.get(VOTING_RESULT);
        List<BendResult> bendResult = new ArrayList<>();
        List<Bend> bends = getBends();
        if(!Files.exists(file)) {
            createFile(file);
            List<String> result = new ArrayList<>();
            for(Bend bend : bends) {
                result.add(bend.getId()+ "\t"+"0");
                bendResult.add(new BendResult(bend, 0));
            }
            write(file, result);
            return bendResult;
        }
        List<String> lines = allLines(file);
        for(String line : lines) {
            String[] splitted = line.split("\\t");
            int id = Integer.parseInt(splitted[0]);
            int numOfVotes = Integer.parseInt(splitted[1]);
            for(Bend bend : bends) {
                if(bend.getId() == id) {
                    bendResult.add(new BendResult(bend, numOfVotes));
                }
            }
        }
        boolean newbends = false;
        for(Bend bend : bends) {
            boolean found = false;
            for(BendResult res : bendResult) {
                if(res.getBend().getId() == bend.getId()) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                bendResult.add(new BendResult(bend, 0));
                newbends = true;
            }
        }
        if(newbends) {
            List<String> result = new ArrayList<>();
            for(BendResult res : bendResult) {
                result.add(res.getBend().getId()+ "\t"+res.getNumOfVotes());
            }
            write(Paths.get(VOTING_RESULT), result);
        }
        return bendResult;
    }
    /**
     * Creates empty file to the given path.
     * @param path path of file
     */
    private void createFile(Path path) {
        synchronized (context) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Returns all lines in the list in the form of list.
     * @param path path to the file
     * @return list of lines
     */
    private List<String> allLines(Path path) {
        
        List<String> result = new ArrayList<>();
        synchronized (context) {
            try {
                result = Files.readAllLines(path);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }
    /**
     * Writes to the given path lines given in the attribute.
     * @param path path to file
     * @param lines lines in file
     */
    private void write(Path path, List<String> lines) {
        synchronized (context) {
            try {
                Files.write(path, lines);
            } catch (IOException e) {
                throw new RuntimeException("Error writting in file  " + path.getFileName().toString());
            }
        }
    }
    /**
     * Sets new bend results into the file.
     * @param bendResult new bends result
     */
    public void setNewResults(List<BendResult> bendResult) {
        List<String> list = new ArrayList<>();
        for(BendResult result : bendResult) {
            list.add(result.getBend().getId()+"\t"+result.getNumOfVotes());
        }
        write(Paths.get(VOTING_RESULT), list);
        
    }
}
