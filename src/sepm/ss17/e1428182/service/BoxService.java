package sepm.ss17.e1428182.service;


import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.ServiceException;
import java.util.List;


public interface BoxService {

    /** This MEthod creates a new Box
     *
     * @param box
     * @return
     * @throws ServiceException
     */
    public Box createBox(Box box) throws ServiceException;

    /** Lists all the Boxes in the database
     *
     * @return
     * @throws ServiceException
     */
    public List<Box> readAllBoxes() throws ServiceException;

    /** This Method updates the Box given in the parameter
     *
     * @param box
     * @throws ServiceException
     */
    public void updateBox(Box box) throws ServiceException;

    /** This Method deletes the given Box
     *
     * @param box
     * @throws ServiceException
     */
    public boolean deleteBox(Box box) throws ServiceException;

    /** This MEthod valdates Box if given informations are correct
     *
     * @param box
     * @throws ServiceException
     */
    void validateBox(Box box) throws ServiceException;


    /** Lists the boxes searched by boxname
     *
     * @param boxname
     * @return
     * @throws ServiceException
     */
    List<Box> searchbyboxname(String boxname) throws ServiceException;

    /** Searches Box with horse Name
     *
     * @param horsename
     * @return
     * @throws ServiceException
     */
    Box searchbyhorsename(String horsename) throws ServiceException;

    /** This Method Lists Boxes with windows or without windows
     *
     * @param window
     * @return
     * @throws ServiceException
     */
    List<Box> searchbywindow(boolean window) throws ServiceException;

    /** This Method lists Box if they are inside or outside
     *
     * @param position
     * @return
     * @throws ServiceException
     */
    List<Box> searchbylocation(String position) throws ServiceException;

    /** This Methods lists Boxes regarding their location and window
     *
     * @param position
     * @param window
     * @return
     * @throws ServiceException
     */
    List<Box> searchCombined(String position, boolean window) throws ServiceException;

    /** This Method lists Boxes regarding their litter either sawdust or straw
     *
     * @param litter
     * @return
     * @throws ServiceException
     */
    List<Box> searchbylitter(String litter) throws ServiceException;


    /** Lists Boxes regarding their Position and Litter
     *
     * @param position
     * @param litter
     * @return
     * @throws ServiceException
     */
    List<Box> searchCombined(String position, String litter) throws ServiceException;

    /** Lists Boxes if they have window or not and if litter is sawdust or straw
     *
     * @param window
     * @param litter
     * @return
     * @throws ServiceException
     */
    List<Box> searchCombined(boolean window, String litter) throws ServiceException;

    /**  Lists Boxes if they have window or not and if litter is sawdust or straw and location if they are inside or outside
     *
     * @param position
     * @param litter
     * @param window
     * @return
     * @throws ServiceException
     */
    List<Box> searchCombined(String position, String litter, boolean window) throws ServiceException;




    /** this Method closes the Connection to the Database
     *
     * @throws ServiceException
     */
    public void close() throws ServiceException;





}
