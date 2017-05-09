package sepm.ss17.e1428182.dao;


import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.domain.Reservation;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface reservationDAO {


    /**This Method creates the given Reservations in the database
     *
     * @param reservation
     * @return
     * @throws DAOException
     */
    Reservation reserve(Reservation reservation) throws DAOException;

    /** This Method reads all the Reservations that are in the database
     *
     * @return
     * @throws DAOException
     */
    List<Reservation> readAll() throws DAOException;

    /** This Method gives the List of Reservations given the time span
     *
     * @param start
     * @param end
     * @return
     * @throws DAOException
     */
    List<Reservation>showPeriod(Timestamp start, Timestamp end) throws DAOException;


    /** This Method deletes the reservations
     *
     * @param reservation
     * @return
     * @throws DAOException
     */
    boolean delete(Reservation reservation) throws DAOException;


    /**This Methods Updates the informations of the Reservation
     *
     * @param reservation
     * @throws DAOException
     */
    void update(Reservation reservation) throws DAOException;








}
