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

public class Register  implements Initializable {
    public Button registerButton;
    public Label errorPassword;
    public Label errorCountry;
    public Label errorGender;
    public Label errorDOB;
    public Label errorMobile;
    public Label errorEmail;
    public Label errorLastName;
    public Label errorFirstName;
    public JFXTextField notes;
    public JFXTextField address;
    public RadioButton otherGender;
    public RadioButton femaleGender;
    public RadioButton maleGender;
    public ToggleGroup genderToggle = new ToggleGroup();
    public JFXPasswordField confirmPassword;
    public JFXPasswordField password;
    public JFXTextField yearDOB;
    public JFXTextField occupation;
    public JFXTextField mobile;
    public JFXTextField email;
    public JFXTextField lastName;
    public JFXTextField firstName;
    public JFXTextField monthDOB;
    public JFXTextField dayDOB;
    public Button backButton;
    public ComboBox<Country> country;
    public JFXTextField nameOnCard;
    public JFXTextField cardNumber;
    public JFXTextField eMonth;
    public JFXTextField eYear;
    public JFXTextField cvv;
    public RadioButton visa;
    public RadioButton masterCard;
    public ToggleGroup typeToggle = new ToggleGroup();
    public Label errorCardName;
    public Label errorCardNumber;
    public Label errorEmonth;
    public Label errorEyear;
    public Label errorCVV;
    Connectivity connection = new Connectivity();
    Connection connec = connection.getConnection();
    registerValidation validate = new registerValidation();
    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    ObservableList<Country> countries;
    String countryName;



    public void initialize(URL url, ResourceBundle rb) {

        PreparedStatement pstmtGetCountry = null;
        ResultSet result = null;
        String sqlGetCountry = "SELECT * FROM `apps_countries`";
        countries = FXCollections.observableArrayList();

        try {
            pstmtGetCountry = connec.prepareStatement(sqlGetCountry);
            result = pstmtGetCountry.executeQuery();
            while(result.next()){
                countries.add(new Country(result.getString(2),result.getString(3)));
            }
            country.setItems(countries);
            /*country.setEditable(true);*/
            country.getSelectionModel().selectFirst();
            System.out.println("done reading countries");
        } catch (SQLException e) {
            System.err.println(e);
        }finally {
            try {
                assert pstmtGetCountry != null;
                pstmtGetCountry.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        country.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country object) {
                return object.getCountry_name();
            }

            @Override
            public Country fromString(String string) {
                return null;
            }
        });



        /*country.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                countryName = newValue.getCountry_name();
            }
        });*/



        maleGender.setToggleGroup(genderToggle);
        maleGender.setUserData("MALE");
        femaleGender.setUserData("FEMALE");
        otherGender.setUserData("OTHER");
        femaleGender.setToggleGroup(genderToggle);
        otherGender.setToggleGroup(genderToggle);
        maleGender.setSelected(true);

        visa.setToggleGroup(typeToggle);
        masterCard.setToggleGroup(typeToggle);
        visa.setUserData("VISA");
        masterCard.setUserData("MASTER CARD");
        visa.setSelected(true);

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


        boolean isCardNameEmpty = validate.emptyError(nameOnCard,errorCardName,"The name on card is required");
        boolean isCardNameNum = validate.isNotNum(nameOnCard,errorCardName,"The name on card can't be numbers");
        boolean isCardNumberEmpty = validate.isNumMaxLengthNOTEmpty(cardNumber,16,errorCardNumber,"Card number");
        boolean isEmonthEmpty = validate.isNumMaxLengthNOTEmpty(eMonth,2,errorEmonth,"Expiry month");
        boolean isEyearEmpty = validate.isNumMaxLengthNOTEmpty(eYear,2,errorEyear,"Expiry year");
        boolean isCVVEmpty = validate.isNumMaxLengthNOTEmpty(cvv,3,errorCVV,"CVV");



       if(isCardNameNum && isFirstNameEmpty && isFirstNameNum && isLastNameEmpty && isLastNameNum && isEmailEmpty && isPasswordEmpty && isDOBValid && isPasswordValid && isemailValid && isPasswordMatched && isUserNameExist && isCardNameEmpty && isCardNumberEmpty && isEmonthEmpty && isEyearEmpty && isCVVEmpty){
           PreparedStatement pstmt = null;
           String sql = "INSERT INTO `users` (`firstName`,`lastName`,`email`,`mobile`,`occupation`,`address`,`dob`,`gender`,`password`,`notes`,`country`,`access`)\n" +
                   "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";


           String Gender = genderToggle.getSelectedToggle().getUserData().toString();
           String DOB = yearDOB.getText()+"-"+monthDOB.getText()+"-"+dayDOB.getText();
           countryName =  country.getSelectionModel().getSelectedItem().getCountry_name();
           System.out.println(countryName);


           PreparedStatement pstmt2 = null;
           String sql2 = "INSERT INTO `card_detail` (`email`,`name`,`number`,`eMonth`,`eYear`,`cvv`,`type`)\n" +
                   "VALUES (?,?,?,?,?,?,?);";

           String cardType = typeToggle.getSelectedToggle().getUserData().toString();



           try {
               pstmt = connec.prepareStatement(sql);
               pstmt.setString(1,firstName.getText());
               pstmt.setString(2,lastName.getText());
               pstmt.setString(3,email.getText());
               pstmt.setString(4,mobile.getText());
               pstmt.setString(5,occupation.getText());
               pstmt.setString(6,address.getText());
               pstmt.setString(7,DOB);
               pstmt.setString(8,Gender);
               pstmt.setString(9,password.getText());
               pstmt.setString(10,notes.getText());
               pstmt.setString(11,countryName);
               pstmt.setString(12,"Customer");

               int i = pstmt.executeUpdate();
               System.out.println("customer info update status = " + i);


               pstmt2 = connec.prepareStatement(sql2);
               pstmt2.setString(1,email.getText());
               pstmt2.setString(2,nameOnCard.getText());
               pstmt2.setString(3,cardNumber.getText());
               pstmt2.setString(4,eMonth.getText());
               pstmt2.setString(5,eYear.getText());
               pstmt2.setString(6,cvv.getText());
               pstmt2.setString(7,cardType);

               int j = pstmt2.executeUpdate();
               System.out.println("credit card info update status = " + j);

               if(i == 1 && j==1){
                   AlertBox.displayAlertBox("Congratulations!","You have successfully registered to JFS");
                   sceneSwitcher.switchScene(registerButton,"sample.fxml","JFS");
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
           AlertBox.displayAlertBox("Error","Registration failed!");
       }



    }

    public void backButtonClicked(ActionEvent actionEvent) {

        sceneSwitcher.switchScene(backButton,"sample.fxml","JFS");
    }



}
