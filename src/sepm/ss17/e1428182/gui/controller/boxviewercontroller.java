package sepm.ss17.e1428182.gui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.junit.experimental.categories.Category;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class boxviewercontroller {


    @FXML
    private TableView<Box> boxTable;

    @FXML
    private TableColumn<Box, String> BoxName;

    @FXML
    private TableColumn<Box, String> HorseName;

    @FXML
    private Label BoxNameLabel;

    @FXML
    private Label HorseNameLabel;

    @FXML
    private Label sizeLabel;

    @FXML
    private Label windowsLabel;

    @FXML
    private Label litterLabel;

    @FXML
    private Label dailyRateLabel;

    @FXML
    private Label locationLabel;

    private PensionHouse pensionHouse;

    @FXML
    private ImageView imageView;

    private Box tempBox;

    /**
     * This Method puts values in the Table Column
     */

    private void refreshTable()
    {

        BoxName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Box, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Box, String> param) {
                return param.getValue().getBoxname();
            }
        });

        HorseName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Box, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Box, String> param) {
                return param.getValue().getHorsename();
            }
        });



    }

    /**
     * Sends Values of the Box to the ShowBoxDetails method
     */
    @FXML
    private void initialize()
    {

       BoxName.setCellValueFactory(celldata -> celldata.getValue().getBoxname());
       HorseName.setCellValueFactory(celldata -> celldata.getValue().getHorsename());
        showBoxDetails(null);
        boxTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showBoxDetails(newValue)));



        refreshTable();

    }


    /**
     * Shows Details of the Box in Label Form
     * @param box
     */
    private void showBoxDetails(Box box)
    {
        if(box != null)
        {
            BoxNameLabel.setText(box.getBoxname().getValue());
            HorseNameLabel.setText(box.getHorsename().getValue());
            sizeLabel.setText(Double.toString(box.getSize()) + " m²");
            if(box.isWindow() == true)
                windowsLabel.setText("With Window");
            else
                windowsLabel.setText("Without Window");

            litterLabel.setText(box.getLitter());
            dailyRateLabel.setText(Double.toString(box.getDayprice()) + " €/hrs");
            locationLabel.setText(box.getLocation());
            File file = new File(box.getPicture());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);

            tempBox = box;

        }
        else
        {
            BoxNameLabel.setText("");
            HorseNameLabel.setText("");
            sizeLabel.setText("");
            windowsLabel.setText("");
            litterLabel.setText("");
            dailyRateLabel.setText("");
            locationLabel.setText("");
        }

    }





    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;


        boxTable.setItems(pensionHouse.getData());
    }

    /**
     * This MEthod call Method in the Main App which opens Edit Box Dialog Stage
     */
    @FXML
    private void onEditClick()
    {
        tempBox = boxTable.getSelectionModel().getSelectedItem();
            if (tempBox == null) {
                ErrorAlert("Box Missing", "Please select a Box to Edit");
            } else {
                pensionHouse.getEditBox(tempBox);
                setPensionHouse(pensionHouse);
            }
    }

    /**
     * This MEthod calls Method in Main App (PensionHouse), which opens the Dialog Box with all Reservations
     */

    @FXML
    private void viewReservation(){
        pensionHouse.showReservationOverview();
    }


    /**
     * This MEthod calls Method in Main App (PensionHouse), which opens the Dialog Box with Receipts that has been payed and will be payed today by the Client
     */
    @FXML
    private void viewReceipt()
    {
        pensionHouse.viewReceipt();

    }

    /**
     * This MEthod calls Method in Main App (PensionHouse), which opens the Dialog Box with New Reservations
     */
    @FXML
    private void reserveBox()
    {
        pensionHouse.reservationDialog();
    }


/*Alerts in Methods*/

    private void  ErrorAlert(String header, String context)
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();

    }


}
