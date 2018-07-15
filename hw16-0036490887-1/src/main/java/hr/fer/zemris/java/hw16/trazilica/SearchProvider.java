package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw16.trazilica.processor.SearchProcessor;

/**
 * {@link SearchProvider} is a class which offers a {@link SearchProcessor} so
 * the rest of the code can be easily upgraded.
 * 
 * @author dario
 *
 */
public class SearchProvider {

    /**
     * search processor
     */
    private static SearchProcessor searchProcessor;

    /**
     * Returns the {@link SearchProcessor} instance.
     * 
     * @return {@link SearchProcessor} instance
     */
    public static SearchProcessor getSearchProvider() {
        return searchProcessor;
    }

    /**
     * Sets the path to the files and constructs the {@link SearchProcessor}.
     * 
     * @param pathToFiles
     *            path to the files
     * @throws IOException
     *             file error
     */
    public static void setPathToFiles(Path pathToFiles) throws IOException {
        searchProcessor = new SearchProcessor(pathToFiles);
    }

}
