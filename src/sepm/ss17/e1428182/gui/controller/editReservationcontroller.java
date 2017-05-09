package sepm.ss17.e1428182.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.h2.util.New;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.ReceiptService;
import sepm.ss17.e1428182.service.ReservationService;
import sepm.ss17.e1428182.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1428182.service.impl.ReservationServiceImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class editReservationcontroller {


    @FXML
    private TextField clientname;

    @FXML
    private TextField r_id;

    @FXML
    private TextField starttime;

    @FXML
    private TextField endTime;

    @FXML
    private ChoiceBox horsename;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;


    private static final String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    private Pattern pattern;
    private Matcher matcher;

    private boolean check = false;
    private Reservation reservation;
    private PensionHouse pensionHouse;
    private Stage dialogStage;

    private boolean reserve = false;

    private Timestamp startTimeStamp;
    private Timestamp endTimeStamp;

    private Integer bid = 0;

    private ReservationService reservationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(editReservationcontroller.class);
    private ReceiptService receiptService;


    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public editReservationcontroller()
    {
        try {
            receiptService = ReceiptServiceImpl.getInstance();
            reservationService = ReservationServiceImpl.getInstance();
        }catch (ServiceException es)
        {
                LOGGER.error("Error Loding Services.");

        }

        pattern = Pattern.compile(TIME_PATTERN);

    }


    public void setReservation(Reservation reservation)
    {
        this.reservation = reservation;

        clientname.setText(this.reservation.getClientname());
        clientname.setEditable(false);

        r_id.setText(this.reservation.getR_id().toString());
        r_id.setEditable(false);

        Date startdate = new Date(reservation.getStart().getTime());
        startDate.setValue(startdate.toLocalDate());

        Date enddate = new Date((reservation.getEnd().getTime()));
        endDate.setValue(enddate.toLocalDate());

        String startt[] = reservation.getStart().toString().split(" ");
        String startf[] = startt[1].split("\\.");
        starttime.setText(startf[0]);

        String end[] = reservation.getEnd().toString().split(" ");
        String endf[] = end[1].split("\\.");
        endTime.setText(endf[0]);


        refreshHorselist();

        for (int i = 0; i < horsename.getItems().size(); i++) {

            if(horsename.getItems().get(i).equals(reservation.getHorsename()))
            {
                horsename.getSelectionModel().select(i);
            }

        }

    }

    /**
     * Refreshes Horse List in Choice Box
     */
    private void refreshHorselist()
    {

        for (int i = 0; i < this.pensionHouse.getData().size(); i++)
        {

            horsename.getItems().add(pensionHouse.getData().get(i).getHorsename().getValue());
        }

    }

    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
    }


    @FXML
    private void initialize()
    {

    }

    /**
     * CHECKS IF HORSE IS AVAILABLE FOR GIVEN TIMESPAN CALLS METHOD IN RESERVATIONSSERVICE
     */
    @FXML
    private void refreshHorsename()
    {


            if(validateInput(starttime.getText()))
            startTimeStamp = Timestamp.valueOf(startDate.getValue() + " " + starttime.getText());
            else
                ErrorAlter("Error", "Start Time must be in format like 12:00:00");

            if (validateInput(endTime.getText()))
            endTimeStamp = Timestamp.valueOf(endDate.getValue() + " " + endTime.getText());
            else
                ErrorAlter("Error", "End Time must be in format like 12:00:00");

            try {

                reservationService.checkAvailable(startTimeStamp, endTimeStamp, horsename.getSelectionModel().getSelectedItem().toString());

            }catch (ServiceException es)
            {
                deleteHorse(horsename.getSelectionModel().getSelectedItem().toString());
                ErrorAlter("Error",es.getMessage());
            }

            check = true;

    }

    /**
     * IF SELECTED BOX IS NOT AVAILABLE IS DELETED FROM THE CHOICE BOX
     * @param horse
     */

    private void deleteHorse(String horse)
    {
        if (horsename != null)
        {

            for (int j = 0; j < horsename.getItems().size(); j++) {

                if (horsename.getItems().get(j).equals(horse))
                {
                    horsename.getItems().remove(j);
                }
            }

        }

    }

    /**
     * CHECKS OF TIME'S FORMAT WRITTEN BY USER IS CORRECT OR NOT CORRECT FORMAT 12:00:00
     * @param time
     * @return
     */

    private boolean validateInput(String time) {

        matcher = pattern.matcher(time);
        return matcher.matches();
    }

    /**
     * THIS METHOD EDITS THE RESERVATION WITH NEW VALUES EXCEPT ID AND CLIENT NAME
     */

    @FXML
    private void edit() {

        try {
            if (check) {

                if(horsename == null)
                {
                    refreshHorselist();
                }


                this.reservation.setStart(startTimeStamp);
                this.reservation.setEnd(endTimeStamp);
                this.reservation.setHorsename(horsename.getValue().toString());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update");
                alert.setContentText("Reservation Update");
                alert.setContentText("Are you sure want to Update the Reservation?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                    {
                    reservationService.update(reservation);
                    pensionHouse.updateReservation();

                    receiptService.delete(reservation.getR_id());

                        for (int i = 0; i < pensionHouse.getData().size(); i++) {


                            if (horsename.getValue().toString().equals(pensionHouse.getData().get(i).getHorsename().getValue()))
                            {

                                receiptService.create(reservation, pensionHouse.getData().get(i));
                            }

                        }

                    this.dialogStage.close();
                    }
                }
                else
            {
                ErrorAlter("Error", "Before Reservation please check Availability");
            }

            }catch(ServiceException es)
            {

                LOGGER.error("An Error occurred during updating the Reservation " + es.getMessage());
            }


    }


    /**
     * CLOSES THE DIALOG BOX
     */
    @FXML
    private void close()
    {
        this.dialogStage.close();
    }


    private void ErrorAlter(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }





}
