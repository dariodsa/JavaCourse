package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw16.trazilica.processor.structures.Document;
import hr.fer.zemris.java.hw16.trazilica.processor.structures.ResultDocument;
import hr.fer.zemris.java.hw16.trazilica.processor.structures.StringPair;

/**
 * Main class {@link Konzola} is responsible for processing user's input 
 * and showing the results on the standard output.
 * @author dario
 *
 */
public class Konzola {
    /**
     * keyword query
     */
    private static final String QUERY = "query";
    
    /**
     * keyword type 
     */
    private static final String TYPE = "type";
    
    /**
     * keyword results
     */
    private static final String RESULTS = "results";
    
    /**
     * keyword exit
     */
    private static final String EXIT = "exit";
    /**
     * last performed result
     */
    private static List<ResultDocument> lastResult;
    
    /**
     * Main static method which runs main program.
     * @param args path to the articles
     */
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: konzola path_to_articles");
            System.out.println("Please enter the path to files as the argument.");
            System.exit(1);
        }
        
        Path pathToFiles = Paths.get(args[0]);
        
        try {
            SearchProvider.setPathToFiles(pathToFiles);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.printf("Veličina rječnika je %d riječi.%n",
                SearchProvider.getSearchProvider().getVocabularSize());
       
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter command > ");
        
        while(scanner.hasNext()) {
            String next = scanner.next();
            switch (next) {
            case QUERY:
                
                String line = scanner.nextLine();
                Document tempDocument = new Document(line);
                
                List<ResultDocument> results = SearchProvider.getSearchProvider().getResult(tempDocument);
                
                System.out.print("Query is: [");
                
                int size = tempDocument.getVocabulary().size();
                int br = 0;
                for(StringPair pair : tempDocument.getVocabulary()) {
                    if(br + 1 != size) {
                        System.out.printf("%s, ", pair.getText());
                    } else {
                        System.out.printf("%s", pair.getText());
                    }
                    ++br;
                }
                System.out.println("]");
                
                System.out.println("Najboljih 10 rezultata:");
                for(int i=0, len = Math.min(10,results.size()); i < len; ++i) {
                    System.out.printf("[ %d] %s%n",i,results.get(i));
                }
                lastResult = results;
                
                break;
            case TYPE:
                int type = scanner.nextInt();
                if(type < 0  || type >= lastResult.size()) {
                    System.out.println("Please execute command type with the regular range.");
                } else {
                    System.out.println("-------------------");
                    System.out.println("Dokument:" + lastResult.get(type).getDocument().getFilePath().toAbsolutePath());
                    System.out.println("-------------------");
                    for(String oneLine : lastResult.get(type).getDocument().getLines()) {
                        System.out.println(oneLine);
                    }
                    System.out.println("-------------------");
                }
                
                break;
            case RESULTS:
                if(lastResult == null) {
                    System.out.println("Please execute command query before calling this one.");
                } else {
                    for(int i=0, len = Math.min(10,lastResult.size()); i < len; ++i) {
                        System.out.printf("[ %d] %s%n",i,lastResult.get(i));
                    }
                }
                break;
            case EXIT:
                System.out.println("Goodbye. :-)");
                scanner.close();
                return;
            default:
                System.out.println(next);
                System.out.println("Nepoznata naredba, unknown command.");
                break;
            }
            
            System.out.print("Enter command > ");
        }
        scanner.close();
    }
}
