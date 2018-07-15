package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Storage connections through the database are in the {@link ThreadLocal}
 * object. {@link ThreadLocal} is actual a map which keys are thread
 * identificators which are making operation on the map.
 * 
 * @author dario
 *
 */
public class SQLConnectionProvider {

    /**
     * thread pool of connections
     */
    private static ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Establish connection for current thread ( or it erase it from map it the
     * argument is <code>null</code>.
     * 
     * @param con
     *            connection through the database
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            connections.remove();
        } else {
            connections.set(con);
        }
    }

    /**
     * Get the connection that current thread can use it.
     * 
     * @return connection through the database
     */
    public static Connection getConnection() {
        return connections.get();
    }

}