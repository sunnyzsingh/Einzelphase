package sepm.ss17.e1428182.dao.impl;

import org.h2.command.Prepared;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.dao.receiptDAO;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class receiptDAOimpl implements receiptDAO {



    private static final Logger LOGGER = LoggerFactory.getLogger(boxDAOimpl.class);
    private static receiptDAOimpl instance;
    private static Connection connection;
    private PreparedStatement create;
    private PreparedStatement create2;
    private PreparedStatement readAll;
    private PreparedStatement update;
    private PreparedStatement delete;


    public  static receiptDAOimpl getInstance() throws  DAOException
    {
        if(instance == null)
        {
            instance = new receiptDAOimpl();
            return instance;

        }else
        {
            return instance;
        }


    }

    private receiptDAOimpl() throws DAOException
    {

        try {
            connection = ConnectionSingleton.getConnection();
            create = connection.prepareStatement("INSERT INTO receipt (r_id, b_id, totalrate, clientname, start) VALUES (?,?,?,?,?);");
            create2 = connection.prepareStatement("INSERT INTO receipt VALUES (?,?,?,?,?,?)");
            readAll = connection.prepareStatement("SELECT  * from receipt;");
            delete = connection.prepareStatement("DELETE from receipt where r_id = ?");
            update = connection.prepareStatement("UPDATE receipt SET totalrate = ? where receipt_id = ?;");


        }catch(SQLException es)
        {
            LOGGER.error("Couldnt Connect to the Database");
        }

    }


    @Override
    public List<Receipt> showAll() throws DAOException {

        LOGGER.debug("READING ALL RECEIPT {}");
        List<Receipt> list = new ArrayList<>();
        try
        {
            ResultSet rs = readAll.executeQuery();
            while(rs.next())
            {

                list.add(new Receipt(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getDouble(4), rs.getString(5), rs.getTimestamp(6)));


            }
        }catch (SQLException es)
        {
            LOGGER.error("Couldnt Read Receipt");
        }

        return list;
    }

    @Override
    public Receipt create(Receipt receipt) throws DAOException {
        LOGGER.debug("Creating new Receipt {}");
        try
        {
            create2.setInt(1,receipt.getReceiptid());
            create2.setInt(2,receipt.getR_id());
            create2.setInt(3, receipt.getB_id());
            create2.setDouble(4,receipt.getTotalprice());
            create2.setString(5,receipt.getClientname());
            create2.setTimestamp(6,receipt.getStart());
            create2.executeUpdate();

        }catch (SQLException es)
        {
            LOGGER.error("Couldnt Create Receipt! hier {} " + es.getMessage());
        }

        return receipt;
    }

    @Override
    public Receipt createwithoutid(Receipt receipt) throws DAOException {
        LOGGER.debug("Creating new Receipt {}");
        try
        {

            create.setInt(1,receipt.getR_id());
            create.setInt(2, receipt.getB_id());
            create.setDouble(3,receipt.getTotalprice());
            create.setString(4,receipt.getClientname());
            create.setTimestamp(5, receipt.getStart());
            create.executeUpdate();

        }catch (SQLException es)
        {
            LOGGER.error("Couldnt Create Receipt!");
        }

        return receipt;
    }

    @Override
    public void delete(Receipt receipt) throws DAOException {

        LOGGER.debug("Deleting the Canceled Reservation {}");


        try
        {
                delete.setInt(1, receipt.getR_id());
                delete.executeUpdate();

        }catch (SQLException es)
        {
            LOGGER.error("Couldnt delete the Receipt!");
        }



    }

    @Override
    public void update(Receipt receipt) throws DAOException {
        LOGGER.debug("Updating the receipt {}");

        try
        {
            update.setDouble(1, receipt.getTotalprice());
            update.setInt(2, receipt.getReceiptid());
            update.executeUpdate();
        }catch (SQLException es)
        {
            LOGGER.error("Could not update the receipt");
        }



    }


}
