package MainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.SLATEGREY);
        primaryStage.setScene(scene);
        primaryStage.setWidth(750);
        primaryStage.setHeight(440);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
