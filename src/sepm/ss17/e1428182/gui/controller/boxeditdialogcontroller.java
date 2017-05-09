package sepm.ss17.e1428182.gui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.domain.Box;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.impl.BoxServiceImpl;

import javax.xml.soap.Text;
import java.io.File;




public class boxeditdialogcontroller {

    private PensionHouse pensionHouse;


    @FXML
    private TextField BoxName;

    @FXML
    private TextField HorseName;

    @FXML
    private TextField Size;

    @FXML
    private TextField dailyRate;

    @FXML
    private ChoiceBox position;

    @FXML
    private ChoiceBox windows;

    @FXML
    private ChoiceBox litter;

    @FXML
    private Button imageButton;

    private Stage stage;
    private Stage dialogStage;
    private boolean okClicked = false;
    private String path;
    private Box temp;


    private  BoxService boxService;
    private static  Logger LOGGER = LoggerFactory.getLogger(boxeditdialogcontroller.class);
    @FXML
    private void initialize()
    {
        position.getItems().addAll("inside","outside");
        position.getSelectionModel().selectFirst();

        litter.getItems().addAll("straw", "sawdust");
        litter.getSelectionModel().selectFirst();

        windows.getItems().addAll("With Window", "Without Window");
        windows.getSelectionModel().selectFirst();

    }

    public Box getTemp()
    {
        return this.temp;
    }


    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }


    public boxeditdialogcontroller() throws ServiceException
    {
      boxService = BoxServiceImpl.getInstance();;
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }


    /**
     * This Method Creates a new Box with given Values by the User
     */
    @FXML
    private void   handleOk()
    {
        try {

                SimpleStringProperty boxname = new SimpleStringProperty(BoxName.getText());
                SimpleStringProperty horsename = new SimpleStringProperty(HorseName.getText());
                temp = new Box(false, 12, boxname, horsename, Double.parseDouble(dailyRate.getText()),
                        Double.parseDouble(Size.getText()), position.getValue().toString(), windows.getValue() == "With Window", litter.getValue().toString(), path);
                boxService.createBox(temp);
                okClicked = true;
                dialogStage.close();
        }
        catch (ServiceException es)
        {
            LOGGER.error("Error adding a Box");

            ErrorAlert("Error in Adding a Box", "Correct following error: " + es.getMessage());
        }
    }

    /**
     *
     * @return path of the Image
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
     * This Method closes the Dialog Box
     */
    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    /**
     * Opens the Error Dialog Box
     * @param header
     * @param context
     */
    private void ErrorAlert(String header, String context)
    {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();

    }


}
