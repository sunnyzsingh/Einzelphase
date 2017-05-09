package sepm.ss17.e1428182.dao;


import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.domain.Box;

import java.util.List;

public interface boxDAO {

    /**This MEthod creates a new Box
     *
     * @param box
     * @return
     * @throws DAOException
     */
    Box create(Box box) throws DAOException;

    /**Lists all the Boxes in the database
     *
     * @return
     * @throws DAOException
     */
    List<Box> readAll() throws DAOException;

    /**
     *  Lists the boxes searched by boxname
     * @param boxname
     * @return
     * @throws DAOException
     */
    List<Box> searchbyboxname(String boxname) throws DAOException;

    /**
     *  Searches Box with horse Name
     * @param horsename
     * @return
     * @throws DAOException
     */
    Box searchbyhorsename(String horsename) throws DAOException;

    /**
     * This Method Lists Boxes with windows or without windows
     *
     * @param window
     * @return
     * @throws DAOException
     */
    List<Box> searchbywindow(boolean window) throws DAOException;

    /** This Method lists Box if they are inside or outside
     *
     * @param position
     * @return
     * @throws DAOException
     */
     List<Box> searchbylocation(String position) throws DAOException;

    /**
     *  This Methods lists Boxes regarding their location and window
     * @param position
     * @param window
     * @return
     * @throws DAOException
     */
     List<Box> searchCombined(String position, boolean window) throws DAOException;

    /**
     *  This Method lists Boxes regarding their litter either sawdust or straw
     * @param litter
     * @return
     * @throws DAOException
     */
     List<Box> searchbylitter(String litter) throws DAOException;


    /** Lists Boxes regarding their Position and Litter
     *
     * @param position
     * @param litter
     * @return
     * @throws DAOException
     */
     List<Box> searchCombined(String position, String litter) throws DAOException;

    /** Lists Boxes if they have window or not and if litter is sawdust or straw
     *
     * @param window
     * @param litter
     * @return
     * @throws DAOException
     */
     List<Box> searchCombined(boolean window, String litter) throws DAOException;

    /** Lists Boxes if they have window or not and if litter is sawdust or straw and location if they are inside or outside
     *
     * @param position
     * @param litter
     * @param window
     * @return
     * @throws DAOException
     */
     List<Box> searchCombined(String position, String litter, boolean window) throws DAOException;






    /**     This Method updates the Box given in the parameter
     *
     * @param box
     * @throws DAOException
     */
    void update(Box box) throws DAOException;

    /**
     *      This Method deletes the given Box
     * @param box
     * @return
     * @throws DAOException
     */
    boolean delete(Box box) throws DAOException;


    /**
     * this Method closes the Connnection to the Database
     * @throws DAOException
     */
    void close() throws  DAOException;


}
