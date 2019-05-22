package sample;

import Connectivity.Connectivity;
import Database.Transaction;
import Interface.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewMyTransactions implements Initializable {
    public Button backButton;
    public TableView tTable;
    public TableColumn tIdC;
    public TableColumn tNameC;
    public TableColumn tEmailC;
    public TableColumn tAmountC;
    public TableColumn tBoughtC;
    public TableColumn tDateC;

    String customerEmail = "mangalika@gmail.com";
    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rslt = null;
    public ObservableList<Transaction> transactions;


    public void initialize(URL url, ResourceBundle rb) {

        transactions = FXCollections.observableArrayList();

        setCellTable();
        loadData(customerEmail);

    }



    private void setCellTable() {
        tIdC.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        tNameC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tEmailC.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        tAmountC.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tBoughtC.setCellValueFactory(new PropertyValueFactory<>("bought"));
        tDateC.setCellValueFactory(new PropertyValueFactory<>("date"));
    }


    private void loadData(String email) {
        String sql = "SELECT * FROM transactions WHERE customerEmail=?";

        try {
            pstmt = connec.prepareStatement(sql);
            pstmt.setString(1,email);
            rslt = pstmt.executeQuery();

            while(rslt.next()){
                transactions.add(new Transaction(rslt.getInt(1),rslt.getString(2),rslt.getString(3),rslt.getString(4),rslt.getString(5),rslt.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        tTable.setItems(transactions);
    }



    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"customerView.fxml","Customer View");
    }

}
