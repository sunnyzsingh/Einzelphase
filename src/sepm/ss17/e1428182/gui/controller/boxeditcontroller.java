package sepm.ss17.e1428182.gui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.ReceiptService;
import sepm.ss17.e1428182.service.impl.BoxServiceImpl;
import sepm.ss17.e1428182.service.impl.ReceiptServiceImpl;

import java.io.File;


public class boxeditcontroller {


    @FXML
    private TextField boxID;

    @FXML
    private TextField boxName;

    @FXML
    private TextField HorseName;

    @FXML
    private TextField size;

    @FXML
    private TextField dailyrate;

    @FXML
    private ChoiceBox position;

    @FXML
    private ChoiceBox windows;

    @FXML
    private Button chooseImage;

    @FXML
    private ChoiceBox litter;

    private Box box;

    private Stage dialogStage;

    private String path;

    private Stage stage;

    private Logger LOGGER = LoggerFactory.getLogger(boxeditcontroller.class);

    private    BoxService boxService;
    private PensionHouse pensionHouse;
    private ReceiptService receiptService;

    @FXML
    private void initialize() throws ServiceException
    {
        boxService = BoxServiceImpl.getInstance();
        receiptService = ReceiptServiceImpl.getInstance();

            boxID.setEditable(false);

    }

    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
    }

    /**
     * This Method recieves the Box that needs to be edited
     * @param box
     */
    public void setBox(Box box)
    {

            this.box = box;
              boxID.setText(box.getId().toString());
              boxName.setText(box.getBoxname().getValue());
             HorseName.setText(box.getHorsename().getValue());

         litter.getItems().addAll("sawdust","straw");
        litter.getSelectionModel().selectFirst();

        windows.getItems().addAll("With Window", "Without Window");
        windows.getSelectionModel().selectFirst();

        position.getItems().addAll("inside","outside");
        position.getSelectionModel().selectFirst();

            size.setText(Double.toString(box.getSize()));
            dailyrate.setText(Double.toString(box.getDayprice()));

            path = box.getPicture();

    }

    /**This Method gives path of the image when user choose a Image for Box.
     *
     * @return path of the choosen Image
     */
    @FXML
    private String chooseImage()
    {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("choose image");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                LOGGER.info("Path chosen {}", file.getAbsolutePath());
                path = file.getAbsolutePath();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return path;
    }

    /**
     * Closes the Dialog
     */

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public  Box getBox()
    {
        return  this.box;
    }


    /**
     * This Method puts new values in the Datatbase
     */
    @FXML
    private void  handleOk()
    {

        try {


                SimpleStringProperty boxname = new SimpleStringProperty(boxName.getText());
                SimpleStringProperty horsename = new SimpleStringProperty(HorseName.getText());
                box.setPicture(path);
                box.setBoxname(boxname);
                box.setWindow(windows.getValue() == "With Window");
                box.setHorsename(horsename);
                box.setSize(Double.parseDouble(size.getText()));
                box.setDayprice(Double.parseDouble(dailyrate.getText()));
                box.setLocation(position.getValue().toString());
                boxService.updateBox(box);
                receiptService.update(box);


        }
        catch (ServiceException es)
        {
            LOGGER.error("Error adding a Box");

            ErrorAlert("Error in Adding a Box!", es.getMessage());

        }

        SuccessAlert("Edit Box", "Box edited successfully");
        dialogStage.close();
    }

    /**
     * This Method opens a Alert box if an error occurs
     * @param header
     * @param context
     */
    private void ErrorAlert(String header, String context)
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();

    }

    /**
     * This Method gives Information that a Box has been edited Successfully
     * @param header
     * @param context
     */

    private void SuccessAlert(String header, String context)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();

    }



}
