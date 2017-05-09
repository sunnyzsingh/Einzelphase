package sepm.ss17.e1428182.service.impl;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.h2.server.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.dao.boxDAO;
import sepm.ss17.e1428182.dao.impl.boxDAOimpl;
import sepm.ss17.e1428182.dao.impl.reservationDAOimpl;
import sepm.ss17.e1428182.dao.reservationDAO;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.ReservationService;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private static ReservationServiceImpl instance;
    private reservationDAO reservationDAO;
    Calendar cal = Calendar.getInstance();
    private BoxService box;

    public static ReservationServiceImpl getInstance() throws ServiceException
    {
        if (instance == null)
        {
                instance = new ReservationServiceImpl();
        }

        return instance;

    }


    private ReservationServiceImpl() throws ServiceException
    {
        try
        {
            reservationDAO= new reservationDAOimpl();
            box = BoxServiceImpl.getInstance();

        }catch (DAOException es)
        {
            throw new ServiceException(es.getMessage());
        }

    }



    @Override
    public Reservation createReservation(Reservation reservation) throws ServiceException {

        LOGGER.debug("SERVICE RESERVATION METHOD {}");

        validate(reservation);

        try
        {
            return reservationDAO.reserve(reservation);
        }catch (DAOException e)
        {
            LOGGER.error("COULDN'T CREATE RESERVATION");
            throw new ServiceException(e.getMessage());
        }


    }

    @Override
    public List<Reservation> showAllReservation() throws ServiceException {
        LOGGER.debug("List all Reservation Service Method {}");

        try
        {
            if (reservationDAO.readAll().size() == 0)
                throw new ServiceException("Database is Empty");
            return reservationDAO.readAll();
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }

    }

    @Override
    public List<Reservation> timedReservation(Timestamp start, Timestamp end) throws ServiceException {
        LOGGER.debug("List all Reservation for defined period Service Method {}");

        try
        {
            if (reservationDAO.showPeriod(start,end).size() == 0)
                throw new ServiceException("Database is Empty");
            return reservationDAO.showPeriod(start, end);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public boolean delete(Reservation reservation) throws ServiceException {
        LOGGER.debug("Delete Reservation  Service Method{}");

        try
        {
            if (reservationDAO.delete(reservation))
            {
                LOGGER.info("Selected Reservation deleted!");
                return true;
            }
            else
            {
                LOGGER.info("Couldnt delete the selected Reservation");
                return false;
            }
        }catch(DAOException es)
        {
            LOGGER.error("Failed to Delete!");
            throw  new ServiceException("Deleting Failed");
        }

    }

    @Override
    public void update(Reservation reservation) throws ServiceException {
        LOGGER.debug("Updating Reservation in Service {}");

        validate(reservation);
        try
        {
            reservationDAO.update(reservation);
        }catch (DAOException es)
        {
            LOGGER.error("Failed to Update!");
            throw  new ServiceException("Updating failed");
        }
    }

    @Override
    public void validate(Reservation reservation) throws ServiceException {

        LOGGER.debug("Validating Reservation Objects");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (isNumeric(reservation.getClientname()))
            throw new ServiceException("Client Name must be String");
        if (isNumeric(reservation.getHorsename()))
            throw new ServiceException("Horse Name must be String");
        if (reservation.getClientname() == null)
            throw new ServiceException("Client name is null");
        if (reservation.getHorsename().isEmpty())
            throw new ServiceException("Horsename name is empty");
        if(reservation.getHorsename() == null)
            throw new ServiceException("Horse name is null");
        if(reservation.getClientname().isEmpty())
            throw new ServiceException("Clientname name is Empty");
        if (reservation.getStart() == null)
            throw new ServiceException("Start Time is null");
        if (reservation.getStart().toString().isEmpty())
            throw new ServiceException("Start Time is Empty");
        if (reservation.getEnd()  == null)
            throw new ServiceException("End Time is null");
        if (reservation.getEnd().toString().isEmpty())
            throw new ServiceException("End Time is null");
        if (reservation.getStart().after(reservation.getEnd()))
            throw new ServiceException("Start Time must be before End Time");
        if(reservation.getStart().equals(reservation.getEnd()))
            throw new ServiceException("Start Time and End Time cannot be same");
        if (reservation.getStart().before(timestamp))
            throw new ServiceException("Start Time must be after or same as current Time");
        if (reservation.getStart().toLocalDateTime().toLocalDate().isBefore(LocalDate.now()))
            throw new ServiceException("Start Time must be before current date!");


    }

    @Override
    public void checkAvailable(Timestamp startTimeStamp, Timestamp endTimeStamp, String horsename) throws ServiceException {

        LOGGER.debug("Checking the Availability of the Box {}");
        try {
            for (int i = 0; i < reservationDAO.readAll().size(); i++) {

                if (reservationDAO.readAll().get(i).getStart().equals(startTimeStamp) && reservationDAO.readAll().get(i).getHorsename().equals(horsename)) {

                    throw new ServiceException("Selected Horse is not available 1");
                }

                if (startTimeStamp.after(reservationDAO.readAll().get(i).getStart()) &&
                        startTimeStamp.before(reservationDAO.readAll().get(i).getEnd()) &&
                            reservationDAO.readAll().get(i).getHorsename().equals(horsename)) {
                    throw new ServiceException("Selected Horse is not available 2");
                }
                if (startTimeStamp.after(reservationDAO.readAll().get(i).getStart()) &&
                        startTimeStamp.before(reservationDAO.readAll().get(i).getEnd()) &&
                        endTimeStamp.after(reservationDAO.readAll().get(i).getEnd()) &&
                        (reservationDAO.readAll().get(i).getHorsename().equals(horsename))) {

                    throw new ServiceException("Selected Horse is not available 3");

                }

                if (startTimeStamp.before(reservationDAO.readAll().get(i).getStart()) &&
                        startTimeStamp.before(reservationDAO.readAll().get(i).getEnd()) &&
                        endTimeStamp.before(reservationDAO.readAll().get(i).getEnd()) &&
                        reservationDAO.readAll().get(i).getHorsename().equals(horsename)) {
                    throw new ServiceException("Selected Horse is not available 4");
                }
                if (startTimeStamp.before(reservationDAO.readAll().get(i).getStart()) &&
                        startTimeStamp.before(reservationDAO.readAll().get(i).getEnd()) &&
                        endTimeStamp.before(reservationDAO.readAll().get(i).getStart()) &&
                        endTimeStamp.after(reservationDAO.readAll().get(i).getEnd()) &&
                        reservationDAO.readAll().get(i).getHorsename().equals(horsename)) {
                    throw new ServiceException("Selected Horse is not available 5");

                }
            }
        }catch (DAOException es)
        {
            LOGGER.error("Couldnt Read the Reservation list");
            throw new ServiceException("Couldnt read the Reservations");
        }

    }

    @Override
    public XYChart.Series checkDayoftheWeek(String horsename, ObservableList<String> weekday) throws ServiceException {
            LOGGER.debug("Creating Statistics {}");
    try
    {

        int[] weekdays = new int[8];
        if (!horsename.isEmpty()) {

            for (Reservation reservation1 : reservationDAO.readAll()) {
                if (horsename.equals(reservation1.getHorsename())) {
                    Date date = new Date(reservation1.getStart().getTime());

                    cal.setTime(date);
                    int day = cal.get(Calendar.DAY_OF_WEEK);
                    weekdays[day]++;
                }

            }

        }

            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            for (int i = 0; i < weekdays.length; i++) {
                series.getData().add(new XYChart.Data<>(weekday.get(i), weekdays[i]));
            }



            return series;

        }catch (DAOException es)
        {
                LOGGER.error("Error occurred during creating Statistics");
        }

        return null;
        }


    private boolean isNumeric(String s)
    {
        return java.util.regex.Pattern.matches("\\d+" , s);
    }



    @Override
    public XYChart.Series bestbooked(ObservableList<String> boxname) throws ServiceException {
        LOGGER.debug("Creating Statistics for best Reserved Box {}");


        try {
            if (!boxname.isEmpty()) {
                long[] countdays = new long[boxname.size()];
                for (Reservation reservation : reservationDAO.readAll()) {

                    for (int i = 0; i < box.readAllBoxes().size(); i++) {

                        if (reservation.getHorsename().equals(box.readAllBoxes().get(i).getHorsename().getValue())) {
                            for (int j = 0; j < boxname.size(); j++) {
                                if (boxname.get(j).equals(box.readAllBoxes().get(i).getBoxname().getValue())) {
                                    long milliseconds = reservation.getEnd().getTime() - reservation.getStart().getTime();
                                    long seconds = milliseconds / 1000;
                                    // calculate hours minutes and seconds
                                    long hours = seconds / 3600;
                                    long days = hours / 24;
                                    countdays[j] = days;

                                }

                            }
                        }

                    }
                }

                XYChart.Series<String, Long> series = new XYChart.Series<>();

                for (int i = 0; i < countdays.length; i++) {
                    series.getData().add(new XYChart.Data<>(boxname.get(i), countdays[i]));
                }

                return series;
            } else {
                LOGGER.error("An Error occurred");
            }
        }catch (DAOException es)
        {
            LOGGER.error("Could not read the Reservations from the database " +  es.getMessage());
        }


        return null;
    }
}
