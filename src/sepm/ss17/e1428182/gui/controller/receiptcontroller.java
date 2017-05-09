package sepm.ss17.e1428182.gui.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.domain.Receipt;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.DAOException;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.impl.BoxServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class receiptcontroller {

    @FXML
    private Label clientname;

    @FXML
    private Label id;

    @FXML
    private Label horse;

    @FXML
    private Label totalrate;

    @FXML
    private TableView<Receipt> receiptTableView;

    @FXML
    private TableColumn<Receipt, String> idcol;


    private Stage dialogStage;

    private PensionHouse pensionHouse;
    private String horsename = "";
    private ObservableList<Reservation> reservationList;
    private BoxService boxService = BoxServiceImpl.getInstance();
    private static Logger LOGGER = LoggerFactory.getLogger(receiptcontroller.class);

    public receiptcontroller() throws ServiceException {
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
        reservationList = FXCollections.observableArrayList();
        receiptTableView.setItems(pensionHouse.receipts());

    }


    /**
     * LIST WITH RECEIPT ID
     */
    @FXML
    private void initialize()
    {

        idcol.setCellValueFactory(celldata -> new ReadOnlyStringWrapper(celldata.getValue().getReceiptid().toString()));
        showReceipt(null);
        receiptTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showReceipt(newValue)));

    }

    /**
     * SHOWS RECEIPT IN LABEL FORM
     * @param receipt
     */
    private  void showReceipt(Receipt receipt)
    {



        if(receipt != null)
        {
            try
            {
            id.setText(receipt.getReceiptid().toString());
            for (int i = 0; i < boxService.readAllBoxes().size(); i++) {

                if (receipt.getB_id().equals(boxService.readAllBoxes().get(i).getId()))
                {
                    horsename  = boxService.readAllBoxes().get(i).getHorsename().getValue();
                }

            }

            horse.setText(horsename);
            totalrate.setText(Double.toString(receipt.getTotalprice()));
            clientname.setText(receipt.getClientname());
            horsename = "";
            }catch (ServiceException es)
            {
                LOGGER.error("Couldnt find the Box");
            }

        }
    }

    /**
     * CLOSES THE DIALOG STAGE
     */
    @FXML
    private void close()
    {
        this.dialogStage.close();
    }







}
