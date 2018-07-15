package hr.fer.zemris.java.hw16.trazilica.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.java.hw16.trazilica.processor.structures.Document;
import hr.fer.zemris.java.hw16.trazilica.processor.structures.ResultDocument;
import hr.fer.zemris.java.hw16.trazilica.processor.structures.StringPair;
import hr.fer.zemris.java.hw16.trazilica.processor.structures.Structure;
import hr.fer.zemris.java.hw16.trazilica.processor.structures.Trie;

/**
 * {@link SearchProcessor} is a class which is most responsible for getting the
 * results of the searches. It will make a vectors and perform comparison
 * between them.
 * 
 * @author dario
 *
 */
public class SearchProcessor {

    /**
     * name of the file in which stop words can be found
     */
    private static final String STOP_WORDS = "stopWords.txt";

    /**
     * threshold used for rang list in results
     */
    private static final double THRESHOLD = 1E-4;

    /**
     * path to the articles
     */
    private Path pathToFiles;

    /**
     * vocabular structure
     */
    private Structure vocabular = new Trie();
    /**
     * stop words structure
     */
    private Structure stopWords = new Trie();

    /**
     * list of documents
     */
    private List<Document> documents = new ArrayList<>();

    /**
     * Construct {@link SearchProcessor} with the path to the folder in which are
     * located all articles. From those article, all words will be collected and
     * stored in the vocabulary with its frequency.
     * 
     * @param pathToFiles
     *            path to the files
     * @throws IOException
     *             file error
     */
    public SearchProcessor(Path pathToFiles) throws IOException {
        this.pathToFiles = pathToFiles;
        load();
    }

    /**
     * Load methods, breaks down the steps by calling different methods.
     * 
     * @throws IOException
     *             file error
     */
    private void load() throws IOException {

        generateStopWords();
        generateVocabular();
        generateVectors();
    }

    /**
     * Generates vector of words with each of it's frequency of occurrence.
     * 
     * @throws IOException
     *             file error
     */
    private void generateVectors() throws IOException {
        Files.walk(pathToFiles).filter(Files::isReadable).filter(Files::isRegularFile).forEach(new Consumer<Path>() {

            @Override
            public void accept(Path path) {
                Document document;
                try {
                    document = new Document(path);
                    document.setVector(vocabular);
                    documents.add(document);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * Generates stopWords from the previously given {@link #STOP_WORDS} constant.
     * 
     * @throws IOException
     *             file error
     */
    private void generateStopWords() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Path path = new File(classLoader.getResource(STOP_WORDS).getFile()).toPath();

        Files.newBufferedReader(path).lines().forEach((line) -> {
            line = line.toLowerCase();
            stopWords.add(new StringPair(line));
        }

        );

    }

    /**
     * Generates vocabulary from the path in which articles with text can be found.
     * 
     * @throws IOException
     *             file error
     */
    private void generateVocabular() throws IOException {
        Files.walk(pathToFiles)
             .filter(Files::isReadable)
             .filter(Files::isRegularFile)
             .forEach((e) -> proccessFile(e));
    }

    /**
     * Adds all words from the path to the global vocabulary.
     * 
     * @param path
     *            {@link Path} to the file
     */
    private void proccessFile(Path path) {
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            StringBuilder builder = new StringBuilder();
            int ch = 0;
            while ((ch = reader.read()) != -1) {

                if (Character.isAlphabetic(ch)) {
                    builder.append((char) ch);
                } else if (builder.length() > 0) {

                    String text = builder.toString().toLowerCase();
                    StringPair pair = new StringPair(text);
                    if (stopWords.get(pair) == null) {
                        boolean notDuplicate = vocabular.add(pair);
                        if (notDuplicate == false) {
                            vocabular.get(pair).addFrequency();
                        }
                    }
                    builder.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the vocabulary size.
     * 
     * @return vocabulary size
     */
    public int getVocabularSize() {
        return this.vocabular.size();
    }

    /**
     * Returns the list of {@link ResultDocument} as the result to the query.
     * 
     * @param tempDocument
     *            {@link Document} used for comparison
     * @return results
     */
    public List<ResultDocument> getResult(Document tempDocument) {
        List<ResultDocument> results = new ArrayList<>();

        int vocabularSize = vocabular.size();

        tempDocument.setVector(vocabular);
        documents.add(tempDocument);

        double[] result = new double[vocabularSize];
        double[] denominator = new double[vocabularSize];
        int num = 0;
        for (StringPair pair : vocabular) {
            for (Document document : documents) {
                if (document == tempDocument)
                    continue;
                if (document.containsWord(pair)) {
                    denominator[num]++;
                }
            }
            ++num;
        }

        double numOfDocuments = (double) documents.size();
        for (Document document : documents) {

            for (int i = 0; i < vocabularSize; ++i) {
                result[i] = Math.log((numOfDocuments - 1) / denominator[i]);
            }

            int br = 0;
            for (StringPair pair : vocabular) {
                result[br] = document.wordFrequency(pair.getText()) * result[br];
                ++br;
            }

            document.setResultVector(result);
            for (int i = 0; i < vocabularSize; ++i) {
                result[i] = 0;
            }
        }

        for (int i = 0; i < numOfDocuments - 1; ++i) {
            Document document = documents.get(i);
            double similarity = tempDocument.getResultVector().cosine(document.getResultVector());

            if (similarity > THRESHOLD) {
                results.add(new ResultDocument(document, similarity));
            }
        }
        documents.remove(documents.size() - 1);
        Collections.sort(results);
        return results;
    }
}
