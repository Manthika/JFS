package sample;

import Connectivity.Connectivity;
import Database.Product;
import Database.Transaction;
import Interface.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionView implements Initializable {
    public Button backButton;
    public TableView<Transaction> tTable;
    public TableColumn tIdC;
    public TableColumn tNameC;
    public TableColumn tEmailC;
    public TableColumn tAmountC;
    public TableColumn tBoughtC;
    public TableColumn tDateC;
    public TextField searchName;
    public ComboBox sYear;
    public ComboBox sMonth;
    public ComboBox sDate;
    public Button nameSearchBtn;
    public Button dateSelectBtn;
    public Button sortById;
    public TextField sbyQ;
    public Button sByQuant;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rslt = null;
    public ObservableList<Transaction> transactions;

    public void initialize(URL url, ResourceBundle rb) {

        sYear.getItems().addAll("2018", "2019", "2020", "2021", "2022", "2023");
        sYear.setValue("2018");
        sMonth.getItems().addAll("01","02","03","04","06","07","08","09","10","11","12");
        sMonth.setValue("01");
        sDate.getItems().addAll("01","02","03","04","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
        sDate.setValue("01");

        transactions = FXCollections.observableArrayList();

        setCellTable();
        loadData();

    }



    private void setCellTable() {
        tIdC.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        tNameC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tEmailC.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        tAmountC.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tBoughtC.setCellValueFactory(new PropertyValueFactory<>("bought"));
        tDateC.setCellValueFactory(new PropertyValueFactory<>("date"));
    }


    private void loadData() {
        String sql = "SELECT * FROM transactions";

        try {
            pstmt = connec.prepareStatement(sql);
            rslt = pstmt.executeQuery();

            while(rslt.next()){
                transactions.add(new Transaction(rslt.getInt(1),rslt.getString(2),rslt.getString(3),rslt.getString(4),rslt.getString(5),rslt.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        tTable.setItems(transactions);
    }



    public void backButtonClickedtable(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"adminPanel.fxml","Admin Panel");
    }


    PreparedStatement pstmt2 = null;
    ResultSet rslt2 = null;
    public void nameSearchBtnClicked(ActionEvent actionEvent) {
        tTable.getItems().clear();

        String sql = "SELECT * FROM `transactions` WHERE customerName LIKE '%"+searchName.getText()+"%'";

        try {
            pstmt2 = connec.prepareStatement(sql);
            rslt2 = pstmt2.executeQuery();

            while(rslt2.next()){
                transactions.add(new Transaction(rslt2.getInt(1),rslt2.getString(2),rslt2.getString(3),rslt2.getString(4),rslt2.getString(5),rslt2.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }


        tTable.setItems(transactions);
    }


    PreparedStatement pstmt3 = null;
    ResultSet rslt3 = null;
    public void dateSelectBtnClicked(ActionEvent actionEvent) {
        tTable.getItems().clear();

        String year = sYear.getSelectionModel().getSelectedItem().toString();
        String month = sMonth.getSelectionModel().getSelectedItem().toString();
        String day = sDate.getSelectionModel().getSelectedItem().toString();
        String date = year+"-"+month+"-"+day;

        String sql = "SELECT * FROM `transactions` WHERE date LIKE '%"+date+"%'";

        try {
            pstmt3 = connec.prepareStatement(sql);
            rslt3 = pstmt3.executeQuery();

            while(rslt3.next()){
                transactions.add(new Transaction(rslt3.getInt(1),rslt3.getString(2),rslt3.getString(3),rslt3.getString(4),rslt3.getString(5),rslt3.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }


        tTable.setItems(transactions);
    }

    public void sortByIdClicked(ActionEvent actionEvent) {
        sort(tTable);
    }




    public void sort(TableView tTable) {

        ArrayList<Transaction> result = new ArrayList<Transaction>(tTable.getItems());
        int smallInt = 0;
        int j=0;
        int smallIntIndex = 0;

        for(int i=0; i<result.size() - 1; i++){

            smallInt = Integer.parseInt(result.get(i).getAmount());
            smallIntIndex = i;

            for(j=i+1; j<result.size(); j++){
                if(Integer.parseInt(result.get(j).getAmount()) < smallInt){
                    smallInt = Integer.parseInt(result.get(j).getAmount());
                    smallIntIndex = j;
                }
            }

            if (i != smallIntIndex) {
                Transaction temp = result.get(smallIntIndex);
                result.set(smallIntIndex, result.get(i));
                result.set(i, temp);
            }
        }

        ObservableList<Transaction> print = FXCollections.observableArrayList(result);

        tTable.setItems(print);


    }




    public ArrayList<Transaction> sSort(TableView tTable) {

        ArrayList<Transaction> result = new ArrayList<Transaction>(tTable.getItems());
        int smallInt = 0;
        int j=0;
        int smallIntIndex = 0;

        for(int i=0; i<result.size() - 1; i++){

            smallInt = Integer.parseInt(result.get(i).getAmount());
            smallIntIndex = i;

            for(j=i+1; j<result.size(); j++){
                if(Integer.parseInt(result.get(j).getAmount()) < smallInt){
                    smallInt = Integer.parseInt(result.get(j).getAmount());
                    smallIntIndex = j;
                }
            }

            if (i != smallIntIndex) {
                Transaction temp = result.get(smallIntIndex);
                result.set(smallIntIndex, result.get(i));
                result.set(i, temp);
            }
        }


        return result;
    }




    public void bSearch(ArrayList<Transaction> array, TableView tTable, int x){

        ArrayList<Transaction> result = new ArrayList<Transaction>();

        int left = 0;
        int right = array.size() - 1;

        while(left <= right){

            int mid = left + ((right-left)/2);

            int no = 0;
            
            try{
                no = Integer.parseInt(array.get(mid).getAmount());
            }catch(NumberFormatException ex){
                System.err.println(ex);
            }

            if(no == x){


                try{
                    result.add(array.get(mid));
                }catch(Exception ex){
                    System.err.println(ex);
                }

            }else if(x < no){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        ObservableList<Transaction> print = FXCollections.observableArrayList(result);
        tTable.setItems(print);
    }


    public void sByQuantClicked(ActionEvent actionEvent) {

        ArrayList<Transaction> newArr = new ArrayList<Transaction>();
        newArr = sSort(tTable);
        try{
            bSearch(newArr, tTable, Integer.parseInt(sbyQ.getText()));
        }catch(Exception e){
            System.err.println(e);
        }

    }
}
