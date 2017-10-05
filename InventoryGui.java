package inventorygui;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class InventoryGui extends Application {
     public static Boolean isValid = false;
     public static Parent root;
   
    @Override
    public void start(Stage stage) {
        try {
            root = FXMLLoader.load(getClass().getResource("loadingSplashWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddAndDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
