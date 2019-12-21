package WorkWithGraphics;

import DataBase.LogirovanieForDb;
import DataBase.WorkWithDbData;
import MainWindow.SignInController;
import WorkWithData.GetDataFromDb;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainGraphicWindowController {

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Button closeB;

    @FXML
    private Button openB;

    @FXML
    private Button highB;

    @FXML
    private Button lowB;

    @FXML
    private Button clearB;

    @FXML
    private Button backB;

    @FXML
    private Button logout;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private ComboBox<String> chooseApp;
    ObservableList<String> chooseApprox = FXCollections.observableArrayList(
            "Quadratic", "Exponential", "Linear", "Hyperbolic"
    );

    @FXML
    private DatePicker dateBegin;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private Button chooseB;

    @FXML
    private TableView<GetDataFromDb> tableData;

    @FXML
    private TableColumn<GetDataFromDb, String> dateColumn;

    @FXML
    private TableColumn<GetDataFromDb, Float> openColumn;

    @FXML
    private TableColumn<GetDataFromDb, Float> highColumn;

    @FXML
    private TableColumn<GetDataFromDb, Float> lowColumn;

    @FXML
    private TableColumn<GetDataFromDb, Float> closeColumn;

    private String currentPosition;

    private ArrayList<GetDataFromDb> dataList;

    private float dataForApprox[];

    private String username = SignInController.setUsername();

    private XYChart.Series<String, Number> series;

    @FXML
    void initialize() {
        chooseApp.setItems(chooseApprox);
    }

    private void drawGraph(String value) {
        series = new XYChart.Series<>();
        ChangeListener<Color> listener = (obs, oldColor, newColor) ->
                updateStyles(lineChart, colorPicker.getValue());
        colorPicker.valueProperty().addListener(listener);
        int i = 0;
        if (dateBegin.getValue() != null && dateEnd.getValue() != null) {
            dataList = WorkWithDbData.getData(username, dateBegin.getValue(), dateEnd.getValue());
            selectTable();
            for (GetDataFromDb getDataFromDb : dataList) {
                series.getData().add(new XYChart.Data<>(getDataFromDb.getDate(), getDataFromDb.getCorrectData(value)));
                i++;
            }
        } else {
            for (GetDataFromDb getDataFromDb : dataList) {
                series.getData().add(new XYChart.Data<>(getDataFromDb.getDate(), getDataFromDb.getCorrectData(value)));
                i++;
            }
        }
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        series.setName(value);
        lineChart.getData().add(series);
    }

    private void drawQuadratic(String valueType) {
        series = new XYChart.Series<>();
        dataForApprox = LeastSquares.quadLeastSquares(dataList, valueType);
        int i = 0;
        for (GetDataFromDb getDataFromDb : dataList) {
            series.getData().add(new XYChart.Data<>(getDataFromDb.getDate(), i * i * dataForApprox[0] + i * dataForApprox[1] + dataForApprox[2]));
            i++;
        }
        LogirovanieForDb.addResults(username, "Mistake is "+dataForApprox[3]+"% , LeastSquares Quadratic for "+valueType+" : a = " + dataForApprox[0] + " , b = " + dataForApprox[1] + " , c = " + dataForApprox[2] + "");
        series.setName("LS Quadratic");
        lineChart.getData().add(series);
    }

    private void drawExponential(String valueType) {
        series = new XYChart.Series<>();
        dataForApprox = LeastSquares.expLeastSquares(dataList, valueType);
        int i = 0;
        for (GetDataFromDb getDataFromDb : dataList) {
            series.getData().add(new XYChart.Data<>(getDataFromDb.getDate(), Math.exp(dataForApprox[0]) * Math.exp(dataForApprox[1] * i)));
            i++;
        }
        LogirovanieForDb.addResults(username, "Mistake is " + dataForApprox[2] + "% , LeastSquares Exponential for "+valueType+" : a = " + dataForApprox[0] + " , b = " + dataForApprox[1] + "");
        series.setName("LS Exponential");
        lineChart.getData().add(series);
    }

    private void drawLinear(String valueType) {
        series = new XYChart.Series<>();
        dataForApprox = LeastSquares.linLeastSquares(dataList, valueType);
        int i = 0;
        for (GetDataFromDb getDataFromDb : dataList) {
            series.getData().add(new XYChart.Data<>(getDataFromDb.getDate(), i * dataForApprox[0] + dataForApprox[1]));
            i++;
        }
        LogirovanieForDb.addResults(username, "Mistake is "+dataForApprox[2]+"% , LeastSquares Linear for "+valueType+" : a = " + dataForApprox[0] + " , b = " + dataForApprox[1] + "");
        series.setName("LS Linear");
        lineChart.getData().add(series);
    }

    private void drawHyperbolic(String valueType) {
        series = new XYChart.Series<>();
        dataForApprox = LeastSquares.hypLeastSquares(dataList, valueType);
        int i = 1;
        for (GetDataFromDb getDataFromDb : dataList) {
            series.getData().add(new XYChart.Data<>(getDataFromDb.getDate(), dataForApprox[0] + dataForApprox[1] / i));
            i++;
        }
        LogirovanieForDb.addResults(username, "Mistake is "+dataForApprox[2]+"% , LeastSquares Hyperbolic for "+valueType+" : a = " + dataForApprox[0] + " , b = " + dataForApprox[1] + "");
        series.setName("LS Hyperbolic");
        lineChart.getData().add(series);
    }

    @FXML
    private void chooseButton() {
        switch (chooseApp.getSelectionModel().getSelectedItem()) {
            case ("Quadratic"):
                drawQuadratic(currentPosition);
                break;
            case ("Exponential"):
                drawExponential(currentPosition);
                break;
            case ("Linear"):
                drawLinear(currentPosition);
                break;
            case ("Hyperbolic"):
                drawHyperbolic(currentPosition);
                break;
        }
    }

    @FXML
    private void closeButton() {
        lineChart.getData().clear();
        currentPosition = closeB.getText();
        drawGraph(closeB.getText());
    }

    @FXML
    private void openButton() {
        lineChart.getData().clear();
        currentPosition = openB.getText();
        drawGraph(openB.getText());
    }

    @FXML
    private void lowButton() {
        lineChart.getData().clear();
        currentPosition = lowB.getText();
        drawGraph(lowB.getText());
    }

    @FXML
    private void highButton() {
        lineChart.getData().clear();
        currentPosition = highB.getText();
        drawGraph(highB.getText());
    }

    @FXML
    private void clearButton() {
        lineChart.getData().clear();
        tableData.getItems().clear();

    }

    @FXML
    private void backButton() {
        backB.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WorkWithData/LoadData.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logout() {
        Platform.exit();
        LogirovanieForDb.log(username, 3);
    }

    private void updateStyles(Node node, Color colorNew) {
        node.setStyle(String.format("CHART_COLOR_1: %s ;", format(colorNew)));
    }

    private String format(Color c) {
        int r = (int) (255 * c.getRed());
        int g = (int) (255 * c.getGreen());
        int b = (int) (255 * c.getBlue());

        return String.format("#%02x%02x%02x", r, g, b);
    }

    private void selectTable() {
        ObservableList<GetDataFromDb> observableList = FXCollections.observableArrayList();
        dateColumn.setCellValueFactory(new PropertyValueFactory<GetDataFromDb, String>("date"));
        openColumn.setCellValueFactory(new PropertyValueFactory<GetDataFromDb, Float>("open"));
        lowColumn.setCellValueFactory(new PropertyValueFactory<GetDataFromDb, Float>("low"));
        highColumn.setCellValueFactory(new PropertyValueFactory<GetDataFromDb, Float>("high"));
        closeColumn.setCellValueFactory(new PropertyValueFactory<GetDataFromDb, Float>("close"));

        observableList.addAll(WorkWithDbData.getData(username, dateBegin.getValue(), dateEnd.getValue()));
        tableData.setItems(observableList);
    }

}
