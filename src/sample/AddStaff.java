package sample;

import Connectivity.Connectivity;
import Database.Country;
import Interface.SceneSwitcher;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import validation.registerValidation;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStaff  implements Initializable {
    public Button registerButton;
    public Label errorPassword;
    public Label errorGender;
    public Label errorDOB;
    public Label errorMobile;
    public Label errorEmail;
    public Label errorLastName;
    public Label errorFirstName;
    public JFXTextField address;
    public RadioButton otherGender;
    public RadioButton femaleGender;
    public RadioButton maleGender;
    public ToggleGroup genderToggle = new ToggleGroup();
    public JFXPasswordField confirmPassword;
    public JFXPasswordField password;
    public JFXTextField yearDOB;
    public JFXTextField mobile;
    public JFXTextField email;
    public JFXTextField lastName;
    public JFXTextField firstName;
    public JFXTextField monthDOB;
    public JFXTextField dayDOB;
    public Button backButton;

    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    registerValidation validate = new registerValidation();
    SceneSwitcher sceneSwitcher = new SceneSwitcher();



    public void initialize(URL url, ResourceBundle rb) {

        maleGender.setToggleGroup(genderToggle);
        maleGender.setUserData("MALE");
        femaleGender.setUserData("FEMALE");
        otherGender.setUserData("OTHER");
        femaleGender.setToggleGroup(genderToggle);
        otherGender.setToggleGroup(genderToggle);
        maleGender.setSelected(true);

    }

    public void registerButtonClicked(ActionEvent actionEvent) throws SQLException {

        boolean isFirstNameEmpty = validate.emptyError(firstName,errorFirstName,"First name is required!");
        boolean isFirstNameNum = validate.isNotNum(firstName,errorFirstName,"First name can't be numbers!");
        boolean isLastNameEmpty = validate.emptyError(lastName,errorLastName,"Last name is required!");
        boolean isLastNameNum = validate.isNotNum(lastName,errorLastName,"Last name can't be numbers!");
        boolean isEmailEmpty = validate.emptyError(email,errorEmail,"Email address is required!");
        boolean isPasswordEmpty = validate.emptyError(password,errorPassword,"Password is required!");
        boolean isDOBValid = validate.isValidDOB(dayDOB,monthDOB,yearDOB,errorDOB);
        boolean isemailValid = validate.isValidEmailAddress(email);
        boolean isPasswordValid = validate.isValidPassword(password,confirmPassword,errorPassword);
        boolean isPasswordMatched = validate.isPasswordMatch(password,confirmPassword);
        boolean isUserNameExist = validate.checkUsernameExists(email.getText(),connec,errorEmail);


        if(isFirstNameEmpty && isFirstNameNum && isLastNameEmpty && isLastNameNum && isEmailEmpty && isPasswordEmpty && isDOBValid && isPasswordValid && isemailValid && isPasswordMatched && isUserNameExist){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO `users` (`firstName`,`lastName`,`email`,`mobile`,`address`,`dob`,`gender`,`password`,`access`)\n" +
                    "VALUES (?,?,?,?,?,?,?,?,?);";


            String Gender = genderToggle.getSelectedToggle().getUserData().toString();
            String DOB = yearDOB.getText()+"-"+monthDOB.getText()+"-"+dayDOB.getText();



            try {
                pstmt = connec.prepareStatement(sql);
                pstmt.setString(1,firstName.getText());
                pstmt.setString(2,lastName.getText());
                pstmt.setString(3,email.getText());
                pstmt.setString(4,mobile.getText());
                pstmt.setString(5,address.getText());
                pstmt.setString(6,DOB);
                pstmt.setString(7,Gender);
                pstmt.setString(8,password.getText());
                pstmt.setString(9,"Staff");

                int i = pstmt.executeUpdate();
                System.out.println("staff info update status = " + i);

                if(i == 1){
                    AlertBox.displayAlertBox("Congratulations!","You have successfully added a staff member");
                    sceneSwitcher.switchScene(registerButton,"adminPanel.fxml","Admin Panel");
                }else{
                    AlertBox.displayAlertBox("Error","Database couldn't be updated");
                }


            } catch (SQLException e) {
                System.err.println(e);
            }finally {
                pstmt.close();
            }



        }else{
            System.out.println("Validation failed");
        }



    }

    public void backButtonClicked(ActionEvent actionEvent) {

        sceneSwitcher.switchScene(backButton,"adminPanel.fxml","Admin Panel");
    }



}
