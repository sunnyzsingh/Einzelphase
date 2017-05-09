package sepm.ss17.e1428182.dao.impl;



import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.dao.boxDAO;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.DAOException;

import java.sql.*;
import java.util.List;
import java.util.*;

public class boxDAOimpl implements boxDAO{


    private static final Logger LOGGER = LoggerFactory.getLogger(boxDAOimpl.class);
    private static boxDAOimpl instance;
    private static Connection connection;
    private PreparedStatement create;
    private PreparedStatement readAll;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement searchbyboxname;
    private PreparedStatement searchbyhorsename;
    private PreparedStatement searchbywindow;
    private PreparedStatement searchbylocation;
    private PreparedStatement searchbylitter;
    private PreparedStatement searchbywindownlocation;
    private PreparedStatement searchbylocationnlitter;
    private PreparedStatement searchbywindowlitter;
    private PreparedStatement searchbywindowlocationlitter;

    public  static boxDAOimpl getInstance() throws DAOException
    {
        if(instance == null)
            instance = new boxDAOimpl();
        return instance;
    }

    private boxDAOimpl() throws DAOException{

        try {


            connection = ConnectionSingleton.getConnection();
            create = connection.prepareStatement("INSERT INTO box(boxname, horsename, litter, size, location, dailyrate, picture, window)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            readAll = connection.prepareStatement("SELECT * from box");
            update = connection.prepareStatement("UPDATE BOX SET boxname = ?, horsename = ?, litter = ?, size = ?, location = ?, dailyrate = ?, " +
                    "picture = ?, deleted = FALSE where ID = ?");
            delete = connection.prepareStatement("UPDATE BOX SET deleted = TRUE where ID = ?");

            searchbyboxname = connection.prepareStatement("Select * from box where boxname = ? and deleted = FALSE;");

            searchbyhorsename = connection.prepareStatement("Select * from box where horsename = ? and deleted = FALSE;");
            searchbylitter = connection.prepareStatement("Select * from box where litter = ? and deleted = FALSE;");
            searchbylocation = connection.prepareStatement("Select * from box where location = ? and deleted = FALSE;");
            searchbywindow = connection.prepareStatement("Select * from box where window = ? and deleted = FALSE;");
            searchbylocationnlitter = connection.prepareStatement("Select * from box where litter = ? and location = ? and deleted = FALSE;");
            searchbywindownlocation = connection.prepareStatement("Select * from box where window = ? and location = ? and deleted = FALSE;");
            searchbywindowlitter = connection.prepareStatement("Select * from box where litter = ? and window = ? and deleted = FALSE;");
            searchbywindowlocationlitter = connection.prepareStatement("Select * from box where litter = ? and location = ? and window = ? and deleted = FALSE;");


        }catch (SQLException e)
        {
            new DAOException(e.getMessage());
        }



    }

    @Override
    public Box create(Box box) throws DAOException {
        LOGGER.debug("METHOD boxDAO create {}");

        try
        {
            create.setString(1, box.getBoxname().getValue());
            create.setString(2, box.getHorsename().getValue());
            create.setString(3, box.getLitter());
            create.setDouble(4, box.getSize());
            create.setString(5, box.getLocation());
            create.setDouble(6, box.getDayprice());
            create.setString(7, box.getPicture());
            create.setBoolean(8, box.isWindow());
            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();
            if (rs.next())
                box.setId(rs.getInt(1));

        }catch (SQLException e)
        {
            LOGGER.error("CANNOT CREATE BOX.");
            throw new DAOException(e.getMessage());
        }


        return box;
    }

    @Override
    public List<Box> readAll() throws DAOException {

        LOGGER.debug("METHOD boxDAO readAll");
        List<Box> boxes = new ArrayList<>();
        try
        {

            ResultSet rs = readAll.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }


    @Override
    public List<Box> searchbyboxname(String boxname) throws DAOException {
        LOGGER.debug("METHOD boxDAO search by boxname");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbyboxname.setString(1, boxname);
            ResultSet rs = searchbyboxname.executeQuery();

                SimpleStringProperty boxnametemp = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxnametemp,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed." + e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public Box searchbyhorsename(String horsename) throws DAOException {
        LOGGER.debug("METHOD boxDAO search by horsename");
        Box box;
        try
        {
            searchbyhorsename.setString(1, horsename);
            ResultSet rs = searchbyhorsename.executeQuery();

                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsenametemp = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                 box = (new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsenametemp,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));


        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return box;
    }

    @Override
    public List<Box> searchbywindow(boolean window) throws DAOException {
        LOGGER.debug("METHOD boxDAO search by window");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbywindow.setBoolean(1, window);
            ResultSet rs = searchbywindow.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public List<Box> searchbylocation(String position) throws DAOException {
        LOGGER.debug("METHOD boxDAO search by position");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbylocation.setString(1, position);
            ResultSet rs = searchbylocation.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public List<Box> searchCombined(String position, boolean window) throws DAOException {
        LOGGER.debug("METHOD boxDAO Search by position and window");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbywindownlocation.setString(2, position);
            searchbywindownlocation.setBoolean(1, window);
            ResultSet rs = searchbywindownlocation.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public List<Box> searchbylitter(String litter) throws DAOException {
        LOGGER.debug("METHOD boxDAO search by Litter");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbylitter.setString(1, litter);
            ResultSet rs = searchbylitter.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public List<Box> searchCombined(String position, String litter) throws DAOException {
        LOGGER.debug("METHOD boxDAO search by position and Litter");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbylocationnlitter.setString(1, litter);
            searchbylocationnlitter.setString(2, position);
            ResultSet rs = searchbylocationnlitter.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public List<Box> searchCombined(boolean window, String litter) throws DAOException {
        LOGGER.debug("METHOD boxDAO Search by Window and Litter");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbywindowlitter.setString(1, litter);
            searchbywindowlitter.setBoolean(2, window);
            ResultSet rs = searchbywindowlitter.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }

    @Override
    public List<Box> searchCombined(String position, String litter, boolean window) throws DAOException {
        LOGGER.debug("METHOD boxDAO Search by Position, Litetr and Window {}");
        List<Box> boxes = new ArrayList<>();
        try
        {
            searchbywindowlocationlitter.setString(1, litter);
            searchbywindowlocationlitter.setString(2, position);
            searchbywindowlocationlitter.setBoolean(3, window);
            ResultSet rs = searchbywindowlocationlitter.executeQuery();
            while (rs.next())
            {
                SimpleStringProperty boxname = new SimpleStringProperty(rs.getString("boxname"));
                SimpleStringProperty horsename = new SimpleStringProperty(rs.getString("horsename"));
                //  String horsename, double dayprice, double size, String location, boolean window, String litter, String picture)
                boxes.add(new Box(rs.getBoolean("deleted"),rs.getInt("id"), boxname,
                        horsename,rs.getDouble("dailyrate"),
                        rs.getDouble("size"), rs.getString("location"), rs.getBoolean("window"),
                        rs.getString("litter"), rs.getString("picture")));
            }

        }catch (SQLException e)
        {
            LOGGER.error("Reading Boxes failed.");
            throw new DAOException(e.getMessage());
        }
        return boxes;
    }


    @Override
    public void update(Box box) throws DAOException {
        LOGGER.debug("METHOD update boxDAO {}");

        try
        {

            update.setString(1, box.getBoxname().getValue());
            update.setString(2, box.getHorsename().getValue());
            update.setString(3, box.getLitter());
            update.setDouble(4, box.getSize());
            update.setString(5, box.getLocation());
            update.setDouble(6, box.getDayprice());
            update.setString(7, box.getPicture());
            update.setInt(8, box.getId());
            update.executeUpdate();


        }catch(SQLException e)
        {
            LOGGER.error("Update failed. ");
            throw new DAOException(e.getMessage());
        }

    }

    @Override
    public boolean delete(Box box) throws DAOException {

        LOGGER.debug("METHOD Delete BoxDAO {}");

        try {
            delete.setInt(1, box.getId());

            return delete.executeUpdate()==1;
        }catch (SQLException e)
        {
            LOGGER.error("Couldn't delete Box");
            throw new DAOException(e.getMessage());
        }


    }

    @Override
    public void close() throws DAOException {
        ConnectionSingleton.closeConnection();
    }


}
