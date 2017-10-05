package inventorygui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
public class EditInventoryController implements Initializable {
    @FXML
    private JFXButton saveInventoryButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField editNameTF;
    @FXML
    private JFXTextField editQuantity;
    @FXML
    private JFXTextField editNotes;
    private String name;
    private String quantity;
    private String notes;
    int count;
    Entry[] productArray = new Entry[200];
    @FXML
    private AnchorPane anchorPane2;
    String[] autoName = {"Apple", "Soda", "Beer", "Milk", "Popcorn", "Chips", "Pop",
        "Orange", "Fanta", "Dew", "Chocolate", "Ice-cream", "Sprite"};
    String[] autoNotes = {"Chilled", "Crushed", "Fruits", "Dryfruits", "Snacks"};
    //Creates auto learning text fields
    Set<String> possibleAutoName = new HashSet<>();
    Set<String> possibleAutoNotes = new HashSet<>();
    //Provides predictive auto completion text feild
    private AutoCompletionBinding<String> autoBindingName;
    private AutoCompletionBinding<String> autoBindingNotes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            int c;
            c = readInventory("inventory.txt");
            String[] name1 = new String[c];
            for (int i = 0; i < c; i++) {
                name1[i] = productArray[i].name;
            }
            Collections.addAll(possibleAutoName, autoName);
            Collections.addAll(possibleAutoName, name1);
            Collections.addAll(possibleAutoNotes, autoNotes);

            String[] autoQuantity = new String[100];
            for (int i = 0; i < 100; i++) {
                autoQuantity[i] = Integer.toString(i);
            }
            autoBindingName = TextFields.bindAutoCompletion(editNameTF, possibleAutoName);
            autoBindingNotes = TextFields.bindAutoCompletion(editNotes, possibleAutoNotes);
            editNameTF.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        learnName(editNameTF.getText());
                        break;
                    case TAB:
                        learnName(editNameTF.getText());
                        break;
                    default:
                        break;
                }
            });
            TextFields.bindAutoCompletion(editQuantity, autoQuantity);
            editNotes.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        learnNotes(editNotes.getText());
                        break;
                    case TAB:
                        learnNotes(editNotes.getText());
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(AddAndDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void editInventoryWindow(ActionEvent event) throws Exception {
        Entry product = new Entry();
        boolean isValid;
        isValid = false;
        count = readInventory("inventory.txt");
        name = editNameTF.getText();
        quantity = editQuantity.getText();
        notes = editNotes.getText();
        product.setName(name);
        product.setQuantity(quantity);
        product.setNotes(notes);
        editNameTF.clear();
        editQuantity.clear();
        editNotes.clear();
        String pattern = "[a-z,A-Z!@#$%&*()-+=;:.></?0]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(quantity);
        for (int i = 0; i < quantity.length(); i++) {
            String c = quantity.substring(i);
            if (m.find()) {
                isValid = true;
                break;
            }
        }
        if (name.isEmpty() || quantity.isEmpty() || notes.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
        } else if (isValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Not a valid quantity");
            alert.showAndWait();
        } else {
            for (int i = 0; i < count; i++) {
                if (productArray[i].name.equalsIgnoreCase(name)) {
                    productArray[i].name = name;
                    productArray[i].quantity = quantity;
                    productArray[i].notes = notes;
                    storeInventory("inventory.txt");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle(null);
                    alert.setContentText("Entry " + name + " edited");
                    alert.showAndWait();
                    break;
                }
                if (i == count - 1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Entry not found !\nDo you want to add this entry?");
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
    }

    @FXML
    private void cancelWindow(ActionEvent event) {
        Stage stage = (Stage) anchorPane2.getScene().getWindow();
        stage.close();
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
    public void storeInventory(String file) throws Exception {
        try (PrintStream p = new PrintStream(file)) {
            int z = 0;
            while (z < count) {
                p.println(productArray[z].name + "\t" + productArray[z].quantity + "\t" + productArray[z].notes);
                z++;
            }
            p.close();
            System.out.println("Inventory stored");
        }
    }

    private void loadWindow(String title, String header) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(title));
            Stage stage = new Stage(StageStyle.DECORATED);

            stage.setScene(new Scene(root));
            stage.setTitle(header);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(EditInventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  //Method for auto learning name
    private void learnName(String text) {
        possibleAutoName.add(text);
        if (autoBindingName != null) {
            autoBindingName.dispose();
        }
        autoBindingName = TextFields.bindAutoCompletion(editNameTF, possibleAutoName);
    }
// Method for auto learning notes
    private void learnNotes(String text) {
        possibleAutoNotes.add(text);
        if (autoBindingNotes != null) {
            autoBindingNotes.dispose();
        }
        autoBindingNotes = TextFields.bindAutoCompletion(editNotes, possibleAutoNotes);
    }
}
