package sample;

import Connectivity.Connectivity;
import Interface.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import validation.registerValidation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProductQuantity {
    public TextField fl100;
    public TextField fl200;
    public TextField fl300;
    public TextField si90;
    public TextField si60;
    public TextField si30;
    public TextField swLarge;
    public TextField swMedium;
    public TextField swSmall;
    public Button backButton;
    public Button fl100AddButton;
    public Button fl200AddButton;
    public Button fl300AddButton;
    public Button si90AddButton;
    public Button si60AddButton;
    public Button si30AddButton;
    public Button swLargeAddButton;
    public Button swMediumAddButton;
    public Button swSmallAddButton;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    registerValidation validate = new registerValidation();

    PreparedStatement pstmt = null;
    ResultSet rslt = null;
    PreparedStatement pstmtUpdate = null;
    public int rsltUpdate = 0;

    public void backButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(backButton,"staffView.fxml","Staff View");
    }

    public void fl100AddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(fl100,"1");
        if(newQ != 0){
            update(newQ,"1",fl100);
        }
    }

    public void fl200AddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(fl200,"2");
        if(newQ != 0){
            update(newQ,"2",fl200);

        }
    }

    public void fl300AddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(fl300,"3");
        if(newQ != 0){
            update(newQ,"3",fl300);

        }
    }

    public void si90AddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(si90,"6");
        if(newQ != 0){
            update(newQ,"6",si90);

        }
    }

    public void si60AddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(si60,"5");
        if(newQ != 0){
            update(newQ,"5",si60);

        }
    }

    public void si30AddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(si30,"4");
        if(newQ != 0){
            update(newQ,"4",si30);

        }
    }

    public void swLargeAddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(swLarge,"9");
        if(newQ != 0){
            update(newQ,"9",swLarge);

        }
    }

    public void swMediumAddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(swMedium,"8");
        if(newQ != 0){
            update(newQ,"8",swMedium);

        }
    }

    public void swSmallAddButtonClicked(ActionEvent actionEvent) {
        int newQ = getNewQuantity(swSmall,"7");
        if(newQ != 0){
            update(newQ,"7",swSmall);
        }

    }

    public int getNewQuantity(TextField product, String id){

        boolean valid = validate.isNumNOTEmpty(product,"Quantity");
        String sql = "SELECT * FROM products WHERE productId="+id;
        int newQuantity = 0;

        if(valid){

            int addingQuantity = Integer.parseInt(product.getText());
            try {
                pstmt = connec.prepareStatement(sql);
                rslt = pstmt.executeQuery();
                if(rslt.next()){
                    int oldQuantity = Integer.parseInt(rslt.getString("quantity"));
                    newQuantity = oldQuantity + addingQuantity;
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return newQuantity;
    }

    public void update(int newQuantity, String id, TextField product){
        /*boolean valid = validate.isNumNOTEmpty(product,"Quantity");*/
        String UpdateSql = "UPDATE products SET quantity=? WHERE productId="+id;

       /* if(valid){*/
            try {
                pstmtUpdate = connec.prepareStatement(UpdateSql);
                pstmtUpdate.setString(1,Integer.toString(newQuantity));
                rsltUpdate = pstmtUpdate.executeUpdate();

                if(rsltUpdate == 1){
                    AlertBox.displayAlertBox("Success","Successfully added!");
                }

            } catch (SQLException e) {
                System.err.println(e);
            }
        /*}*/

    }



}
