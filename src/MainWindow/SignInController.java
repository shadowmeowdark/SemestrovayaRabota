package MainWindow;

import DataBase.LogirovanieForDb;
import DataBase.WorkWithDbData;
import DataBase.WorkWithDbUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class SignInController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInButton;

    @FXML
    private Label information;

    public static String login;

    @FXML
    void initialize() {
        signInButton.setOnAction(event -> {
            login = username.getText();
            String pass = password.getText();
            if (!login.equals("") && !pass.equals("")) {
                if (WorkWithDbUsers.checkUser(login, pass)) {
                    Parent root;
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    LogirovanieForDb.log(login , 2);
                    signInButton.getScene().getWindow().hide();
                    try {
                        root = FXMLLoader.load(getClass().getResource("/WorkWithData/LoadData.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("User is " + login);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    information.setText("Enter correct username or password");
                    information.setTextFill(Color.RED);
                }
            } else {
                information.setText("Enter correct username or password");
                information.setTextFill(Color.RED);
            }
        });
    }

    public static String setUsername(){
        return login;
    }
}
