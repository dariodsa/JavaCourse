package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JFrame;

/**
 * Class {@link FormLocalizationProvider} extends
 * {@link LocalizationProviderBridge} which connects to the given {@link JFrame}
 * and disconnect from it when the {@link JFrame} is closed. It is a proxy in
 * this design pattern.
 * 
 * @author dario
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Constructs {@link FormLocalizationProvider} with JFrame on which the object
     * will connect and disconnect when {@link JFrame} will be closed.
     * 
     * @param provider
     *            it is send as the parameter in the parent's constructor method
     * @param frame
     *            {@link JFrame} on which the object is connected
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);
        Objects.requireNonNull(frame);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent event) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent event) {
                disconnect();
            }

        });
    }

}
