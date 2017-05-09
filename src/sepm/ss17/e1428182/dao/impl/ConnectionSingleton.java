package sepm.ss17.e1428182.dao.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.Server;


public class ConnectionSingleton {

    private final String driver = "org.h2.Driver";
    private final String URL = "jdbc:h2:tcp://localhost/~/pensionhouse;";
    private final String user = "sa";
    private final String password = "";
    private static Connection connection = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionSingleton.class);


    /**
     * This method accesses the h2 database and logs in using the name and password defined in the followings.
     *
     * @throws DAOException in case a problem with the logging into the h2 database.
     */
    private ConnectionSingleton() throws DAOException {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            LOGGER.error("ERROR: failed to load H2 JDBC driver.", e);
        }

        try {

            connection = DriverManager.getConnection(URL, user, password);


        } catch (SQLException eSQL) {
            throw new RuntimeException();
        }
    }

    /**
     * This method establishes a connection to the database.
     *
     * @return the established connection.
     * @throws DAOException if connection does not establish due to any problems.
     */
    public static Connection getConnection() throws DAOException {
        LOGGER.info("Establishing connection to the database.");

        if (connection == null) {
            new ConnectionSingleton();
            LOGGER.info("Connection successfully established!");
        }
        return connection;
    }

    public static void closeConnection() throws DAOException {
        LOGGER.info("Closing the existing connection to the database.");

        if(connection!=null){
            try {
                connection.close();
                LOGGER.info("Connection to the database closed successfully.");
            } catch (SQLException e) {
                LOGGER.error("Connection has not closed!");
                throw new DAOException(e.getMessage());
            }
        }
    }





}
