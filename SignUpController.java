package inventorygui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignUpController implements Initializable {

    @FXML
    private JFXTextField nameTF;
    @FXML
    private JFXTextField userNameTF;
    @FXML
    private JFXPasswordField passwordTF;
    @FXML
    private JFXPasswordField retypePasswordTF;
    @FXML
    private JFXButton saveButton;
    private String memberName, memberLastName, userName, password, retypePassword;
    Member[] arrayName = new Member[1000];
    int number;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        try {
            number = readUserName("member.txt");
            memberName = nameTF.getText();
            memberLastName = lastName.getText();
            userName = userNameTF.getText();
            password = passwordTF.getText();
            retypePassword = retypePasswordTF.getText();
            Member member = new Member();
            member.setMemberName(memberName);
            member.setMemerLastName(memberLastName);
            member.setUserName(userName);
            member.setPassword(password);
            arrayName[number] = member;
            nameTF.clear();
            lastName.clear();
            userNameTF.clear();
            passwordTF.clear();
            retypePasswordTF.clear();
            if (memberName.isEmpty() || memberLastName.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please enter all fields");
                alert.showAndWait();
            } else if (!password.equals(retypePassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Password doesn't match");
                alert.showAndWait();

            } else if (password.equals(retypePassword)) {
                int r = 0;
                for (int i = 0; i < number; i++) {
                    if (userName.equalsIgnoreCase(arrayName[i].userName)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("User name already exists");
                        alert.showAndWait();
                        return;
                    }
                    r = i;

                }
                if (number == 0) {
                    arrayName[number].memberName = memberName;
                    arrayName[number].memberLastName = memberLastName;
                    arrayName[number].userName = userName;
                    arrayName[number].password = password;
                    number++;
                    writeUserName("member.txt");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Member " + memberName + " is added");
                    alert.showAndWait();

                }

                int number11 = number;
                if (number11 == r + 1) {
                    arrayName[number].memberName = memberName;
                    arrayName[number].memberLastName = memberLastName;
                    arrayName[number].userName = userName;
                    arrayName[number].password = password;
                    number++;
                    writeUserName("member.txt");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Member " + memberName + " is added");
                    alert.showAndWait();

                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No place to add member");
            alert.showAndWait();
        }
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

    public void writeUserName(String file) {
        PrintStream p;
        try {
            p = new PrintStream(file);
            int z = 0;
            while (z < number) {
            p.println(arrayName[z].memberName + "\t" + arrayName[z].memberLastName + "\t" + arrayName[z].userName + "\t" + arrayName[z].password);
            z++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) rootAnchor.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionRetypePassword(ActionEvent event) {
        if (passwordTF.getText().equals(retypePasswordTF.getText())) {
            if (passwordTF.getText().equals(retypePasswordTF.getText())) {
                label.setText("");
            }
        } else {
            label.setText("Password does not match");
        }
    }
}
