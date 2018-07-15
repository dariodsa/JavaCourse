package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Class {@link LocalizableAction} is abstract class and it extends
 * {@link AbstractAction}. It doesn't specifies what happens in the method
 * {@link #actionPerformed(java.awt.event.ActionEvent)} and that is on the child
 * classes to implement.
 * 
 * For more details see private attributes in the {@link JNotepadPP}.
 * 
 * @author dario
 *
 */
public abstract class LocalizableAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * {@link ILocalizationListener} listeners on the localization changes in the
     * local provider
     */
    private ILocalizationListener listener;

    /**
     * Constructs {@link LocalizableAction} with the key used for translation, and
     * local provider which offers the translation.
     * 
     * @param key
     *            key for translation
     * @param localProvider
     *            local provider
     */
    public LocalizableAction(String key, ILocalizationProvider localProvider) {

        Objects.requireNonNull(key);
        Objects.requireNonNull(localProvider);

        listener = new ILocalizationListener() {
            public void localizationChanged() {
                putValue(NAME, localProvider.getString(key));
                putValue(Action.SHORT_DESCRIPTION, localProvider.getString(key + "DES"));
            }
        };
        localProvider.addLocalizationListener(listener);
    }

}
