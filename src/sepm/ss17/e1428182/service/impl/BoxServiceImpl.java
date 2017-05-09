package sepm.ss17.e1428182.service.impl;

import sepm.ss17.e1428182.dao.boxDAO;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.dao.impl.boxDAOimpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

public class BoxServiceImpl implements BoxService{

    private static final Logger LOGGER = LoggerFactory.getLogger(BoxServiceImpl.class);
    private static BoxServiceImpl instance;
    private boxDAO BoxDAO;


    public static BoxServiceImpl getInstance() throws ServiceException
    {
        if(instance == null)
            instance = new BoxServiceImpl();

        return instance;
    }

    private BoxServiceImpl() throws ServiceException
    {
        try
        {
                BoxDAO = boxDAOimpl.getInstance();
        }catch (DAOException ex)
        {
            throw new ServiceException("Box has no instance.");
        }
    }

    @Override
    public Box createBox(Box box) throws ServiceException {
        LOGGER.debug("Service Create Box {}", box);

        validateBox(box);
        try {

            return BoxDAO.create(box);

        }catch (DAOException ex)
        {
            throw new ServiceException("Box ist not created. ");
        }


    }

    @Override
    public List<Box> readAllBoxes() throws ServiceException {
        LOGGER.debug("List all Boxes Service Method {}");

        try
        {
            if (BoxDAO.readAll().size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.readAll();
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }

    }

    @Override
    public void updateBox(Box box) throws ServiceException {

        LOGGER.debug("Method update of Boxes in Service {}");

            validateBox(box);
        try
        {
            BoxDAO.update(box);
        } catch (DAOException es)
        {
            throw new ServiceException("Couldn't Update Box");
        }

    }

    @Override
    public boolean deleteBox(Box box) throws ServiceException {

        LOGGER.debug("Method delete Box Service {}");
        try
        {
           return BoxDAO.delete(box);
        }catch (DAOException ex)
        {
            throw new ServiceException("Couldn't Delete the Box");
        }


    }

    @Override
    public void validateBox(Box box) throws ServiceException {

        LOGGER.debug("Validating Box Objects");

        if (isNumeric(box.getBoxname().getValue()))
            throw new ServiceException("Box Name must be String");
        if (isNumeric(box.getHorsename().getValue()))
            throw new ServiceException("Horse Name must be String");
        if (box.getBoxname() == null)
            throw new ServiceException("Box name is null");
        if (box.getBoxname().getValue().isEmpty())
            throw new ServiceException("Box name is empty");
        if(box.getHorsename() == null)
            throw new ServiceException("Horse name is null");
        if(box.getHorsename().getValue().isEmpty())
            throw new ServiceException("Horse name is Empty");
        if(box.getPicture().isEmpty())
            throw new ServiceException("Picture is invalid");
        if(box.getPicture() == null)
            throw new ServiceException("Picture is invalid.");
        if (box.getDayprice() < 0 || box.getDayprice() == 0)
            throw new ServiceException("Daily Rate invalid");
        if(box.getSize() < 0 || box.getSize() == 0)
            throw new ServiceException("Box Size invalid");

    }

    @Override
    public List<Box> searchbyboxname(String boxname) throws ServiceException {
        LOGGER.debug("List Search by boxname Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchbyboxname(boxname) == null)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchbyboxname(boxname);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }

    }

    @Override
    public Box searchbyhorsename(String horsename) throws ServiceException {
        LOGGER.debug("List Search by Horsename Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchbyhorsename(horsename) == null)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchbyhorsename(horsename);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }

    }

    @Override
    public List<Box> searchbywindow(boolean window) throws ServiceException {
        LOGGER.debug("List Search by Window Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchbywindow(window).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchbywindow(window);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public List<Box> searchbylocation(String position) throws ServiceException {
        LOGGER.debug("List Search by Position Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchbylocation(position).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchbylocation(position);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public List<Box> searchCombined(String position, boolean window) throws ServiceException {
        LOGGER.debug("List Search by Window and Position  Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchCombined(position, window).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchCombined(position, window);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public List<Box> searchbylitter(String litter) throws ServiceException {
        LOGGER.debug("List Search by Litter  Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchbylitter(litter).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchbylitter(litter);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public List<Box> searchCombined(String position, String litter) throws ServiceException {
        LOGGER.debug("List Search by Position and Litter Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchCombined(position, litter).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchCombined(position, litter);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public List<Box> searchCombined(boolean window, String litter) throws ServiceException {
        LOGGER.debug("List Search by Window and litter Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchCombined(window, litter).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchCombined(window, litter);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public List<Box> searchCombined(String position, String litter, boolean window) throws ServiceException {

        LOGGER.debug("List Search by Window and Position and litter Boxes Service Method {}");

        try
        {
            if (BoxDAO.searchCombined(position, litter, window).size() == 0)
                throw new ServiceException("Database is Empty");
            return BoxDAO.searchCombined(position,litter, window);
        }catch (DAOException ex)
        {
            LOGGER.error("Failed to read Boxes in the Database");
            throw new ServiceException("Selection Failed");
        }
    }

    @Override
    public void close() throws ServiceException {

        LOGGER.debug("Closing BoxDAO Connection");

        try
        {
            BoxDAO.close();
        }catch(DAOException es)
        {
            LOGGER.error("Couldn't close the connection");
            throw new ServiceException(es.getMessage());
        }


    }





    private boolean isNumeric(String s)
    {
        return java.util.regex.Pattern.matches("\\d+" , s);
    }
}
