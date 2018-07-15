package hr.fer.zemris.java.webserver;

/**
 * Interface {@link IWebWorker} specifies what should
 * every worker implement. They only should implement method 
 * processRequest in which they should specify what they 
 * should do.
 * @author dario
 *
 */
public interface IWebWorker {
    
    /**
     * ProcessRequest method specifies what is every worker responsibility. In
     * that method worker should do their work and send result to the 
     * request context's output stream.
     * @param context {@link RequestContext} used for showing result of the worker
     * @throws Exception exception.
     */
    public void processRequest(RequestContext context) throws Exception;
}
