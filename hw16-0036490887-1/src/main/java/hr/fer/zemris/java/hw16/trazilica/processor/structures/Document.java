package hr.fer.zemris.java.hw16.trazilica.processor.structures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Document specifies how to construct one, set main vector with the all
 * frequencies of the words and has the possibility to update result vector
 * which will be used in the comparison.
 * 
 * @author dario
 *
 */
public class Document {

    /**
     * path to its file
     */
    private Path path;
    /**
     * lines loaded from the path
     */
    private List<String> lines;
    /**
     * local vocabular of the document
     */
    private Structure localVocabular = new Trie();

    /**
     * result vector
     */
    private Vector resultVector;

    /**
     * Constructs the {@link Document} from the given path.
     * 
     * @param path
     *            path to the file
     * @throws IOException
     *             file processing error
     */
    public Document(Path path) throws IOException {
        this.path = path;
        this.lines = Files.readAllLines(path);
    }

    /**
     * Constructs the {@link Document} from one line. Used in user interaction.
     * 
     * @param line
     *            line of words
     */
    public Document(String line) {
        this.lines = new ArrayList<>();
        this.lines.add(line);
    }

    /**
     * Sets the document vector with the general vocabulary as the assistants
     * 
     * @param vocabular
     *            general vocabulary
     */
    public void setVector(Structure vocabular) {
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            for (int i = 0, len = line.length(); i < len; ++i) {
                if (Character.isAlphabetic(line.charAt(i))) {
                    builder.append(line.charAt(i));
                } else if (builder.length() > 0) {
                    StringPair pair = new StringPair(builder.toString());
                    if (vocabular.get(pair) != null) {
                        boolean notDuplicate = localVocabular.add(pair);
                        if (!notDuplicate) {
                            localVocabular.get(pair).addFrequency();
                        }
                    }
                    builder.setLength(0);
                }
            }
            if (builder.length() > 0) {
                StringPair pair = new StringPair(builder.toString());
                if (vocabular.get(pair) != null) {
                    boolean notDuplicate = localVocabular.add(pair);
                    if (!notDuplicate) {
                        localVocabular.get(pair).addFrequency();
                    }
                }
            }
            builder.setLength(0);
        }
    }

    /**
     * Returns the file path of the document.
     * 
     * @return filePath of the document
     */
    public Path getFilePath() {
        return this.path;
    }

    /**
     * Returns the {@link List} of {@link String} loaded from the document's file.
     * 
     * @return file content split by new line.
     */
    public List<String> getLines() {
        return this.lines;
    }

    /**
     * Returns new result vector.
     * 
     * @return {@link Vector} result vector
     */
    public Vector getResultVector() {
        return this.resultVector;
    }

    /**
     * Sets the result vector.
     * 
     * @param result
     *            double array, values of the result vector
     */
    public void setResultVector(double[] result) {
        resultVector = new Vector(result);
    }

    /**
     * Returns true if the words is in the local vocabulary.
     * 
     * @param word
     *            {@link StringPair}
     * @return true if it is, false otherwise
     */
    public boolean containsWord(StringPair word) {
        return localVocabular.get(word) != null;
    }

    /**
     * Returns the words frequency from the local vocabular.
     * 
     * @param word
     *            word for which frequency will be calculated
     * @return word frequency of the given word
     */
    public int wordFrequency(String word) {
        StringPair pair = new StringPair(word);
        if (localVocabular.get(pair) == null) {
            return 0;
        } else {
            return localVocabular.get(pair).getFrequency();
        }
    }
    /**
     * Returns the document's vocabulary.
     * @return document's vocabulary {@link Structure}
     */
    public Structure getVocabulary() {
        return this.localVocabular;
    }
}
