package sepm.ss17.e1428182.service.impl;

import org.h2.server.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.ReceiptService;
import sepm.ss17.e1428182.dao.receiptDAO;
import sepm.ss17.e1428182.dao.impl.receiptDAOimpl;
import sepm.ss17.e1428182.service.ReservationService;

import java.sql.SQLRecoverableException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptServiceImpl.class);
    private receiptDAO receiptL;
    private static ReceiptServiceImpl instance;
    private static ReservationService reservationService;
    private static BoxService boxService;

    public static ReceiptServiceImpl getInstance() throws ServiceException {
        if (instance == null)
            instance = new ReceiptServiceImpl();

        return instance;
    }

    private ReceiptServiceImpl() throws ServiceException {

        try {
            receiptL = receiptDAOimpl.getInstance();
            reservationService = ReservationServiceImpl.getInstance();
            boxService = BoxServiceImpl.getInstance();

        } catch (DAOException ex) {
            throw new ServiceException("Receipt has no instance.");
        }
    }


    @Override
    public List<Receipt> showReceipts() throws ServiceException {
        LOGGER.debug("Reading all Receipt Service Method {}");
        List<Receipt> receipts = new ArrayList<>();
        try {
            receipts = receiptL.showAll();
            return receipts;

        } catch (DAOException es) {
            LOGGER.error("Couldnt read  Receipt.");
        }

        return null;

    }

    @Override
    public Receipt create(Reservation reservation, Box box) throws ServiceException
    {

        LOGGER.debug("Creating a Receipt New Method{}");




        try {
            for (int i = 0; i < reservationService.showAllReservation().size(); i++) {

                if (reservationService.showAllReservation().get(i).getR_id().equals(reservation.getR_id()))
                {

                    for (int j = 0; j < boxService.readAllBoxes().size(); j++) {

                        if (boxService.readAllBoxes().get(j).getId().equals(box.getId()))
                        {
                            double totalprice = calculatePrice(reservation, box);
                            Receipt receipt = new Receipt(null, reservation.getR_id(), box.getId(), totalprice, reservation.getClientname(), reservation.getStart());
                            validate(receipt);
                             return receiptL.createwithoutid(receipt);
                        }

                    }
                }

            }

            throw new ServiceException("Reservation or Box does not exists");



        }catch (DAOException es)
        {
            LOGGER.error("Couldnt create the receipt! " + es.getMessage());
            throw new ServiceException(es.getMessage());
        }




        //return null;

    }

    @Override
    public void delete(Integer rid) throws ServiceException {

        LOGGER.debug("Deleting the Canceled Reservation {}");

        try
        {

            for (int i = 0; i < receiptL.showAll().size(); i++) {

                if(receiptL.showAll().get(i).getR_id().equals(rid))
                {
                    receiptL.delete(receiptL.showAll().get(i));
                }

            }

        }catch (DAOException es)
        {
            LOGGER.error("Couldn't delete the Receipt");
            throw new ServiceException(es.getMessage());
        }




    }

    @Override
    public void update(Box box) throws ServiceException {
        LOGGER.debug("Updating the price of the box");

        try {

            for (int i = 0; i < receiptL.showAll().size(); i++) {
                {
                    if (receiptL.showAll().get(i).getB_id().equals(box.getId())) {
                        if (receiptL.showAll().get(i).getStart().toLocalDateTime().toLocalDate().isAfter(LocalDate.now())) {

                            for (int j = 0; j < reservationService.showAllReservation().size(); j++) {

                                if (reservationService.showAllReservation().get(j).getHorsename().equals(box.getHorsename().getValue()))
                                {
                                    double price = calculatePrice(reservationService.showAllReservation().get(j),box);
                                    Receipt receipt = receiptL.showAll().get(i);
                                    receipt.setTotalprice(price);
                                    receiptL.update(receipt);
                                }

                            }

                        }
                    }
                }

            }

        }catch(DAOException es)
        {
                LOGGER.error("Could not Update the Receipt");
        }


    }


    @Override
    public Receipt create(Integer recid, Reservation reservation, Box box) throws ServiceException {
        LOGGER.debug("Creating a Receipt Service with given Id {}");


        double totalprice = calculatePrice(reservation, box);
        Receipt receipt = new Receipt(recid, reservation.getR_id(), box.getId(), totalprice, reservation.getClientname(), reservation.getStart());
        try {

            validate(receipt);

            if(!receiptL.showAll().get(receiptL.showAll().size()-1).getR_id().equals(reservation.getR_id()) ) {
                return receiptL.create(receipt);
            }
        } catch (DAOException es) {
            LOGGER.error("Couldnt create a Receipt!");
            throw new ServiceException(es.getMessage());
        }

            return null;

    }

    @Override
    public void validate(Receipt receipt) throws ServiceException {

        if (receipt.getR_id() == 0)
            throw new ServiceException("Reservation ID cannot be 0");
        if (receipt.getStart() == null)
            throw new ServiceException("Start Time is null");
        if (receipt.getB_id() == 0)
            throw new ServiceException("Box ID cannot be null");
        if (receipt.getTotalprice() == 0.0)
            throw new ServiceException("Total Price cannot be null");
        if (receipt.getClientname().isEmpty())
            throw new ServiceException("Client Name cannot be Empty");
        if (receipt.getClientname() == null)
            throw new ServiceException("Client Name cannot be null");



    }


    private double calculatePrice(Reservation reservation, Box box) throws ServiceException
    {

        LOGGER.debug("Calculating Price for the given Reservation for specific given Box");

            long milliseconds = reservation.getEnd().getTime() - reservation.getStart().getTime();
            int seconds = (int) milliseconds / 1000;
            // calculate hours minutes and seconds
            int hours = seconds / 3600;

            return box.getDayprice()*hours;


    }

}
