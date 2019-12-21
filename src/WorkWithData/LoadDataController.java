package WorkWithData;

import DataBase.LogirovanieForDb;
import MainWindow.SignInController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class LoadDataController {

    @FXML
    private Button buttonChooseFile;

    @FXML
    private TextField linkForDownloading;

    @FXML
    private Button buttonDownload;

    @FXML
    private Button logout;

    @FXML
    private Button buttonGraphics;

    @FXML
    void initialize() {
        buttonChooseFile.setOnAction(event -> {
            FileChooser file_chooser = new FileChooser();
            File fileReady1 = file_chooser.showOpenDialog(buttonChooseFile.getScene().getWindow());
            GetData.chooseDataToDb(fileReady1);
        });

        buttonDownload.setOnAction(event -> {
            //https://raw.githubusercontent.com/shadowmeowdark/LinkForSemestrovayaRabota/master/US2.AAPL_150506_191209.txt
            GetData.downloadFile(linkForDownloading.getText());
            File fileReady2 = new File("data.txt");
            GetData.chooseDataToDb(fileReady2);
        });

        buttonGraphics.setOnAction(event -> {
            Parent root;
            Stage stage = (Stage) buttonGraphics.getScene().getWindow();
            buttonGraphics.getScene().getWindow().hide();
            try {
                root = FXMLLoader.load(getClass().getResource("/WorkWithGraphics/MainGraphicWindow.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setWidth(1366);
                stage.setHeight(768);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logout.setOnAction(event -> {
            LogirovanieForDb.log(SignInController.setUsername(), 3);
            Platform.exit();
        });
    }
}
