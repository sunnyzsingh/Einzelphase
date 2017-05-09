package sepm.ss17.e1428182.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.dao.boxDAO;
import sepm.ss17.e1428182.dao.impl.boxDAOimpl;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.controller.*;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.ReceiptService;
import sepm.ss17.e1428182.service.ReservationService;
import sepm.ss17.e1428182.service.impl.BoxServiceImpl;
import sepm.ss17.e1428182.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1428182.service.impl.ReservationServiceImpl;

import java.io.IOException;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.LogManager;


public class PensionHouse extends Application {

    private ObservableList<Box> data = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    private ObservableList<Receipt> receiptObservableList = FXCollections.observableArrayList();
    private  static  final Logger LOGGER = LoggerFactory.getLogger(PensionHouse.class);
    protected static Stage stage;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private boxeditdialogcontroller boxedit;



    private ReservationService reservationService;
    private ReceiptService receiptService;
    private BoxService boxService;
    @Override
    public void start(Stage primaryStage) throws ServiceException{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Wendy's Pferdepension");
        this.primaryStage.getIcons().add(new Image("file:src/resources/icon.png"));

        initRootLayout();

        showBoxOverview();
    }

    public void refreshBoxlist()
    {


        data.clear();
        try {
            for (int i = 0; i < boxService.readAllBoxes().size(); i++) {
                if (!boxService.readAllBoxes().get(i).isDeleted())
                data.add(boxService.readAllBoxes().get(i));
            }
        }catch (ServiceException es)
        {
            LOGGER.error("ERROR IN LOADING BOXES!");

            ErrorAlert("Error", es.getMessage());

        }



    }

    public ObservableList<Receipt> receipts()
    {
        if(receiptObservableList != null)
        receiptObservableList.clear();

        try {
            if (receiptService.showReceipts().size() > 0) {
                for (int i = 0; i < receiptService.showReceipts().size(); i++) {

                    Date date = new Date(receiptService.showReceipts().get(i).getStart().getTime());

                    if (date.toLocalDate().equals(LocalDate.now()) || date.toLocalDate().isBefore(LocalDate.now()))
                        receiptObservableList.add(receiptService.showReceipts().get(i));
                }
            }
        }catch (ServiceException es)
        {
                LOGGER.error("ERROR LOADING RECEIPTS");
                ErrorAlert("Error", es.getMessage());
        }

        return receiptObservableList;

    }

    public ObservableList<Reservation> reservations(List<Reservation> my)
    {
        reservations.clear();
        for (int i = 0; i < my.size(); i++) {
            reservations.add(my.get(i));
        }

        return reservations;
    }

    public ObservableList<Reservation> updateReservation()
    {

       try {
           reservations.clear();
           for (int i = 0; i < reservationService.showAllReservation().size(); i++) {

               if (!reservationService.showAllReservation().get(i).isDeleted())
                   reservations.add(reservationService.showAllReservation().get(i));

           }
       }catch (ServiceException es)
       {
           LOGGER.error("ERROR LOADING RESERVATIONS!");
           ErrorAlert("Error", es.getMessage());

        }

        return reservations;

    }


    public PensionHouse() throws ServiceException
    {
        boxService = BoxServiceImpl.getInstance();
        reservationService = ReservationServiceImpl.getInstance();
        receiptService = ReceiptServiceImpl.getInstance();
        updateReservation();
       refreshBoxlist();
    }

    public ObservableList<Box> getData()
    {
        return this.data;
    }

    public ObservableList<Reservation> getReservations()
    {
        return this.reservations;
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {

        LOGGER.debug("Opening the Main Programme Dialog");
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/menulayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            menulayoutcontroller controller = loader.getController();
            controller.setPensionHouse(this);

            primaryStage.show();
        } catch (IOException e) {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }


    }

    /**
     * OPens Reservation Edit dialog
     * @param reservation
     */

    public void editReservation(Reservation reservation)
    {
        LOGGER.debug("Opening Edit Reservation Dialog");
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/editReservation.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Reservation");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            editReservationcontroller controller = loader.getController();
            controller.setPensionHouse(this);
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);
            dialogStage.showAndWait();
        }catch (IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }

    }


    /**
     * opens Search box dialog
     */
    public void searchBox()
    {
        LOGGER.debug("Opening Search Box  Dialog");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/searchdialog.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Search Box");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            searchcontroller controller = loader.getController();
            controller.setPensionHouse(this);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();


        }catch(IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }
    }

    /**
     * Opens Receipt Viewer Dialog
     */
    public void viewReceipt()
    {
        LOGGER.debug("Opening Receipt Viewer Dialog");
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/receipt.fxml"));
            AnchorPane pane = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Receipt");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            receiptcontroller controller = loader.getController();
            controller.setPensionHouse(this);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        }catch (IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }
    }

    /**
     * Shows the Box overview inside the root layout.
     */
    public void showBoxOverview() {
        LOGGER.debug("Opening  Box  Overview Dialog");
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/boxviewer.fxml"));
            AnchorPane BoxOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(BoxOverview);
            boxviewercontroller boxviewercontroller = loader.getController();
            boxviewercontroller.setPensionHouse(this);


        } catch (IOException e) {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }
    }


    /**
     * OPens Reservation View Dialog
     */
    public void showReservationOverview()
    {
        LOGGER.debug("Opening Reservation Viewer Dialog");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/reservationviewer.fxml"));
            AnchorPane reservationOverview = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Reservations");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(reservationOverview);
            dialogStage.setScene(scene);

           reservationviewercontroller con = loader.getController();
            con.setPensionHouse(this);
            con.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        }
        catch (IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }




    }

    /**
     * Opens Edit Box Dialog Stage
     * @param box
     */

    public void getEditBox(Box box)
    {

        LOGGER.debug("Opening Edit Box Dialog");
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/editBox.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Box");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            boxeditcontroller controller = loader.getController();
            controller.setPensionHouse(this);
            controller.setBox(box);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            refreshBoxlist();

        }
        catch(IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
        }



    }

    public void close()
    {

        primaryStage.close();
    }

    /**
     * OPens Dialog if the USer wants to add new Box to the database
     * @return
     */

    public boolean showEditBox()
    {
        LOGGER.debug("Opening New Box Dialog");
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/boxeditdialog.fxml"));


            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Box");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            boxedit = loader.getController();
            boxedit.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            refreshBoxlist();


            return boxedit.isOkClicked();
        }catch (IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File");
            return false;

        }
    }


    /**
     * Opens the New Reservation Dialog if the USer wants to add a new Reservaiton
     */
    public void reservationDialog()
    {
        LOGGER.debug("Opening Reservation Dialog");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/reservationdialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            reservationdialogcontroller controller = loader.getController();
            controller.setPensionHouse(this);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        }catch(IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred durign loading FXML File" + es.getMessage());
        }
    }


    /**
     * Opens Statistics Dialog
     * @throws ServiceException
     */
    public void showStatistics() throws ServiceException
    {
        LOGGER.debug("Opening Statistics Dialog");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PensionHouse.class.getResource("view/statisticviewer.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogstage = new Stage();
            dialogstage.setTitle("Statistics");
            dialogstage.initOwner(primaryStage);
            dialogstage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(pane);
            dialogstage.setScene(scene);
            statisticviewercontroller controller = loader.getController();

            controller.setPensionHouse(this);
            dialogstage.show();
        }catch (IOException es)
        {
            LOGGER.error("ERROR LOADING FXML FILE!");
            ErrorAlert("Error Occurred", "Error occurred during loading FXML File");
        }
    }



    private void ErrorAlert(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }




    public static void main(String[] args) {

        LOGGER.info("Starting the Wendy's Pferdepension");

        launch(args);
    }
}
