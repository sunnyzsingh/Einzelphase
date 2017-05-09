package sepm.ss17.e1428182.test;


import javafx.beans.property.SimpleStringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.*;

import sepm.ss17.e1428182.dao.impl.ConnectionSingleton;
import sepm.ss17.e1428182.dao.impl.boxDAOimpl;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class boxDAOTest {




    private static Connection connection;
    private static boxDAOimpl boxDAO;
    private static Box box;


    @BeforeClass
    public static void setBeforeClass() throws SQLException, DAOException
    {

        connection = ConnectionSingleton.getConnection();
        connection.setAutoCommit(false);
        boxDAO = boxDAOimpl.getInstance();

        box = new Box(false, 1, new SimpleStringProperty("TestFile"), new SimpleStringProperty("TestHorse"), 12, 11, "inside", true, "sawdust", "/Users/Sunny/Coding/Java/Einzelphase/src/resources/blackhouse.jpg");

    }
    /**
     * This Test creates a new Box in Database
     */
    @Test
    public void createBoxinDatabase() throws SQLException, DAOException
    {
        int size = boxDAO.readAll().size();
        boxDAO.create(box);
        assertEquals(size+1, boxDAO.readAll().size());
    }


    /**
     * This test updates an existing horse in
     * the database.
     *
     **/
    @Test
    public void UpdateHorseName() throws SQLException, DAOException
    {
        boxDAO.create(box);
        box.setHorsename(new SimpleStringProperty("Many"));
        boxDAO.update(box);
        assertEquals("Many", boxDAO.readAll().get(boxDAO.readAll().size()-1).getHorsename().getValue());
        box.setHorsename(new SimpleStringProperty("TestHorse"));
        boxDAO.update(box);


    }

    /**
     * This test first creates a Box object and checks the number of
     * all Boxes, then deletes the Box from database and again checks the
     * number.
     *
     **/
    @Test
    public void deleteBox() throws SQLException, DAOException
    {
        int size = boxDAO.readAll().size();
        boxDAO.create(box);
        assertEquals(size+1, boxDAO.readAll().size());
        boxDAO.delete(box);
        assertEquals(size+1, boxDAO.readAll().size());
    }


    /**
     * This test tries to save an invalid value
     * in the database. A NullPointerException will be thrown.
     **/
    @Test(expected = NullPointerException.class)
    public void createWithNull_shouldThrowNullPointerException() throws SQLException, DAOException {
        boxDAO.create(null);


    }


    /**
     * This test tries to search a Box, which has not been
     * created yet and does not exist in the database.
     **/
    @Test(expected = DAOException.class)
    public void readByBoxname_shouldThrowDAOException() throws SQLException, DAOException {
        boxDAO.searchbyboxname(box.getBoxname().toString());
    }

    /**
     * This test tries to select Box matching the given
     * name, which in empty and does not exist in the database.
     **/
    @Test(expected = DAOException.class)
    public void readByName_shouldNotSelectAnyBox() throws SQLException, DAOException {
        assertTrue(boxDAO.searchbyboxname("") == null);
    }



    /**
     * This test tries to update an invalid value (NULL value)
     * in the database. A NullPointerException will be thrown.
     **/
    @Test(expected = NullPointerException.class)
    public void updateWithNull_shouldThrowNullPointerException() throws SQLException, DAOException {
        boxDAO.update(null);
    }


    @After
    public void tearDown() throws SQLException {
        connection.rollback();
    }

    @AfterClass
    public static void tearDownAfterClass() throws SQLException {
        connection.close();
    }

}
