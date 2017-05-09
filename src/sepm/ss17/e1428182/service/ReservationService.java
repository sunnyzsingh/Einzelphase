package sepm.ss17.e1428182.service;



import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.exception.ServiceException;

import java.sql.Timestamp;
import java.util.List;


public interface ReservationService {


    /** This Method creates a Reservation in the Database
     *
     * @param reservation
     * @return
     * @throws ServiceException
     */
    Reservation createReservation(Reservation reservation) throws ServiceException;

    /** This method list all Reservations in the database
     *
     * @return
     * @throws ServiceException
     */
    List<Reservation> showAllReservation() throws ServiceException;

    /**
     * This Method gives the list of Reservations given the timespan
     * @param start
     * @param end
     * @return
     * @throws ServiceException
     */
    List<Reservation> timedReservation(Timestamp start, Timestamp end) throws ServiceException;

    /** This Method deletes the given Reservation from the database
     *
     * @param reservation
     * @return
     * @throws ServiceException
     */
    boolean delete(Reservation reservation) throws ServiceException;

    /**
     *
     * @param reservation
     * @throws ServiceException
     */
    void update(Reservation reservation) throws ServiceException;

    /** This Methods validates the Informations of the given Reservation
     *
     * @param reservation
     * @throws ServiceException
     */
    void validate(Reservation reservation) throws ServiceException;

    /** This Method checks if the Box is available in the given timespan
     *
     * @param startTimeStamp
     * @param endTimeStamp
     * @throws ServiceException
     */
    void checkAvailable(Timestamp startTimeStamp, Timestamp endTimeStamp, String horsename) throws ServiceException;

    /**
     * This Method created a Statistic on which Day of the Week a Box has been reserved
     * @param horsename
     * @param weekday
     * @return
     * @throws ServiceException
     */
    XYChart.Series checkDayoftheWeek(String horsename, ObservableList<String> weekday) throws ServiceException;


    /**
     *
     * @return
     * @throws ServiceException
     */
    XYChart.Series bestbooked(ObservableList<String> boxname) throws ServiceException;



}
