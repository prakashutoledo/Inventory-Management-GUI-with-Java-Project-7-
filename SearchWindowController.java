package inventorygui;
import java.io.File;
import java.util.Scanner;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
public class SearchWindowController implements Initializable {
    @FXML
    private JFXTextField byKeyWord;
    @FXML
    private JFXButton buttonByKeyWord;
    @FXML
    private JFXButton buttinByName;
    @FXML
    private JFXTextField searchNameTF;
    @FXML
    private JFXTextField bySearchNotesTF;
    @FXML
    private JFXButton buttonByNotes;
    public static String keyWord;
    public static String nameWord;
    public static String notesWord;
    Entry[] productArray = new Entry[200];
    private String name;
    private String quantity;
    private String notes;
    String[] autoName = {"Apple", "Soda", "Beer", "Milk", "Popcorn", "Chips", "Pop",
        "Orange", "Fanta", "Dew", "Chocolate", "Ice-cream", "Sprite"};
    String[] autoNotes = {"Chilled", "Crushed", "Fruits", "Dryfruits", "Snacks"};
    String[] autoKeyWord = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    Set<String> possibleAutoName = new HashSet<>();
    Set<String> possibleAutoNotes = new HashSet<>();
    private AutoCompletionBinding<String> autoBindingName;
    private AutoCompletionBinding<String> autoBindingNotes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            try {
            int c = readInventory("inventory.txt");
            String[] name1 = new String[c];
            for (int i = 0; i < c; i++) {
                name1[i] = productArray[i].name;
            }
            Collections.addAll(possibleAutoName, autoName);
            Collections.addAll(possibleAutoName, name1);
            Collections.addAll(possibleAutoNotes, autoNotes);
            autoBindingName = TextFields.bindAutoCompletion(searchNameTF, possibleAutoName);
            autoBindingNotes = TextFields.bindAutoCompletion(bySearchNotesTF, possibleAutoNotes);
            searchNameTF.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        learnName(searchNameTF.getText());
                        break;
                    case TAB:
                        learnName(searchNameTF.getText());
                        break;
                    default:
                        break;
                }
            });
            TextFields.bindAutoCompletion(byKeyWord, autoKeyWord);
            bySearchNotesTF.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        learnNotes(bySearchNotesTF.getText());
                        break;
                    case TAB:
                        learnNotes(bySearchNotesTF.getText());
                        break;
                    default: 
                        break;
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(AddAndDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void searchByKeyWord(ActionEvent event) {
    }
    @FXML
    private void searchByKeyWord1(ActionEvent event) throws Exception {
        keyWord = byKeyWord.getText();
        byKeyWord.clear();
        int num_entries = readInventory("inventory.txt");
        if (keyWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all fields");
            alert.showAndWait();
            return;
        }
        for (int i = 0; i < num_entries; i++) {
            if (keyWord.length() < 8) {
                loadWindow("tableViewKeyWord.fxml", "List by initial Keyword");
                break;
            } else if (i == num_entries - 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No list to show");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void searchByName(ActionEvent event) throws Exception {
        nameWord = searchNameTF.getText();
        searchNameTF.clear();
        int num_entries = readInventory("inventory.txt");
        if (nameWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Search by name field is empty");
            alert.showAndWait();
            return;
        }
        for (int i = 0; i < num_entries; i++) {
            if (nameWord.equalsIgnoreCase(productArray[i].name)) {
                loadWindow("tableViewByName.fxml", "List by Names");
                break;
            } else if (i == num_entries - 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText(nameWord +" Doesn't exist\nDo you want to add this entry?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    productArray[i].name = name;
                    productArray[i].quantity = quantity;
                    productArray[i].notes = notes;
                    loadWindow("addAndDelete.fxml", "Add or Delete");
                } else {
                }
            }
        }
    }
    @FXML
    private void searchByNotes(ActionEvent event) throws Exception {
        notesWord = bySearchNotesTF.getText();
        bySearchNotesTF.clear();
        int num_entries = readInventory("inventory.txt");
        if (notesWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all fields");
            alert.showAndWait();
            return;
        }
        for (int i = 0; i < num_entries; i++) {
            if (notesWord.equalsIgnoreCase(productArray[i].notes)) {
                loadWindow("tableViewByNotes.fxml", "List by Notes");
                break;
            } else if (i == num_entries - 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No list to show");
                alert.showAndWait();
            }
        }
    }
    void loadWindow(String title, String header) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(title));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.setTitle(header);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public int readInventory(String file) throws Exception {
        int x;
        x = 0;
        File newFile;
        newFile = new File(file);
        Scanner reader = new Scanner(newFile);
        try {
            while (reader.hasNext()) {
                Entry product = new Entry();
                productArray[x] = product;
                productArray[x].name = reader.next();
                productArray[x].quantity = reader.next();
                productArray[x].notes = reader.next();
                x++;
            }
        } catch (Exception e) {
        }
        return x;
    }
    private void learnName(String text) {
        possibleAutoName.add(text);
        if (autoBindingName != null) {
            autoBindingName.dispose();
        }
        autoBindingName = TextFields.bindAutoCompletion(searchNameTF, possibleAutoName);
    }
    private void learnNotes(String text) {
        possibleAutoNotes.add(text);
        if (autoBindingNotes != null) {
            autoBindingNotes.dispose();
        }
        autoBindingNotes = TextFields.bindAutoCompletion(bySearchNotesTF, possibleAutoNotes);
    }

}
