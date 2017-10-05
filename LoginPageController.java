package inventorygui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class LoginPageController implements Initializable {
    @FXML
    private JFXTextField userNameTF;
    @FXML
    private JFXPasswordField passwordTF;
    @FXML
    private JFXButton logInButton;
    @FXML
    private JFXButton signUpButton;
    @FXML
    private AnchorPane rootPane;
    // Creates a array of member object of length 1000
    Member[] arrayName = new Member[1000];
    int number;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    @FXML
    private void logInButtonAction(ActionEvent event) {
        try {
            String uName, password;
            uName = userNameTF.getText();
            password = passwordTF.getText();
            int numOfMember = readUserName("member.txt");
            for (int i = 0; i < numOfMember; i++) {
                if (uName.equals(arrayName[i].userName) && password.equals(arrayName[i].password)) {
                    loadWindow("FXMLDocument.fxml", "Inventory Managment");
                    rootPane.getScene().getWindow().hide();
                    break;
                }
                if (i == numOfMember - 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Username or password doesn't match");
                    alert.showAndWait();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    @FXML
    private void signUpButtonAction(ActionEvent event) {
        loadWindow("signUp.fxml", "Sign Up Window");
    }
    public int readUserName(String file) throws FileNotFoundException {
        int x;
        x = 0;
        File newFile;
        newFile = new File(file);
        Scanner reader = new Scanner(newFile);
        try {
            while (reader.hasNext()) {
                Member member = new Member();
                arrayName[x] = member;
                arrayName[x].memberName = reader.next();
                arrayName[x].memberLastName = reader.next();
                arrayName[x].userName = reader.next();
                arrayName[x].password = reader.next();
                x++;
            }
        } catch (Exception e) {
        }
        return x;
    }
    public void writeUserName(String file) throws FileNotFoundException {
        try (PrintStream p = new PrintStream(file)) {
            int z = 0;
            while (z < number) {
                p.println(arrayName[z].memberName + "\t" + arrayName[z].memberLastName + "\t"+ arrayName[z].userName + "\t" + arrayName[z].password);
                z++;
            }
            p.close();
            System.out.println("Member added");
        }

    }
}
