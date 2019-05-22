package Interface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public void switchScene(Button button, String path, String title){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Stage stage = (Stage) button.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
