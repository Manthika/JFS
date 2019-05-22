package sample;

import Interface.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class OwnerView implements Initializable {


    public Button customerViewButton;
    public Button adminPanelButton;
    public Button logoutButton;
    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void initialize(URL url, ResourceBundle rb) {



    }

    public void customerViewButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(customerViewButton,"customerView.fxml","Customer");
    }

    public void adminPanelButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(adminPanelButton,"adminPanel.fxml","Admin Panel");
    }

    public void logoutButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(logoutButton,"sample.fxml","JFS");
    }
}
