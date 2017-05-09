package sepm.ss17.e1428182.gui.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.ReservationService;
import sepm.ss17.e1428182.service.impl.ReservationServiceImpl;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class statisticviewercontroller {


    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private  BarChart<String, Integer> bestbooked;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private ChoiceBox horsename;

    private ObservableList<String> weekday = FXCollections.observableArrayList();

    private PensionHouse pensionHouse;

    private ReservationService reservationService = ReservationServiceImpl.getInstance();

    private ObservableList<String> boxName = FXCollections.observableArrayList();

    public statisticviewercontroller() throws ServiceException {
    }


    public void setPensionHouse(PensionHouse pensionHouse) {
        this.pensionHouse = pensionHouse;

        for (Box box : pensionHouse.getData()) {
            horsename.getItems().add(box.getHorsename().getValue());
            boxName.add(box.getBoxname().getValue());
        }
        horsename.getSelectionModel().selectFirst();



    }

    /**
     * INITIALIZES THE VALUES TO THE CATAGORIES
     */

    @FXML
    private void initialize() {
        //barChart.setTitle("Reservation Count");

        String[] days = DateFormatSymbols.getInstance(Locale.ENGLISH).getWeekdays();
        weekday.addAll(Arrays.asList(days));
        xAxis.setCategories(weekday);

        categoryAxis.setCategories(boxName);


    }

    /**
     * SHOWS STATISTICS OF THE SELECTED HORSE ON WHICH DA OF THE WEEK IT HAS BEEN RESERVED
     *
     * @throws ServiceException
     */
    @FXML
    private void setReservation() throws ServiceException {
        if (!barChart.getData().isEmpty())
            barChart.getData().clear();


        barChart.getData().addAll(reservationService.checkDayoftheWeek(horsename.getSelectionModel().getSelectedItem().toString(), weekday));

        if (barChart.getData().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Information");
            alert.setContentText("This Box has not been reserved");
            alert.showAndWait();
        }


    }

    /**
     * SHOWS STATISTICS OF ALL THE BOXES FOR HOW MANY DAYS IT HAS BEEN RESERVED
     * @throws ServiceException
     */
    @FXML
    private void clickbestBooked() throws ServiceException
    {

        if(!bestbooked.getData().isEmpty())
            bestbooked.getData().clear();


        bestbooked.getData().addAll(reservationService.bestbooked(boxName));



    }
}
