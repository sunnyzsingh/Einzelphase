package sepm.ss17.e1428182.service;

import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.ServiceException;

import java.sql.Timestamp;
import java.util.List;

public interface ReceiptService {


    /** This Method lists all the Receipts
     *
     * @return
     * @throws ServiceException
     */
    List<Receipt> showReceipts() throws ServiceException;

    /**This Method Creates a Receipt of the given Reservation of the specific Box when client decides to have more than one Reservation
     *
     * @param recid
     * @param reservation
     * @param box
     * @return
     * @throws ServiceException
     */
    Receipt create(Integer recid, Reservation reservation, Box box) throws ServiceException;

    void validate(Receipt receipt) throws ServiceException;

    /** This Method Creates a Receipt of the given Reservation of the specific Box
     *
     * @param reservation
     * @param box
     * @return
     * @throws ServiceException
     */
    Receipt create(Reservation reservation, Box box) throws ServiceException;

    /** This Method deletes Receipt of the Reservation given the Reservation ID
     *
     * @param rid
     * @throws ServiceException
     */
    void delete(Integer rid) throws ServiceException;


    /**
     * This Method updates the price if the box is edited and the price
     * changes only when the receipt aren't collected by the client
     * @param box
     * @throws ServiceException
     */
    void update(Box box) throws ServiceException;




}
