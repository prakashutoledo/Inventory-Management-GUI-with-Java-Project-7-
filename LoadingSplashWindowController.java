package inventorygui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class LoadingSplashWindowController implements Initializable {
    @FXML
    private StackPane rootPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new SplashScreen().start();
    }
    // Creates a jave thread so that application can load faster
    class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                Platform.runLater(() -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        rootPane.getScene().getWindow().hide();
                    } catch (IOException ex) {
                        Logger.getLogger(LoadingSplashWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

            } catch (InterruptedException ex) {
                Logger.getLogger(LoadingSplashWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
