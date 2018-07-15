package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizableAction;

/**
 * Class {@link UniqueActions} extends {@link LocalizableAction} and implements
 * its constructor and method {@link #actionPerformed(ActionEvent)} in which
 * removes all non unique selected lines from the current document and sorts the
 * rest of them and shows that in the text component of the current text
 * component.
 * 
 * @author dario
 *
 */
public class UniqueActions extends LocalizableAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * model under modification is made
     */
    private MultipleDocumentModel model;

    /**
     * sets used for sorting and getting unique lines
     */

    private Map<String, Boolean> set = new LinkedHashMap<>();

    /**
     * Constructs {@link UniqueActions} with key and local provider need for
     * translation and reference to the model on which modification will be
     * performed.
     * 
     * @param key
     *            key needed for translation
     * @param localProvider
     *            local provide offers translation
     * @param model
     *            model under which current document modification is done
     */
    public UniqueActions(String key, ILocalizationProvider localProvider, MultipleDocumentModel model) {
        super(key, localProvider);
        
        Objects.requireNonNull(model);
        setEnabled(false);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        set.clear();

        JTextArea area = model.getCurrentDocument().getTextComponent();
        Caret caret = area.getCaret();
        StringBuilder builder = new StringBuilder();

        int dot = caret.getDot();
        int mark = caret.getMark();

        int mini = Math.min(dot, mark);
        int maxi = Math.abs(dot - mark) + mini;

        try {
            int firstLine = area.getLineOfOffset(mini);
            int endLine = area.getLineOfOffset(maxi);
            int firstOffset = area.getLineStartOffset(firstLine);
            int endOffset = area.getLineEndOffset(endLine);

            if (endOffset >= area.getText().length()) {
                endOffset = area.getText().length() - 1;
            }
            String[] lines = area.getText().substring(firstOffset, endOffset).split("\\n");
            for (String line : lines) {
                set.put(line, true);
            }
            for (int i = 0; i < firstOffset; ++i) {
                builder.append(area.getText().charAt(i));
            }
            //System.out.println("----");
            for (String line : set.keySet()) {
                //System.out.println(line);
                builder.append(line);
                if(set.keySet().iterator().hasNext()) {
                    builder.append("\n");
                }
            }

            for (int i = endOffset; i < area.getText().length(); ++i) {
                builder.append(area.getText().charAt(i));
            }
            area.setText(builder.toString());

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}
