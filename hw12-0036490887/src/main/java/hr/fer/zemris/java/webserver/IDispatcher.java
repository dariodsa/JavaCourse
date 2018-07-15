package hr.fer.zemris.java.webserver;

/**
 * Interface IDispatcher specifies how should every 
 * request with urlPath be performed.
 * @author dario
 *
 */
public interface IDispatcher {
    /**
     * Request how should given url the performed.
     * @param urlPath url path
     * @throws Exception exception
     */
    void dispatchRequest(String urlPath) throws Exception;
}
