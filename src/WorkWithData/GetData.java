package WorkWithData;

import DataBase.WorkWithDbData;
import MainWindow.SignInController;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GetData {

    public static void downloadFile(String link) {
        try {
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            Files.copy(inputStream, new File("data.txt").toPath(), REPLACE_EXISTING);
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void chooseDataToDb(File file) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            WorkWithDbData workWithDbData = new WorkWithDbData();
            workWithDbData.addDataToDb(bufferedReader , SignInController.setUsername());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
