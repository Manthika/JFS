package sample;

import Connectivity.Connectivity;
import Database.Product;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductStock implements Initializable {
    public TableView<Product> pTable;
    public TableColumn pIdC;
    public TableColumn pNameC;
    public TableColumn pSizeC;
    public TableColumn pCodeC;
    public TableColumn pQuantityC;
    public TableColumn pUpdateC;
    public Button backButton;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rslt = null;
    public ObservableList<Product> products;

    public void initialize(URL url, ResourceBundle rb) {

        products = FXCollections.observableArrayList();

        setCellTable();
        loadData();

    }


    public void setCellTable(){
        pIdC.setCellValueFactory(new PropertyValueFactory<>("productId"));
        pNameC.setCellValueFactory(new PropertyValueFactory<>("productName"));
        pSizeC.setCellValueFactory(new PropertyValueFactory<>("productSize"));
        pCodeC.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        pQuantityC.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pUpdateC.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    public void loadData(){

        String sql = "SELECT * FROM products";

        try {
            pstmt = connec.prepareStatement(sql);
            rslt = pstmt.executeQuery();

            while(rslt.next()){
                products.add(new Product(rslt.getInt(1),rslt.getString(2),rslt.getString(3),rslt.getString(4),rslt.getString(5),rslt.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        pTable.setItems(products);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"adminPanel.fxml","Admin Panel");
    }

    /*public int[] convertToInt(ObservableList<Product> products){

        int[] quant = new int[products.size()];

        for(int i = 0; i < products.size(); i++){
            quant[i] = Integer.parseInt(products.get(i).getQuantity());
        }

        return quant;
    }

    public void sort(int arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }


    public void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
*/

}
