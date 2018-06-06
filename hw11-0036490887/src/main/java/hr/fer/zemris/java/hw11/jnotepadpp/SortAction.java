package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;

/**
 * Class {@link SortAction} extends {@link LocalizableAction} and implements its
 * constructor and method {@link #actionPerformed(ActionEvent)} in which sorts
 * all selected lines in the currently selected document and updates it's text
 * component.
 * 
 * @author dario
 *
 */
public class SortAction extends LocalizableAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * function which is applied on the result of the comparator, so you can think
     * that of like final result will be composition of those two functions
     */
    private Function<Integer, Integer> function;

    /**
     * current {@link MultipleDocumentModel} model
     */
    private MultipleDocumentModel model;

    /**
     * first offset of the selected text
     */
    private int poc;

    /**
     * last offset of the selected text
     */
    private int end;

    /**
     * Constructs {@link SortAction} with given parameters.
     * 
     * @param key
     *            used for translation
     * @param localProvider
     *            used for translation
     * @param function
     *            function used in sorting
     * @param model
     *            current {@link MultipleDocumentModel} model
     */
    public SortAction(String key, ILocalizationProvider localProvider, Function<Integer, Integer> function,
            MultipleDocumentModel model) {
        super(key, localProvider);

        Objects.requireNonNull(function);
        Objects.requireNonNull(model);

        this.function = function;
        this.model = model;
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        JTextArea area = model.getCurrentDocument().getTextComponent();
        String text = area.getText();

        Locale local = new Locale(LocalizationProvider.getInstance().getLanguage());
        Collator collator = Collator.getInstance(local);
        //r", "sun.util.resources", "sun.text.resources"),
        
        List<String> lines = getLines(area);
        for(String line : lines) {
        //    System.out.println(line);
        }
        //System.out.println("-----------s");
        
        Collections.sort(lines, new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                int res = collator.compare(s1, s2);
                return function.apply(res);
            }
        });

        StringBuilder newText = new StringBuilder();

        for (int i = 0; i < poc; ++i) {
            newText.append(text.charAt(i));
        }

        
        for (String line : lines) {

            for (int i = 0; i < line.length(); ++i) {
                newText.append(line.charAt(i));
            }
            if (lines.iterator().hasNext()) {
                newText.append('\n');
            }
        }

        for (int i = end ; i < text.length(); ++i) {
            newText.append(text.charAt(i));
        }

        area.setText(newText.toString());
    }

    /**
     * Returns selected lines from the given {@link JTextArea} area.
     * 
     * @param area
     *            area from which selected lines will be got
     * @return list of string, or lines which are selected in the given
     *         {@link JTextArea} area
     */
    private List<String> getLines(JTextArea area) {
        Objects.requireNonNull(area);

        List<String> lines = new ArrayList<>();
        Caret caret = area.getCaret();
        String text = area.getText();

        int dot = caret.getDot();
        int mark = caret.getMark();
        int mini = Math.min(dot, mark);
        int maxi = Math.max(dot, mark);

        try {
            int firstLine = area.getLineOfOffset(mini);
            int endLine = area.getLineOfOffset(maxi);
            int firstOffset = area.getLineStartOffset(firstLine);
            int endOffset = area.getLineEndOffset(endLine);

            if (endOffset >= text.length()) {
                endOffset = text.length();
            }
            
            poc = firstOffset;
            end = endOffset;

            String[] linesSplited = text.substring(firstOffset, endOffset ).split("\\n");
            for (String line : linesSplited) {
                lines.add(line);
            }

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
