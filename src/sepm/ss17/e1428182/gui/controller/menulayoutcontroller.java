package sepm.ss17.e1428182.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import sepm.ss17.e1428182.domain.Box;
import sepm.ss17.e1428182.exception.ServiceException;
import sepm.ss17.e1428182.gui.PensionHouse;


public class menulayoutcontroller {

    private PensionHouse pensionHouse;






    public void setPensionHouse(PensionHouse pensionHouse)
    {
        this.pensionHouse = pensionHouse;
    }

    /**
     * OPENS THE DIALOG STAGE FOR NEW BOX
     */
    @FXML
    private void handlenewBox()
    {
        //Box box = new Box();
        boolean okClicked = pensionHouse.showEditBox();
        if (okClicked)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("New Box");
            alert.setContentText("A New Box has been added successfully");
            alert.showAndWait();
        }


    }

    /**
     * OPENS SEARCH BOX DIALOG STAGE BY CALLING METHOD IN MAIN APP (PENSIONHOUSE)
     */
    @FXML
    private void searchBox()
    {
        pensionHouse.searchBox();
    }

    /**
     * OPENS STATISTICS DIALOG STAGE
     * @throws ServiceException
     */
    @FXML
    private void showStatistics() throws ServiceException
    {
        pensionHouse.showStatistics();
    }

    /**
     * CLOSES THE APP
     */
    @FXML
    private void close()
    {
        pensionHouse.close();
    }



}
