package sample;

import Interface.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class AdminPanel {
    public Button addStaffButton;
    public Button transactionButton;
    public Button pStockButton;
    public Button backButton;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"ownerView.fxml","Owner View");
    }

    public void pStockButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(pStockButton,"productStock.fxml","Product Stock");
    }

    public void transactionButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(transactionButton,"transactionView.fxml","Transactions");
    }

    public void addStaffButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(addStaffButton,"addStaff.fxml","Add Staff");
    }
}
