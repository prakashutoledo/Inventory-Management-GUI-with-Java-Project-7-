package inventorygui;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
public class AddAndDeleteController implements Initializable {
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private AnchorPane acnhorPane1;
    @FXML
    private JFXButton cancel1;
    @FXML
    private JFXTextField nameTF;
    @FXML
    private JFXTextField quantityTF;
    @FXML
    private JFXTextField notesTF;
    Entry[] productArray = new Entry[200];
    private String name;
    private String quantity;
    private String notes;
    int num_entries;
    int count;
    String[] autoName = {"Apple", "Soda", "Beer", "Milk", "Popcorn", "Chips", "Pop",
        "Orange", "Fanta", "Dew", "Chocolate", "Ice-cream", "Sprite"};
    String[] autoNotes = {"Chilled", "Crushed", "Fruits", "Dryfruits", "Snacks"};
    // Creates hash set for auto learning names and notes
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
            String[] autoQuantity = new String[100];
            for (int i = 0; i < 100; i++) {
                autoQuantity[i] = Integer.toString(i);
            }
            autoBindingName = TextFields.bindAutoCompletion(nameTF, possibleAutoName);
            autoBindingNotes = TextFields.bindAutoCompletion(notesTF, possibleAutoNotes);
            nameTF.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        learnName(nameTF.getText());
                        break;
                    case TAB:
                        learnName(nameTF.getText());
                        break;
                    default:
                        break;
                }
            });
            TextFields.bindAutoCompletion(quantityTF, autoQuantity);
            notesTF.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        learnNotes(notesTF.getText());
                        break;
                    case TAB:
                        learnNotes(notesTF.getText());
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
    private void addButtonAction(ActionEvent event) throws Exception {
        count = readInventory("inventory.txt");
        try {
            num_entries = count;
            Entry product = new Entry();
            name = nameTF.getText();
            quantity = quantityTF.getText();
            notes = notesTF.getText();
            product.setName(name);
            product.setQuantity(quantity);
            product.setNotes(notes);
            productArray[count] = product;
            nameTF.clear();
            quantityTF.clear();
            notesTF.clear();
            boolean isValid = false;
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
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Product already exists\nDo you want to edit this entry?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            productArray[i].name = name;
                            productArray[i].quantity = quantity;
                            productArray[i].notes = notes;
                            storeInventory("inventory.txt");
                            {
                                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                                alert1.setHeaderText(null);
                                alert1.setContentText("Entry " + name + " edited");
                                alert1.showAndWait();
                            }
                            return;
                        } else {
                            return;
                        }
                    }
                }
                count++;
                String tempName, tempQuantity, tempNotes;
                int x, y;
                // Exchange Sort
                for (x = 0; x < count; x++) {
                    for (y = x + 1; y < count; y++) {
                        if (productArray[x].name.compareToIgnoreCase(productArray[y].name) > 0) {
                            tempName = productArray[x].name;
                            tempQuantity = productArray[x].quantity;
                            tempNotes = productArray[x].notes;
                            productArray[x].name = productArray[y].name;
                            productArray[x].quantity = productArray[y].quantity;
                            productArray[x].notes = productArray[y].notes;
                            productArray[y].name = tempName;
                            productArray[y].quantity = tempQuantity;
                            productArray[y].notes = tempNotes;
                        }
                    }
                }
                storeInventory("inventory.txt");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Entry " + name + " stored");
                alert.showAndWait();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Inventory full");
            alert.showAndWait();
        }

    }
    @FXML
    private void deleteButtonAction(ActionEvent event) throws Exception {
        int x;
        x = 0;
        File newFile;
        newFile = new File("inventory.txt");
        Scanner reader = new Scanner(newFile);
        try {
            while (reader.hasNext()) {
                Entry product = new Entry();
                product.setName(name);
                product.setQuantity(quantity);
                product.setNotes(notes);
                productArray[x] = product;
                productArray[x].name = reader.next();
                productArray[x].quantity = reader.next();
                productArray[x].notes = reader.next();
                x++;
            }
        } catch (Exception e) {
        }

        if (nameTF.getText().isEmpty() || quantityTF.getText().isEmpty() || notesTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();

        } else if (x == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No entries are stored to list");
            alert.showAndWait();
        } else {
            name = nameTF.getText();
            quantity = quantityTF.getText();
            notes = notesTF.getText();
            nameTF.clear();
            quantityTF.clear();
            notesTF.clear();
            for (int i = 0; i < x; i++) {
                if (productArray[i].name.equalsIgnoreCase(name)) {
                    productArray[i] = productArray[x - 1];
                    x--;
                    count = x;
                    storeInventory("inventory.txt");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Entry deleted");
                    alert.showAndWait();
                    break;
                }
                if (i == x - 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Entry not found");
                    alert.showAndWait();
                }

            }
        }
    }

    @FXML
    private void CancelWindow1(ActionEvent event) {
        Stage stage = (Stage) acnhorPane1.getScene().getWindow();
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
    // Method for  auto learning Name
    private void learnName(String text) {
        possibleAutoName.add(text);
        if (autoBindingName != null) {
            autoBindingName.dispose();
        }
        autoBindingName = TextFields.bindAutoCompletion(nameTF, possibleAutoName);
    }
    // Method for auto learning notes
    private void learnNotes(String text) {
        possibleAutoNotes.add(text);
        if (autoBindingNotes != null) {
            autoBindingNotes.dispose();
        }
        autoBindingNotes = TextFields.bindAutoCompletion(notesTF, possibleAutoNotes);
    }

}
