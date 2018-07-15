package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link AbstractLocalizationProvider} is an abstract class which implements
 * {@link ILocalizationProvider}. You can add {@link ILocalizationListener}
 * listeners by method {@link #addLocalizationListener(ILocalizationListener)}
 * or you can remove one with
 * {@link #removeLocalizationListener(ILocalizationListener)}. It also supports
 * fire method which notifies all listeners about subject's new state or in this
 * case a new language.
 * 
 * @author dario
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * list of listeners
     */
    private List<ILocalizationListener> listeners = new ArrayList<>();

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        Objects.requireNonNull(listener);

        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        Objects.requireNonNull(listener);

        this.listeners = new ArrayList<>(listeners);
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners on this subject that it's state is changed.
     */
    public void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }
}
