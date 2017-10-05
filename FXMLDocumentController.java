package inventorygui;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.RotateEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class FXMLDocumentController implements Initializable {
    @FXML
    private Button addAndDeleteButton;
    @FXML
    private ImageView addDelImage;
    @FXML
    private Button listButton;
    @FXML
    private ImageView listImage;
    @FXML
    private Button settingsButton;
    @FXML
    private ImageView settingsImage;
    @FXML
    private MenuItem newFile;
    @FXML
    private MenuItem menuBarClose;
    @FXML
    private MenuItem prakashInventoryHelp;
    @FXML
    private MenuItem about;
    @FXML
    private Button searchButton;
    @FXML
    private Button editButton;
    @FXML
    private MenuItem searchInventory;
    @FXML
    private MenuItem editInventory;
    @FXML
    private StackPane root;
    public ImageView imageInventory;
    @FXML
    private MenuItem menuPicEffect1;
    @FXML
    private BorderPane borderPane;
    @FXML
    private RadioMenuItem greenMenuEffect;
    @FXML
    private RadioMenuItem tealMenuEffect1;
    @FXML
    private CheckMenuItem blueMenuEffect;
    @FXML
    private CheckMenuItem blackMenuEffect;
    @FXML
    private CheckMenuItem sepiaToneMenuEffect;
    @FXML
    private CheckMenuItem glowToneMenuEffect;
    @FXML
    private CheckMenuItem shadowMenuEffect;
    @FXML
    private MenuItem listInventoryMenu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creates key code combination 
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_ANY));
        listInventoryMenu.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_ANY));
        menuBarClose.setAccelerator(new KeyCodeCombination(KeyCode.F4,KeyCombination.ALT_ANY));
        searchInventory.setAccelerator(new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_ANY));
        editInventory.setAccelerator(new KeyCodeCombination(KeyCode.E,KeyCombination.CONTROL_ANY));
        prakashInventoryHelp.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        if (!InventoryGui.isValid) {
            loadWelcomeScreen();
        }
    }
    @FXML
    private void addDelInventoryWindow(ActionEvent event) {
        loadWindow("addAndDelete.fxml","Add or Delete ");
    }
    @FXML
    private void listInventory(ActionEvent event) throws Exception {
        int number = readInventory("inventory.txt");
        if (number == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No entries to show");
            alert.showAndWait();
        } else if (number > 0) {
            loadWindow("displayTableView.fxml", "List Inventory");
        }
    }
    @FXML
    private void exitInventory(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    private void inventoryHelp(ActionEvent event) {
        loadWindow("inventoryManagementHelp.fxml" , "Help");
    }
    private void loadWelcomeScreen() {
        try {
            InventoryGui.isValid = true;
            StackPane  pane = FXMLLoader.load(getClass().getResource("beginingWindow.fxml"));
            root.getChildren().addAll(pane);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(8), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(8), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);
            fadeIn.play();
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });
            fadeOut.setOnFinished((e) -> {
                try {
                    InventoryGui.root = FXMLLoader.load(getClass().getResource(("FXMLDocument.fxml")));
                    root.getChildren().setAll(InventoryGui.root);

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Method for loading fxml file with title name
    @FXML
    private void addSearchWindow(ActionEvent event) {
        loadWindow("SearchWindow.fxml", "Search");   
    }
    @FXML
    private void aboutInventory(ActionEvent event) {
        loadWindow("aboutInventoryManagement.fxml", "About");
    }
    @FXML
    private void editInventory(ActionEvent event) {
        loadWindow("EditInventory.fxml", "Edit Inventory");
    }
    @FXML
    private void editInventoryMenu(ActionEvent event) {
        loadWindow("EditInventory.fxml", "Edit Inventory");
    }
    public int readInventory(String file) throws Exception {
        int x;
        x = 0;
        File newFile;
        newFile = new File(file);
        Scanner reader = new Scanner(newFile);
        try {
            while (reader.hasNext()) {
                String name = reader.next();
                String quantity = reader.next();
                String notes = reader.next();
                x++;
            }
        } catch (Exception e) {
        }
        return x;
    }
    @FXML
    private void rotateImage(RotateEvent event) {
        imageInventory.setRotationAxis(Point3D.ZERO);
    }
    @FXML
    private void actionEvent1(ActionEvent event) {
        imageInventory.setEffect(null);
        borderPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));   
    }
    @FXML
    private void setBackroundGreen(ActionEvent event) {
        borderPane.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
    }
    @FXML
    private void setBackroundTeal(ActionEvent event) {
        borderPane.setBackground(new Background(new BackgroundFill(Color.TEAL,null,null)));
    }
    @FXML
    private void setBackroundBlue(ActionEvent event) {
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
    }
    @FXML
    private void setBackroundBlack(ActionEvent event) {
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    }
    @FXML
    private void setImageSepiaTone(ActionEvent event) {
        imageInventory.setEffect(new SepiaTone());
    }
    @FXML
    private void setImageGlow(ActionEvent event) {
        imageInventory.setEffect(new Glow());
    }
    @FXML
    private void setImageShadow(ActionEvent event) {
        imageInventory.setEffect(new Shadow());
    }
    void loadWindow(String title, String header) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(title));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setTitle(header);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddAndDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
