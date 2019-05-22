package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void displayAlertBox(String title, String message){
        Stage alertStage = new Stage();

        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle(title);
        alertStage.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button btn = new Button("OK");
        btn.setOnAction(e -> alertStage.close());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label,btn);
        vBox.setAlignment(Pos.CENTER);

        Scene alt = new Scene(vBox);
        alertStage.setScene(alt);
        alertStage.showAndWait();


    }
}
