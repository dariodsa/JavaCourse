package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class Inicijalizacija implements {@link ServletContextListener} which sets
 * basic information about server and its connection to the database. It will
 * create database if it doesn't find and it will fill with some given data so
 * that demo user can test the application without so many troubles.
 * 
 * @author dario
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

    /**
     * host identifier in properties file
     */
    private static final String HOST = "host";

    /**
     * port number identifier in properties file
     */
    private static final String PORT = "port";

    /**
     * database name identifier in properties file
     */
    private static final String DBNAME = "name";

    /**
     * user name identifier in properties file
     */
    private static final String USERNAME = "user";

    /**
     * password identifier in properties file
     */
    private static final String PASSWORD = "password";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dbName = properties.getProperty(DBNAME);
        String userName = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        String port = properties.getProperty(PORT);
        String host = properties.getProperty(HOST);
        Objects.requireNonNull(dbName, "Database name is not set.");
        Objects.requireNonNull(host, "Host name is not set.");
        Objects.requireNonNull(port, "Port is not set.");
        Objects.requireNonNull(userName, "Username is not set.");
        Objects.requireNonNull(password, "Password is not set.");

        String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + userName + ";password="
                + password + ";create=true";

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
        }
        cpds.setJdbcUrl(connectionURL);

        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

        System.out.println("Server was successfully started.");

        Properties dbProperties = new Properties();
        dbProperties.setProperty("user", userName);
        dbProperties.setProperty("password", password);
        Connection con = null;
        try {
            con = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        boolean pollsExsist = false;
        boolean pollOptionsExsist = false;

        boolean pollEmpty = false;

        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null, null, null);
            System.err.println(res.next());
            while (res.next()) {
                System.err.println("   " + res.getString("TABLE_CAT") + ", " + res.getString("TABLE_SCHEM") + ", "
                        + res.getString("TABLE_NAME") + ", " + res.getString("TABLE_TYPE") + ", "
                        + res.getString("REMARKS"));
                if (res.getString("TABLE_NAME").compareTo("POLLS") == 0) {
                    pollsExsist = true;
                } else if (res.getString("TABLE_NAME").compareTo("POLLOPTIONS") == 0) {
                    pollOptionsExsist = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!pollsExsist) {
            try {
                con.createStatement()
                        .execute("CREATE TABLE Polls" + "    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                                + "    title VARCHAR(150) NOT NULL," + "    message CLOB(2048) NOT NULL" + ")");
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.exit(0);
            }
        }

        if (!pollOptionsExsist) {
            try {
                con.createStatement()
                        .execute("CREATE TABLE PollOptions" + "    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                                + "    optionTitle VARCHAR(100) NOT NULL," + "    optionLink VARCHAR(150) NOT NULL,"
                                + "    pollID BIGINT," + "    votesCount BIGINT,"
                                + "    FOREIGN KEY (pollID) REFERENCES Polls(id)" + ")");
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.exit(0);
            }
        }

        pollEmpty = checkIfTableIsEmpty(con, "POLLS");

        if (pollEmpty) {
            try {
                fillDatabase(con, Paths.get("src/main/webapp/WEB-INF/sql/poll1.txt"));
                fillDatabase(con, Paths.get("src/main/webapp/WEB-INF/sql/poll2.txt"));
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }

    }

    /**
     * Method checks if the table under given name is empty. If it is it will return
     * true, false otherwise.
     * 
     * @param con
     *            SQL connection
     * @param tableName
     *            table name
     * @return true if table is empty, false otherwise
     */
    private boolean checkIfTableIsEmpty(Connection con, String tableName) {
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM " + tableName);
                ResultSet rst = pst.executeQuery()) {

            while (rst.next()) {
                return false;
            }
            rst.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
                .getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method fills database with sql data which will be found in the given path.
     * 
     * @param con
     *            sql connection
     * @param path
     *            path to the file
     * @throws SQLException
     *             SQL error
     * @throws IOException
     *             can't open file in the given path
     */
    private void fillDatabase(Connection con, Path path) throws SQLException, IOException {
        PreparedStatement pst = null;

        List<String> lines = Files.readAllLines(path);
        if (lines.size() < 2) {
            throw new RuntimeException("Unexpected number of lines in the given file.");
        }
        String pollCreateSql = lines.get(0);

        pst = con.prepareStatement(pollCreateSql, Statement.RETURN_GENERATED_KEYS);
        pst.executeUpdate();
        System.out.println(lines.get(0));
        System.out.println(pst.getGeneratedKeys().next());
        long pollId = pst.getGeneratedKeys().getLong(1);

        for (int i = 1; i < lines.size(); ++i) {
            PreparedStatement pstOption = con.prepareStatement(lines.get(i), Statement.RETURN_GENERATED_KEYS);
            System.out.println(lines.get(i));

            pstOption.setLong(1, pollId);
            pstOption.executeUpdate();
        }
    }

}