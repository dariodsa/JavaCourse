package hr.fer.zemris.java.hw11.jnotepadpp.localization.component;

import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Class {@link LJMenu} extends JMenu so it is possible to add it to the JFrame
 * because it is also {@link JComponent}. It specifies in the constructor how
 * should JMenu should be named.
 * 
 * @author dario
 *
 */
public class LJMenu extends JMenu {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs {@link LJMenu} with key from which translated word will be set as
     * the name visible to the user. It subscribed to the changes in the language on
     * the local provider.
     * 
     * @param key
     *            key of the menu, from which translation is possible
     * @param lp
     *            localization provider from which translation is possible
     */
    public LJMenu(String key, ILocalizationProvider lp) {

        super();

        Objects.requireNonNull(key);
        Objects.requireNonNull(lp);

        ILocalizationListener listener = new ILocalizationListener() {
            public void localizationChanged() {
                setText(lp.getString(key));
            }
        };

        lp.addLocalizationListener(listener);
    }
}
