package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Interface {@link ILocalizationProvider} specifies what should localization
 * provider should implement, such as adding or removing the listeners or
 * getting the translated word from the key.
 * 
 * @author dario
 *
 */
public interface ILocalizationProvider {
    /**
     * Adds new {@link ILocalizationListener} to the list of all listeners.
     * 
     * @param listener
     *            new listeners
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Removes given listener from the listeners list.
     * 
     * @param listener
     *            listeners which will be removed from list
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Returns translated word under given key.
     * 
     * @param key
     *            key under which translated word is stored
     * @return translated word or phrase
     */
    String getString(String key);
}
