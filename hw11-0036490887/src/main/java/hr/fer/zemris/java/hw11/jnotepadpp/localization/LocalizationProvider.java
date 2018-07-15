package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class {@link LocalizationProvider} extends
 * {@link AbstractLocalizationProvider} and implements all methods from parent
 * class. It sets a Croatian language as the default one. It can change language
 * with method {@link #setLanguage(String)} or you can get current one with
 * {@link #getString(String)}.
 * 
 * @author dario
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * current language
     */
    private String language;

    /**
     * bundle, contains locale-specific objects
     */
    private ResourceBundle bundle;

    /**
     * private static instance, used in singleton design principle
     */
    private static LocalizationProvider instance = new LocalizationProvider();

    /**
     * Creates new {@link LocalizationProvider} and sets default language to be
     * Croatian.
     */
    private LocalizationProvider() {
        setLanguage("hr");
    }

    /**
     * Returns static instance of the object.
     * 
     * @return static instance of the object
     */
    public static LocalizationProvider getInstance() {
        return LocalizationProvider.instance;
    }

    @Override
    public String getString(String key) {
        Objects.requireNonNull(key);

        return bundle.getString(key);
    }

    /**
     * Returns current language in the {@link LocalizationProvider}.
     * 
     * @return current language used
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Sets new language in the {@link LocalizationProvider} and it notifies all its
     * listeners about that change.
     * 
     * @param language
     *            new language
     */
    public void setLanguage(String language) {

        Objects.requireNonNull(language);

        this.language = language;
        Locale locale = Locale.forLanguageTag(language);

        this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.translation", locale);
        this.fire();
    }

}
