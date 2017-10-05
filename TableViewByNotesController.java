package inventorygui;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
public class TableViewByNotesController implements Initializable {
    ObservableList<Inventory3> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Inventory3> tableView;
    @FXML
    private TableColumn<Inventory3, String> nameColumn;
    @FXML
    private TableColumn<Inventory3, String> quantityColumn;
    @FXML
    private TableColumn<Inventory3, String> notesColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumn();
        try {
            readInventory("inventory.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableViewByNotesController.class.getName()).log(Level.SEVERE, null, ex);
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
                if (notes.equalsIgnoreCase(inventorygui.SearchWindowController.notesWord)) {
                    list.add(new Inventory3(name, quantity, notes));
                }
            }
        } catch (Exception e) {
        }
        tableView.getItems().setAll(list);
    }
    public static class Inventory3 {
        private final SimpleStringProperty name;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty notes;
        Inventory3(String name, String quantity, String notes) {
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
