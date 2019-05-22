package validation;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.AlertBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;


public class registerValidation {

    public boolean fieldNotEmpty(TextField textFieldId){
        boolean answer = false;
        if(textFieldId.getText().length() != 0 || !textFieldId.getText().isEmpty()){
            answer = true;
        }
        return answer;
    }
    public boolean fieldIsNumber(TextField textFieldId){
        boolean answer = false;
        if(textFieldId.getText().matches("([0-9]+(\\.[0-9]+)?)+")){
            answer = true;
        }
        return answer;
    }

    public boolean emptyError(TextField text, Label label, String errorMessage){
        boolean b = true;
        String msg = null;
        if(!fieldNotEmpty(text)){
            b = false;
            msg = errorMessage;
            text.setPromptText("");
        }
        label.setText(msg);

        return b;
    }

    public boolean isNotNum(TextField text, Label label, String errorMessage){
        boolean b = true;
        String msg = null;
        if(fieldIsNumber(text)){
            b = false;
            msg = errorMessage;
            text.setPromptText("");
        }
        label.setText(msg);

        return b;
    }

    public boolean isNumNOTEmpty(TextField textField,String field){
        boolean b;
        String msg = null;
        if(!fieldNotEmpty(textField)){
            b = false;
            msg = field+" is required!!";
            AlertBox.displayAlertBox("Error",msg);
            textField.clear();
        }else if(!fieldIsNumber(textField)){
            b = false;
            msg = field+" should be entered in numbers!! ";
            AlertBox.displayAlertBox("Error",msg);
            textField.clear();
        }else{
            b = true;
        }



        return b;
    }

    public boolean isNumMaxLengthNOTEmpty(TextField textField, int maxLength, Label label,String field){
        boolean b;
        String msg = null;
        if(!fieldNotEmpty(textField)){
            b = false;
            msg = field+" is required!!";
            textField.clear();
        }else if(!fieldIsNumber(textField)){
            b = false;
            msg = field+" should be entered in numbers!! ";
            textField.clear();
        }else if(textField.getLength()>maxLength){
            b = false;
            msg = field+" max length is " + maxLength;
            textField.clear();
        }else{
            b = true;
        }

        label.setText(msg);

        return b;
    }

    public boolean isValidDOB(TextField day,TextField month,TextField year, Label label){
        boolean b;
        String msg = null;
        /*int dayDOB = Integer.parseInt(day.getText());
        int monthDOB = Integer.parseInt(month.getText());
        int yearDOB = Integer.parseInt(year.getText());*/
        if(!fieldNotEmpty(day) || !fieldNotEmpty(month) || !fieldNotEmpty(year)){
            b = false;
            msg = "DOB is required!!";
            day.clear();
            month.clear();
            year.clear();
        }else if(!fieldIsNumber(day) || !fieldIsNumber(month) || !fieldIsNumber(year)){
            b = false;
            msg = "DOB should be entered in numbers!! ";
            day.clear();
            month.clear();
            year.clear();
        }/*else if(!((dayDOB<=31 && dayDOB>0) && (yearDOB>1880 && yearDOB<2019) && (monthDOB>0 && monthDOB<=12))){
            b = false;
            msg = "DOB is invalid!!";
            day.clear();
            month.clear();
            year.clear();
        }*/else{
            b = true;
        }

        label.setText(msg);

        return b;
    }

    public boolean isValidEmailAddress(TextField emailText) {
        String email = emailText.getText();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);

        boolean b = m.matches();
        if(b == false && fieldNotEmpty(emailText)){
            emailText.setPromptText("Invalid Email!!");
            emailText.clear();
        }
        return b;
    }


    public boolean isValidPassword(PasswordField passwordText,PasswordField confirmText,Label passLabel) {

        String password = passwordText.getText();
        String pPattern = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pPattern);
        java.util.regex.Matcher m = p.matcher(password);

        boolean b = m.matches();
        if(b == false && fieldNotEmpty(passwordText)){
            passwordText.setPromptText("Password not Strong!!");
            confirmText.setPromptText("");
            passLabel.setText("Should contain at least 8 chars\n" +
                    "Should contain at least one digit\n" +
                    "Should contain at least one lower alpha char and one upper alpha char\n" +
                    "Should contain at least one char within a set of special chars (@#%$^ etc.)\n" +
                    "Should NOT contain space, tab, etc.");
            passwordText.clear();
            confirmText.clear();
        }
        return b;
    }

    public boolean isPasswordMatch(PasswordField passwordT, PasswordField confirmPasswordT){
        boolean b;
        if(passwordT.getText().equals(confirmPasswordT.getText())){
            b = true;
        }else{
            b = false;
            confirmPasswordT.setText("");
            confirmPasswordT.setPromptText("Password did not match! ");
        }


        return b;
    }



    public boolean checkUsernameExists(String username, Connection connection, Label label)
    {
        boolean usernameExists = true;

        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM `users` ORDER BY `userId` ASC");
            ResultSet r1=st.executeQuery();
            String usernameCounter;
            if(r1.next())
            {
                usernameCounter =  r1.getString("email");
                if(usernameCounter.equals(username))
                {
                    label.setText("email already exists");
                    usernameExists = false;
                }
            }

        }catch (SQLException e){
            System.err.println(e);
        }

        return usernameExists;
    }







}
