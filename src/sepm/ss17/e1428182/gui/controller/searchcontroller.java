package sepm.ss17.e1428182.gui.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.domain.Reservation;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;
import sepm.ss17.e1428182.service.BoxService;
import sepm.ss17.e1428182.service.impl.BoxServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class searchcontroller {

    @FXML
    private TableView<Box> boxTable;

    @FXML
    private TableColumn<Box, String> BoxName;

    @FXML
    private TableColumn<Box, String> HorseName;

    @FXML
    private ChoiceBox litter;

    @FXML
    private ChoiceBox window;

    @FXML
    private ChoiceBox position;

    boolean clicked = false;

    private PensionHouse pensionHouse;

    private List<Box> local = new ArrayList<>();

    private Stage dialogStage;

    private static  final Logger LOGGER = LoggerFactory.getLogger(searchcontroller.class);

    private BoxService service = BoxServiceImpl.getInstance();

    private ObservableList<Box> temp = FXCollections.observableArrayList();



    public searchcontroller() throws ServiceException {
    }


    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
        boxTable.setItems(pensionHouse.getData());
        litter.getItems().addAll("sawdust", "straw");


        position.getItems().addAll("inside", "outside");


        window.getItems().addAll("With Window", "Without Window");


    }


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

    @FXML
    private void initialize()
    {
        BoxName.setCellValueFactory(celldata -> celldata.getValue().getBoxname());
        HorseName.setCellValueFactory(celldata -> celldata.getValue().getHorsename());
    }


    /**
     * CALLS MAINAPP (PENSIONHOUSE) TO EDIT THE SELECTED BOX
     */
    @FXML
    private void edit()
    {

        try {


            Box tempBox = boxTable.getSelectionModel().getSelectedItem();
            if (tempBox == null) {
                ErrorAlert("Box Missing", "Please slect a Box first to Edit");
            }
            pensionHouse.getEditBox(tempBox);
        }catch(Exception io)
        {
            LOGGER.error("AN ERROR OCCURRED");
        }
    }

    /**
     * DELETES THE BOX FROM THE LIST
     */
    @FXML
    private void delete()
    {
        try {

            Box box = boxTable.getSelectionModel().getSelectedItem();

            if(box != null)
            {
               
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Box");
                alert.setHeaderText("Delete");
                alert.setContentText("delete selected Box?");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    service.deleteBox(box);
                    boxTable.setItems(pensionHouse.getData());
                    pensionHouse.refreshBoxlist();
                }


            }
            else
            {
                ErrorAlert("Box", "Select the Box to delete!");
            }
        }catch (ServiceException es)
        {

            ErrorAlert("Delete Box", "Couldn't Delete the Box");
        }

    }

    /**
     * SEARCHES THE BOX WITH GIVEN PARAMETERS
     */
    @FXML
    private void search()
    {

        try {



            if ((position.getSelectionModel().isEmpty() && litter.getSelectionModel().isEmpty() && window.getSelectionModel().isEmpty())) {
                ErrorAlert("Input", "You must select one or more given the Options above.");
            }

            if (position.getSelectionModel().getSelectedItem() != null && litter.getSelectionModel().isEmpty() && window.getSelectionModel().isEmpty()) {


                if (!temp.isEmpty())
                    temp.clear();
                local = service.searchbylocation(position.getValue().toString());
                for (int i = 0; i < local.size(); i++) {
                                  temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                position.getSelectionModel().clearSelection();
            }

            if(position.getSelectionModel().isEmpty() && litter.getSelectionModel().getSelectedItem() != null && window.getSelectionModel().isEmpty())
            {
               // local = service.searchbyhorsename(litter.getValue().toString());
                if (!temp.isEmpty()) {
                    temp.clear();
                }
                local = service.searchbylitter(litter.getValue().toString());
                for (int i = 0; i < local.size(); i++) {
                    temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                litter.getSelectionModel().clearSelection();
            }

            if(position.getSelectionModel().isEmpty() && litter.getSelectionModel().isEmpty() && window.getSelectionModel().getSelectedItem() != null)
            {
                if (!temp.isEmpty())
                    temp.clear();
               local = service.searchbywindow(window.getValue().toString().equals("With Window"));
                for (int i = 0; i < local.size(); i++) {
                    temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                window.getSelectionModel().clearSelection();
            }

            if(position.getSelectionModel().isEmpty() && litter.getSelectionModel().getSelectedItem() != null && window.getSelectionModel().getSelectedItem() != null)
            {
                if (!temp.isEmpty())
                    temp.clear();
                local = service.searchCombined(window.getValue().toString().equals("With Window"), litter.getValue().toString());
                for (int i = 0; i < local.size(); i++) {
                    temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                litter.getSelectionModel().clearSelection();
                window.getSelectionModel().clearSelection();
            }

            if(position.getSelectionModel().getSelectedItem() != null && litter.getSelectionModel().isEmpty() && window.getSelectionModel().getSelectedItem() != null)
            {
                if (!temp.isEmpty())
                    temp.clear();
                local = service.searchCombined(position.getValue().toString(),window.getValue().toString().equals("With Window"));
                for (int i = 0; i < local.size(); i++) {
                    temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                position.getSelectionModel().clearSelection();
                window.getSelectionModel().clearSelection();
            }

            if(position.getSelectionModel().getSelectedItem() != null && litter.getSelectionModel().getSelectedItem() != null && window.getSelectionModel().isEmpty())
            {
                if (!temp.isEmpty())
                    temp.clear();
                local = service.searchCombined(position.getValue().toString(), litter.getValue().toString());
                for (int i = 0; i < local.size(); i++) {
                    temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                position.getSelectionModel().clearSelection();
                litter.getSelectionModel().clearSelection();
            }

            if(position.getSelectionModel().getSelectedItem() != null && litter.getSelectionModel().getSelectedItem() != null && window.getSelectionModel().getSelectedItem() != null)
            {
                if (!temp.isEmpty())
                    temp.clear();
                local = service.searchCombined(position.getValue().toString(), litter.getValue().toString(), window.getValue().toString().equals("With Window"));
                for (int i = 0; i < local.size(); i++) {
                    temp.add(local.get(i));
                }
                boxTable.setItems(temp);
                position.getSelectionModel().clearSelection();
                litter.getSelectionModel().clearSelection();
                window.getSelectionModel().clearSelection();
            }





        }catch (ServiceException es)
        {
            ErrorAlert("Error Occurred", "Could not Read the Result.");
        }

    }

    /**
     * CLOSES THE DIALOG BOX
     */
    @FXML
    private void cancel()
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
