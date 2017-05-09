package sepm.ss17.e1428182.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.dao.reservationDAO;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class reservationDAOimpl implements reservationDAO {


    private static final Logger LOGGER = LoggerFactory.getLogger(boxDAOimpl.class);
    private static reservationDAOimpl instance;
    private static Connection connection;
    private PreparedStatement create;
    private PreparedStatement readAll;
    private PreparedStatement readPeriod;
    private PreparedStatement delete;
    private PreparedStatement update;


    private reservationDAOimpl getInstance() throws DAOException {
        if (instance == null)
            instance = new reservationDAOimpl();

        return instance;
    }


    public reservationDAOimpl() throws DAOException {
        try {

            connection = ConnectionSingleton.getConnection();
            create = connection.prepareStatement("INSERT INTO reservation(clientname, horsename, start, END)" +
                    " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            readAll = connection.prepareStatement("SELECT * from reservation where deleted = FALSE ");
            readPeriod = connection.prepareStatement("Select * from reservation where start BETWEEN ?  and ? and end between ? and ? and deleted = false;");
            delete = connection.prepareStatement("UPDATE reservation set deleted = TRUE where r_id = ?;");
            update = connection.prepareStatement("UPDATE reservation set start = ?, end = ?, horsename = ? , deleted = FALSE where r_id = ?;");

        } catch (SQLException e) {
            new DAOException(e.getMessage());
        }
    }

    @Override
    public Reservation reserve(Reservation reservation) throws DAOException {
        LOGGER.debug("METHOD reservationDAO create {}");

        try {
            create.setString(1, reservation.getClientname());
            create.setString(2, reservation.getHorsename());
            create.setTimestamp(3, reservation.getStart());
            create.setTimestamp(4, reservation.getEnd());

            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();
            if (rs.next())
                reservation.setR_id(rs.getInt(1));

        } catch (SQLException e) {
            LOGGER.error("CANNOT CREATE RESERVATION");
            throw new DAOException(e.getMessage());
        }

        return reservation;

    }

    @Override
    public List<Reservation> readAll() throws DAOException {
        LOGGER.debug("READING ALL RESERVATIONDAOIMPL {}");
        List<Reservation> reservations = new ArrayList<>();

        try {
            ResultSet rs = readAll.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getTimestamp(4),
                        rs.getTimestamp(5), rs.getString(3), rs.getBoolean(6)));
            }

        } catch (SQLException es) {
            LOGGER.error("READING ALL RESERVATION FAILED.");
            throw new DAOException(es.getMessage());
        }

        return reservations;
    }

    @Override
    public List<Reservation> showPeriod(Timestamp start, Timestamp end) throws DAOException {
        LOGGER.debug("READING Reservation Period RESERVATIONDAOIMPL {}");
        List<Reservation> reservations = new ArrayList<>();

        try {
            readPeriod.setTimestamp(1, start);
            readPeriod.setTimestamp(2, end);
            readPeriod.setTimestamp(3, start);
            readPeriod.setTimestamp(4, end);
            ResultSet rs = readPeriod.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getTimestamp(4),
                        rs.getTimestamp(5), rs.getString(3), rs.getBoolean(6)));
            }

        } catch (SQLException es) {
            LOGGER.error("READING Reservation Period FAILED.");
            throw new DAOException(es.getMessage());
        }

        return reservations;
    }

    @Override
    public boolean delete(Reservation reservation) throws DAOException {

        LOGGER.debug("delete Reservation DAo{}");
        try {
            delete.setInt(1, reservation.getR_id());
            delete.executeUpdate();
            return true;
        } catch (SQLException es) {
            LOGGER.error("COULDNT DELETE THE RESERVATION!");
        }
        return false;
    }

    @Override
    public void update(Reservation reservation) throws DAOException {

        LOGGER.debug("update Reservation DAo{}");

        try {
            update.setTimestamp(1, reservation.getStart());
            update.setTimestamp(2, reservation.getEnd());
            update.setString(3, reservation.getHorsename());
            update.setInt(4, reservation.getR_id());
            update.executeUpdate();
        }catch (SQLException es)
        {
            LOGGER.error("COULDNT UPDATE THE RESERVATION!");
        }

    }

}
