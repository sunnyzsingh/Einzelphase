package sepm.ss17.e1428182.gui.controller;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.ReceiptService;
import sepm.ss17.e1428182.service.ReservationService;
import sepm.ss17.e1428182.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1428182.service.impl.ReservationServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class reservationdialogcontroller {


    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField clientName;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;


    private boolean check = false;
    private boolean reserve = false;

    private Timestamp startTimeStamp;
    private Timestamp endTimeStamp;

    private ReceiptService receiptService;
    @FXML
    private ChoiceBox Horsename;

    private PensionHouse pensionHouse;

    private ReservationService reservationService;
    private Stage dialogStage;

    private Reservation reservation;
    private boolean okClicked;
    private double price;

    private List<Box> box = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(reservationdialogcontroller.class);

    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
        setHorsename();
        Random random = new Random();
        id = random.nextInt(1234566);

    }

    private List<String> nameList = new ArrayList<>();

    private Integer id;

    private static final String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    private Pattern pattern;
    private Matcher matcher;

    public boolean isOkClicked()
    {
        return this.okClicked;
    }

    public reservationdialogcontroller()
    {
        try {
            reservationService = ReservationServiceImpl.getInstance();
            receiptService = ReceiptServiceImpl.getInstance();
        }catch (ServiceException es)
        {
            ErrorAlert("Error", es.getMessage());
        }

        pattern = Pattern.compile(TIME_PATTERN);
    }

    /**
     * SETS PATTERN FOR DATE PICKERS
     */
    @FXML
    private void initialize()
    {


        String pattern = "yyyy-MM-dd";

        startDate.setPromptText(pattern.toLowerCase());
        startDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        startDate.setValue(LocalDate.now());


        endDate.setPromptText(pattern.toLowerCase());

        endDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    /**
     * FILLS THE CHOICE BOX WITH HORSE NAME WHICH THE USER CAN CHOOSE
     */
    private void setHorsename()
    {
        if (Horsename.getItems().size() > 0)
            Horsename.getItems().clear();

        for (int i = 0; i < this.pensionHouse.getData().size(); i++)
        {

            Horsename.getItems().add(pensionHouse.getData().get(i).getHorsename().getValue());
            box.add(pensionHouse.getData().get(i));
        }

        Horsename.getSelectionModel().selectFirst();

    }



    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }


    /**
     * THIS METHOD SAVES THE RESERVATIONS
     */
    @FXML
    private void  handleSave() {
        if (check){

            if (!reserve) {

                Random random = new Random();
                id = random.nextInt(1234566);
            }


        try {

            reservation = new Reservation(1, clientName.getText(), startTimeStamp, endTimeStamp, Horsename.getValue().toString(), false);
            reservationService.createReservation(reservation);
            pensionHouse.getReservations().add(reservation);
            check = false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New Reservation");
            alert.setHeaderText("New Reservation");
            alert.setContentText("Do you want another Reservation");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
                reserve = true;
                clientName.setEditable(false);
                startDate.setEditable(false);
                endDate.setEditable(false);
                for (int i = 0; i < box.size(); i++) {

                    if (box.get(i).getHorsename().getValue().equalsIgnoreCase(Horsename.getValue().toString())) {

                        receiptService.create(id, reservation,box.get(i));
                    }
                }
                Horsename.getSelectionModel().clearSelection();

                setHorsename();

            } else {
                if (!reserve) {
                    for (int i = 0; i < box.size(); i++) {
                        if (box.get(i).getHorsename().getValue().equalsIgnoreCase(Horsename.getValue().toString())) {
                            receiptService.create(pensionHouse.getReservations().get(pensionHouse.getReservations().size()-1),  box.get(i));
                        }
                    }
                }
                if (reserve) {

                        for (int i = 0; i < box.size(); i++) {

                            if (box.get(i).getHorsename().getValue().equalsIgnoreCase(Horsename.getValue().toString())) {

                                receiptService.create(id, reservation, box.get(i));
                            }
                        }

                }
                this.dialogStage.close();
            }




        } catch (ServiceException e) {
            LOGGER.error("COULDNT CREATE RESERVATION CONTROLLER {}");
            ErrorAlert("Error", e.getMessage());
            check = false;
        }

        }else {
           ErrorAlert("Error", "Check Availability by clicking Avalability Button");
        }
    }


    private boolean validateInput(final String time) {

        matcher = pattern.matcher(time);
       return matcher.matches();
    }


    @FXML
    private void handleCancel()
    {
        this.dialogStage.close();
    }

    private void ErrorAlert(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    /**
     * CHECKS IF THE HORSE IS AVAILABLE OR NOT AND IF NOT DELETES THE HORSE NAME FROM THE LIST
     */
    @FXML
    private void checkhorse()
    {
        if (validateInput(startTime.getText()))
            startTimeStamp = Timestamp.valueOf(startDate.getValue() + " " + startTime.getText());
        else
            ErrorAlert("Input Invalid", "Start Time must be like 12:00:00");

        if (validateInput(endTime.getText()))
            endTimeStamp = Timestamp.valueOf(endDate.getValue() + " " + endTime.getText());
        else
            ErrorAlert("Input Invalid", "End Time must be like 12:00:00");


        try {
            if(Horsename.getSelectionModel().getSelectedItem().toString().isEmpty() || Horsename.getSelectionModel().getSelectedItem().toString() == null)
            {
                ErrorAlert("Error", "Please Select a horse first");
            }

            if (startTimeStamp != null && endTimeStamp != null && !Horsename.getSelectionModel().getSelectedItem().toString().isEmpty())
            reservationService.checkAvailable(startTimeStamp, endTimeStamp, Horsename.getSelectionModel().getSelectedItem().toString());
            else
            {
                ErrorAlert("Input Invalid", "Please dont leave Inputs Empty");
            }

        }catch (ServiceException es)
        {
            deleteHorse(Horsename.getSelectionModel().getSelectedItem().toString());
            ErrorAlert("Error",es.getMessage());
        }

        check = true;

    }

    private void deleteHorse(String horse)
    {
        if (Horsename != null)
        {

            for (int j = 0; j < Horsename.getItems().size(); j++) {

                if (Horsename.getItems().get(j).equals(horse))
                {
                    Horsename.getItems().remove(j);
                }
            }

        }

    }

}
