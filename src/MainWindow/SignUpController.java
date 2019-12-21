package MainWindow;

import DataBase.LogirovanieForDb;
import DataBase.WorkWithDbUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SignUpController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpButton;

    @FXML
    private Label information;

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {
            String login = username.getText();
            String pass = password.getText();
            if (!login.equals("") && !pass.equals("")) {
                if (!WorkWithDbUsers.addUserToDb(login, pass)) {
                    LogirovanieForDb.log(login , 1);
                    information.setText("Registration successfully completed");
                    information.setTextFill(Color.GREEN);
                } else {
                    information.setText("User already exists");
                    information.setTextFill(Color.RED);
                }
            } else {
                information.setText("Enter correct username or password");
                information.setTextFill(Color.RED);
            }
        });
    }
}


