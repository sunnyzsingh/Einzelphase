package sepm.ss17.e1428182.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.dao.impl.boxDAOimpl;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.domain.Receipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface receiptDAO {



    /**
     * Lists all the Receipts that are in the database
     * @return
     * @throws DAOException
     */
    List<Receipt> showAll() throws DAOException;

    /** This Method creates the Receipt given in the parameter
     *
     * @param receipt
     * @return
     * @throws DAOException
     */
    Receipt create(Receipt receipt) throws DAOException;

    /** This method creates the receipt given in the parameter
     *
     * @param receipt
     * @return
     * @throws DAOException
     */
    Receipt createwithoutid(Receipt receipt) throws DAOException;

    /**
     *This Method deletes the canceled Reservations
     * @param receipt
     * @throws DAOException
     */
    void delete(Receipt receipt) throws DAOException;


    void update(Receipt receipt) throws DAOException;

}
