package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Objects;

/**
 * Class {@link LocalizationProviderBridge} extends
 * {@link AbstractLocalizationProvider} and implements its methods
 * {@link #getString(String)} which returns translation under given key. It can
 * connect to the given object parent in the constructor which means that adds
 * itself on it list of listeners. When that object notifies about changes it
 * fires all that changes to the connected on this object, so this object
 * servers as proxy to all object which listen on it.
 * 
 * @author dario
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * connection property
     */
    private boolean connected;

    /**
     * {@link ILocalizationProvider} parent on which object can connect or
     * disconnect from listening
     */
    private ILocalizationProvider parent;

    /**
     * listener which on localization changes fires that to its listeners.
     */
    private ILocalizationListener listener;

    /**
     * Constructs {@link LocalizationProviderBridge} with
     * {@link ILocalizationProvider} parent as one on which can connect and listen
     * to all changes.
     * 
     * @param parent
     *            {@link ILocalizationProvider} parent
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        Objects.requireNonNull(parent);

        this.parent = parent;
        this.listener = () -> fire();

    }

    /**
     * It connects it to the {@link ILocalizationProvider} parent and adds it to it
     * list of listeners.
     */
    public void connect() {
        if (!connected) {
            this.connected = true;
            parent.addLocalizationListener(listener);
        }
    }

    /**
     * It disconnects it from the {@link ILocalizationProvider} parent and it
     * removes itself from it list of listeners.
     */
    public void disconnect() {
        if (connected) {
            this.connected = false;
            parent.removeLocalizationListener(listener);
        }

    }

    @Override
    public String getString(String key) {
        Objects.requireNonNull(key);

        return parent.getString(key);
    }

}
