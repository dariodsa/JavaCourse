package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Interface {@link ILocalizationListener} specifies what should every listener
 * on {@link ILocalizableAction} should implement in order that subject can
 * communicate with them.
 * 
 * @author dario
 *
 */
public interface ILocalizationListener {

    /**
     * Method from which subject says to the listener that the new language was set.
     */
    void localizationChanged();
}
