package validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.AlertBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsletterValidation {

    public boolean mailNotEmpty(TextField textFieldId){
        boolean answer = false;
        if(textFieldId.getText().length() != 0 || !textFieldId.getText().isEmpty()){
            answer = true;
            textFieldId.setPromptText("");
        }
        return answer;
    }

    public boolean invalidError(TextField text){
        boolean b = true;
        if(!mailNotEmpty(text)){
            b = false;

        }
        text.setPromptText("Email is Empty!!");

        return b;
    }

    public boolean isValidEmailAddress(TextField emailText) {
        String email = emailText.getText();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);

        boolean b = m.matches();
        if(b == false && mailNotEmpty(emailText)){
            emailText.setPromptText("Invalid Email!!");
            emailText.clear();
        }
        return b;
    }


    public boolean checkEmailExists(String username, Connection connection)
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
                    AlertBox.displayAlertBox("Alert","You have signed up from this email before!");
                    usernameExists = false;
                }
            }

        }catch (SQLException e){
            System.err.println(e);
        }

        return usernameExists;
    }
}
