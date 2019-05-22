package sample;

import Interface.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffView implements Initializable {


    public Button logoutButton;
    public Button updatePQButton;
    public Button nwsltrButton;
    public Button transactionsButton;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void initialize(URL url, ResourceBundle rb) {



    }

    public void logoutButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(logoutButton,"sample.fxml","JFS");
    }

    public void updatePQButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(updatePQButton,"updateProductQuantity.fxml","UPDATE PRODUCT STOCK");
    }

    public void nwsltrButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(nwsltrButton,"newsletter.fxml","Newsletter");
    }

    public void transactionsButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(transactionsButton,"transactionStaff.fxml","Transactions");
    }
}
