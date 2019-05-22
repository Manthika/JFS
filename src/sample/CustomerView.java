package sample;

import Database.LoginDetail;
import ImplementedLinkedList.MyLinkedList;
import Interface.SceneSwitcher;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import validation.CustomerViewValidation;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerView implements Initializable {


    public ComboBox flTypes;
    public ComboBox siTypes;
    public ComboBox swTypes;
    public JFXTextField flQuantity;
    public JFXTextField swQuantity;
    public JFXTextField siQuantity;
    public Label customerEmail;
    public Button vmyacc;
    public Button checkoutButton;
    public Button logoutButton;
    public Button flAddButton;
    public Button siAddButton;
    public Button swAddButton;
    public Label flCartPrice;
    public Label siCartPrice;
    public Label swCartPrice;
    public Button inqButton;
    public Button viewTransButton;


    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    MyLinkedList linkedList = new MyLinkedList();
    int no = 1;


    /*String mailID;

    public void setMailLabel(String text){
        mailID = text;
        System.out.println("mail-  " + mailID);
    }*/

    public void initialize(URL url, ResourceBundle rb) {


        flTypes.getItems().addAll("100m roll","200m roll","300m roll");
        flTypes.setValue("100m roll");
        siTypes.getItems().addAll("30 grams","60 grams","90 grams");
        siTypes.setValue("30 grams");
        swTypes.getItems().addAll("small","medium","large");
        swTypes.setValue("small");



    }

    public void vmyaccClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(vmyacc,"viewMyAccount.fxml","Update your account");

    }

    public void inqButtonCliciked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(inqButton,"inquiry.fxml","Inquiries");
    }

    public void checkoutButtonClicked(ActionEvent actionEvent) {
        System.out.println(linkedList);

        int sum = swPriceSum+siPriceSum+flPriceSum;
        if(sum>0){
            sceneSwitcher.switchScene(checkoutButton,"confirmPayment.fxml","Confirm Payment");
        }else {
            AlertBox.displayAlertBox("Error","Your cart is empty");
        }

    }

    public void logoutButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(logoutButton,"sample.fxml","JFS");
    }

    CustomerViewValidation customerViewValidation = new CustomerViewValidation();

    int swPriceSum = 0;
    public void swAddButtonClicked(ActionEvent actionEvent) {
        /*boolean isTypeValid = customerViewValidation.emptyComboError(swTypes);*/
        boolean isQuantityValid = customerViewValidation.isNumNOTEmpty(swQuantity);
        String swValue = swTypes.getSelectionModel().getSelectedItem().toString();
        System.out.println(swValue);

        int swTypePrice = 0;

        if(isQuantityValid){
            switch (swValue) {
                case "small":
                    swTypePrice = 250;
                    break;
                case "medium":
                    swTypePrice = 400;
                    break;
                case "large":
                    swTypePrice = 550;
                    break;
            }

            int total = swTypePrice * Integer.parseInt(swQuantity.getText());
            swPriceSum = swPriceSum + total;
            swCartPrice.setText("SW Price : Rs. " + swPriceSum + "/=");

            addButton("Swivels","SW",swValue,swTypePrice,no,swQuantity,linkedList);
            no++;
        }
    }

    int siPriceSum = 0;
    public void siAddButtonClicked(ActionEvent actionEvent) {
        /*boolean isTypeValid = customerViewValidation.emptyComboError(swTypes);*/
        boolean isQuantityValid = customerViewValidation.isNumNOTEmpty(siQuantity);
        String siValue = siTypes.getSelectionModel().getSelectedItem().toString();
        System.out.println(siValue);


        int siTypePrice = 0;

        if(isQuantityValid){



            switch (siValue){
                case "30 grams":
                    siTypePrice = 150;
                    break;
                case "60 grams":
                    siTypePrice = 275;
                    break;
                case "90 grams":
                    siTypePrice = 400;
                    break;
            }

            int total = siTypePrice * Integer.parseInt(siQuantity.getText());
            siPriceSum = siPriceSum + total;
            siCartPrice.setText("SI Price : Rs. " + siPriceSum + "/=");


            addButton("Sinkers","SI",siValue,siTypePrice,no,siQuantity,linkedList);
            no++;

        }
    }

    int flPriceSum = 0;
    public void flAddButtonClicked(ActionEvent actionEvent) {
        /*boolean isTypeValid = customerViewValidation.emptyComboError(swTypes);*/
        boolean isQuantityValid = customerViewValidation.isNumNOTEmpty(flQuantity);
        String flValue = flTypes.getSelectionModel().getSelectedItem().toString();
        System.out.println(flValue);


        int flTypePrice = 0;

        if(isQuantityValid){
            switch (flValue){
                case "100m roll":
                    flTypePrice = 200;
                    break;
                case "200m roll":
                    flTypePrice = 350;
                    break;
                case "300m roll":
                    flTypePrice = 500;
                    break;
            }

            int total = flTypePrice * Integer.parseInt(flQuantity.getText());
            flPriceSum = flPriceSum + total;
            flCartPrice.setText("FL Price : Rs. " + flPriceSum + "/=");

            addButton("Fishing Line","FL",flValue,flTypePrice,no,flQuantity,linkedList);
            no++;
        }
    }

    public void addButton(String description,String pCode,String size, int cost, int no, TextField quantity, MyLinkedList linkedList){


            String pSize = size;
            int pCost = cost;
            int pQuantity = Integer.parseInt(quantity.getText());
            int pAmount = pCost*pQuantity;

            linkedList.insertAtProduct(no,description,pCode,pSize,pCost,pQuantity,pAmount);
            System.out.println(linkedList);
            no++;


    }


    public void viewTransButtonClicked(ActionEvent actionEvent) {
        sceneSwitcher.switchScene(viewTransButton,"viewMyTransactions.fxml","My Transactions");
    }
}
