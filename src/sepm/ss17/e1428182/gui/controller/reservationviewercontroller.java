package sepm.ss17.e1428182.gui.controller;


import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.ReceiptService;
import sepm.ss17.e1428182.service.ReservationService;
import sepm.ss17.e1428182.service.impl.BoxServiceImpl;
import sepm.ss17.e1428182.service.impl.ReceiptServiceImpl;
import sepm.ss17.e1428182.service.impl.ReservationServiceImpl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

public class reservationviewercontroller {

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> Horsename;

    @FXML
    private TableColumn<Reservation, String> clientname;

    @FXML
    private Label id;

    @FXML
    private Label horsename;

    @FXML
    private Label clientnamelabel;

    @FXML
    private Label starttime;

    @FXML
    private Label endtime;

    private Stage dialogStage;

    private PensionHouse pensionHouse;

    @FXML
    private DatePicker start;

    @FXML
    private DatePicker end;

    private Timestamp startT;

    private Timestamp endT;

    private ReservationService service = ReservationServiceImpl.getInstance();
    private ReceiptService receiptService = ReceiptServiceImpl.getInstance();

    public reservationviewercontroller() throws ServiceException {
    }


    @FXML
    private void initialize()
    {


       Horsename.setCellValueFactory(celldata -> new ReadOnlyStringWrapper(celldata.getValue().getHorsename()));
       clientname.setCellValueFactory(celldata -> new ReadOnlyStringWrapper(celldata.getValue().getClientname()));

       showReservation(null);
        reservationTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showReservation(newValue)));


    }





    @FXML
    private void search()
    {
        try {


            if ((start.getValue() != null && end.getValue() != null) && ( start.getValue().isBefore(end.getValue()) || (start.getValue().isEqual(end.getValue())))) {



                    startT = Timestamp.valueOf(start.getValue() + " " + "00:01:00 ");
                    endT = Timestamp.valueOf(end.getValue() + " " + "23:59:59 ");

                    List<Reservation> my = service.timedReservation(startT, endT);

                    if (my != null)
                        reservationTable.setItems(pensionHouse.reservations(my));


            }
            else
            {
                ErrorAlert("Wrong Input", "Invalid Input");
            }
        }catch (ServiceException es)
        {
            ErrorAlert("For this Period", "No Reservation found");
        }

    }


    @FXML
    private void delete()
    {
        try
        {
            Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();

            if ( reservation != null)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Reservation");
                alert.setHeaderText("Delete");
                alert.setContentText("Selected Reservation Deleted");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK)
                {
                    receiptService.delete(reservation.getR_id());
                    service.delete(reservation);

                    reservationTable.setItems(pensionHouse.updateReservation());
                }


            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Reservation");
                alert.setHeaderText("Delete");
                alert.setContentText("You must select the Box to delete");
                alert.showAndWait();

            }



        }catch (ServiceException es)
        {

            ErrorAlert("Delete", "Couldnt Delete the Reservation");
        }





    }



    private void showReservation(Reservation reservation)
    {
        if (reservation != null)
        {
            id.setText(reservation.getR_id().toString());
            horsename.setText(reservation.getHorsename());
            clientnamelabel.setText(reservation.getClientname());
            starttime.setText(reservation.getStart().toString());
            endtime.setText(reservation.getEnd().toString());
        }
        else
        {
            id.setText("KA");
            horsename.setText("KA");
            clientnamelabel.setText("KA");
            starttime.setText("KA");
            endtime.setText("KA");
        }




    }



    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }


    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
        reservationTable.setItems(pensionHouse.getReservations());
    }




    @FXML
    private void onClickEdit()
    {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if (reservation != null) {

             Date date = new Date(reservation.getStart().getTime());

             if(!date.toLocalDate().equals(LocalDate.now()) && !date.toLocalDate().isAfter(date.toLocalDate()))
            this.pensionHouse.editReservation(reservation);
             else
             {
                 Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("Edit Reservation");
                 alert.setHeaderText("Warning");
                 alert.setContentText("Cannot Edit the Reservation on the same Day or Day past the today date");
                 alert.showAndWait();
             }
        }
        else
        {
            ErrorAlert("Input", "Please Select a Reservation");
        }
    }

    @FXML
    private void onClickclose()
    {
        this.dialogStage.close();
    }

    private void ErrorAlert(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(context);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}
