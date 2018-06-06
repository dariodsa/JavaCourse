package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.util.function.Function;

import javax.swing.JTextArea;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizableAction;

/**
 * Class {@link ToolsAction} extends {@link LocalizableAction} and performs
 * action which is specified in the constructor. Action will be performed on
 * selected characters and on the current document, which will be found thanks
 * to {@link MultipleDocumentModel}.
 * 
 * @author dario
 *
 */
public class ToolsAction extends LocalizableAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * function which will be applied on characters
     */
    private Function<Character, Character> function;
    /**
     * model with documents
     */
    private MultipleDocumentModel model;

    /**
     * Constructs {@link ToolsAction} with specification such as key and
     * localProvider which are used for international experience, and model with
     * function which will be performed on selected characters.
     * 
     * @param key
     *            key used to international translation
     * @param localProvider
     *            local provider from translation is got
     * @param function
     *            function which will be applied on selected characters
     * @param model
     *            model with documents
     */
    public ToolsAction(String key, ILocalizationProvider localProvider, Function<Character, Character> function,
            MultipleDocumentModel model) {
        
        super(key, localProvider);
        this.model = model;
        this.function = function;
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JTextArea area = model.getCurrentDocument().getTextComponent();

        String text = area.getText();
        char[] newText = new char[text.length()];
        Caret caret = area.getCaret();

        int dot = caret.getDot();
        int mark = caret.getMark();
        int len = Math.abs(dot - mark);
        int mini = Math.min(dot, mark);

        for (int i = 0, length = text.length(); i < length; ++i) {
            if (i >= mini && i < mini + len) {
                newText[i] = function.apply(text.charAt(i));
            } else {
                newText[i] = text.charAt(i);
            }
        }
        area.setText(new String(newText));
        area.setCaretPosition(mini + len);
        return;
    }

}
