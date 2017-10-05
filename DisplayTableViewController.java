package inventorygui;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
public class DisplayTableViewController implements Initializable {
    // Creates list for storing into table view
    ObservableList<Inventory> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<Inventory, String> nameColumn;
    @FXML
    private TableColumn<Inventory, String> quantityColumn;
    @FXML
    private TableColumn<Inventory, String> notesColumn;
    @FXML
    private TableView<Inventory> tableView;
    @FXML
    private JFXTextField filterText;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumn();
        try {
            readInventory("inventory.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DisplayTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initColumn() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }
    private void readInventory(String file) throws FileNotFoundException {
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
                list.add(new Inventory(name, quantity, notes));

            }
        } catch (Exception e) {
        }
        // Creates filter search field into reality
        FilteredList<Inventory> filteredData = new FilteredList<>(list, e -> true);
        filterText.setOnKeyPressed(e -> {
            filterText.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Inventory>) inventory -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Converting into both lower and uppercase filter search keyword
                    String lowerCaseFilter = newValue.toLowerCase();
                    String upperCaseFilter = newValue.toUpperCase();
                    if (inventory.getName().toLowerCase().contains(newValue) ||inventory.getName().toUpperCase().contains(newValue )) {
                        return true;
                    } else if (inventory.getNotes().toLowerCase().contains(newValue) || inventory.getNotes().toUpperCase().contains(newValue)) {
                        return true;
                    } else if (inventory.getQuantity().toLowerCase().contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Inventory> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        });
        tableView.setItems(list);
    }
    public static class Inventory {
        private final SimpleStringProperty name;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty notes;

        Inventory(String name, String quantity, String notes) {
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleStringProperty(quantity);
            this.notes = new SimpleStringProperty(notes);
        }

        public String getName() {
            return name.get();
        }

        public String getQuantity() {
            return quantity.get();
        }

        public String getNotes() {
            return notes.get();
        }
    }
}
