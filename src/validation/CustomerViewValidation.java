package validation;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.AlertBox;

public class CustomerViewValidation {

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


    public boolean isNumNOTEmpty(TextField textField){
        boolean b;
        String msg = null;
        if(!fieldNotEmpty(textField)){
            b = false;
            msg = "Required!!";
            textField.clear();
        }else if(!fieldIsNumber(textField)){
            b = false;
            msg = "Invalid";
            textField.clear();
        }else{
            b = true;
        }

        textField.setPromptText(msg);

        return b;
    }


   /* public boolean emptyComboError(ComboBox comboBox){
        boolean b = true;
        String value = comboBox.getValue();
        System.out.println(value);
        if(value.equals("")){
            b = false;
            AlertBox.displayAlertBox("ERROR!","Please select type");
        }

        return b;
    }*/

}
