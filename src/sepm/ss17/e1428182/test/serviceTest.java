package sepm.ss17.e1428182.test;

import javafx.beans.property.SimpleStringProperty;
import org.h2.server.Service;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.*;

import sepm.ss17.e1428182.dao.impl.ConnectionSingleton;
import sepm.ss17.e1428182.dao.impl.boxDAOimpl;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.service.impl.*;
import sepm.ss17.e1428182.service.*;
import sepm.ss17.e1428182.domain.*;
import sepm.ss17.e1428182.exception.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class serviceTest {

    private static Connection connection;
    private static ReservationServiceImpl reservationService;
    private static ReceiptServiceImpl receiptService;
    private static BoxServiceImpl boxService;

    private static Box box = new Box(false, 1, new SimpleStringProperty("TestFile"), new SimpleStringProperty("TestHorse"), 12, 11, "inside", true, "sawdust", "/Users/Sunny/Coding/Java/Einzelphase/src/resources/blackhouse.jpg");

    private static Reservation reservation = new Reservation(1,"TestName", Timestamp.valueOf("2019-03-22 12:00:00.0"), Timestamp.valueOf("2019-03-22 18:00:00.0"), "TestHorse", false);
    private static  Receipt receipt;

    @BeforeClass
    public static void setUpBeforeClass() throws SQLException, DAOException, ServiceException
    {

        connection = ConnectionSingleton.getConnection();
        connection.setAutoCommit(false);
        boxService = BoxServiceImpl.getInstance();
        receiptService = ReceiptServiceImpl.getInstance();
        reservationService = ReservationServiceImpl.getInstance();
    }

    /**
     *
     * @throws SQLException
     * @throws ServiceException
     *
     * Creates a new Receipt
     */
    @Test
    public void AddReceipt() throws SQLException, ServiceException
    {

       int size = receiptService.showReceipts().size();
        boxService.createBox(box);
        reservationService.createReservation(reservation);
        receiptService.create(reservation, box);
        assertEquals(size+1,receiptService.showReceipts().size());

    }

    @Test(expected = ServiceException.class)
    public void checkifBoxisavailable() throws SQLException, ServiceException
    {
        boxService.createBox(box);
        reservationService.createReservation(reservation);
        reservationService.checkAvailable(reservation.getStart(), reservation.getEnd(), reservation.getHorsename());


    }

    @Test(expected = NullPointerException.class)
    public void checkReceitcreation() throws SQLException, ServiceException
    {
        receiptService.create(null, null);
    }

    @Test(expected = ServiceException.class)
    public void checkReceiptcreation() throws SQLException, ServiceException
    {
        receiptService.create(reservation, box);
    }

    /**
     * Deleting a Reservation with null
     * @throws ServiceException
     */
    @Test(expected = NullPointerException.class)
    public void deleteReservationwithNUll() throws ServiceException
    {
        reservationService.delete(null);
    }


    /**
     * This Test gives a Reservation given the Timespan
     * @throws ServiceException
     * @throws SQLException
     */
    @Test
    public void searchReservationgivenTime() throws ServiceException, SQLException
    {
        reservationService.createReservation(reservation);

        assertEquals(1,reservationService.timedReservation(reservation.getStart(), reservation.getEnd()).size());
    }


    /**
     * This Test gives a Reservation given the Timespan which is not in the database so Service Exception is Thrown
     * @throws ServiceException
     * @throws SQLException
     */
    @Test(expected = ServiceException.class)
    public void searchReservationwrongTime() throws ServiceException
    {
        reservationService.timedReservation(reservation.getStart(), reservation.getEnd()).size();
    }


    @Test(expected = ServiceException.class)
    public void upDateTimeofReservation() throws ServiceException, SQLException
    {
        Timestamp test = reservation.getStart();
        reservationService.createReservation(reservation);
        reservation.setStart(reservation.getEnd());
        reservation.setEnd(test);
        reservationService.update(reservation);
    }

    /**
     * This Method Tests if the calculation of Total price is correct given dailyrate per hour of Box and TImespan
     */
    @Test
    public void calculatePrice() throws SQLException, ServiceException
    {
       long milliseconds = reservation.getEnd().getTime() - reservation.getStart().getTime();
        int seconds = (int) milliseconds / 1000;
        int hours = seconds / 3600;
        double price = hours*box.getDayprice();
        boxService.createBox(box);
        reservationService.createReservation(reservation);
        receiptService.create(reservation, box);
        assertEquals(price,receiptService.showReceipts().get(receiptService.showReceipts().size()-1).getTotalprice(), 73);



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
