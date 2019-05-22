package sample;

import Connectivity.Connectivity;
import Interface.SceneSwitcher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import validation.NewsletterValidation;
import validation.loginValidation;

import java.sql.*;

public class Controller {

    public JFXTextField newsletterEmail;
    public JFXButton regButton;
    @FXML public JFXTextField loginUserName;
    public JFXPasswordField loginPassword;
    public JFXButton loginButton;
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    ViewMyAccount viewMyAccount = new ViewMyAccount();

    loginValidation validateLogin = new loginValidation();

    public void loginButtonClicked(ActionEvent actionEvent) {

        System.out.println(loginUserName.getText());



        boolean validateCustomer = validateLogin.CusLoginValidate(loginUserName.getText(),loginPassword.getText(),connec);
        boolean validateStaff = validateLogin.StaffLoginValidate(loginUserName.getText(),loginPassword.getText(),connec);
        boolean validateOwner = validateLogin.OwnerLoginValidate(loginUserName.getText(),loginPassword.getText(),connec);

        if(loginUserName.getText().equals("") || loginPassword.getText().equals("")){
            AlertBox.displayAlertBox("ERROR!","Both fields can't be empty!");
        }else{
            if(validateCustomer){
                sceneSwitcher.switchScene(loginButton,"customerView.fxml","Customer");
            }else if(validateStaff){
                sceneSwitcher.switchScene(loginButton,"staffView.fxml","Customer");
            }else if(validateOwner){
                sceneSwitcher.switchScene(loginButton,"ownerView.fxml","Customer");
            }else{
                AlertBox.displayAlertBox("ERROR!","Invalid Username or Password! ");
            }
        }


        String enteredValue = loginUserName.getText().toString();
        /*usernameEntered.setText("Username: " + enteredValue);*/

        System.out.println("From Controller: " + enteredValue);

        System.out.println("From ViewMyAccount: "+
                viewMyAccount.setAndReturnMail(enteredValue));





    }

    public void registerButtonClicked(ActionEvent actionEvent) {

        sceneSwitcher.switchScene(regButton,"register.fxml","Register");
    }

    NewsletterValidation validateEmail = new NewsletterValidation();

    public void newsletterButtonClicked(ActionEvent actionEvent) throws SQLException {


        boolean isNewsletterEmailEmpty = validateEmail.invalidError(newsletterEmail);
        boolean isValid = validateEmail.isValidEmailAddress(newsletterEmail);
        boolean isEmailExist = validateEmail.checkEmailExists(newsletterEmail.getText(),connec);

        if(isNewsletterEmailEmpty && isValid && isEmailExist){

            PreparedStatement pstmt = null;
            String sql = "INSERT INTO `nwemails` (`email`)\n" +
                    "VALUES (?);";



            try {
                pstmt = connec.prepareStatement(sql);
                pstmt.setString(1,newsletterEmail.getText());

                int i = pstmt.executeUpdate();
                System.out.println("newsletter email update status = " + i);
                AlertBox.displayAlertBox("Alert!","You have successfully signed up for the news letter");


            } catch (SQLException e) {
                System.err.println(e);
            }finally {
                pstmt.close();
            }

        }else{
            System.out.println("Validation failed");
        }



    }


}
