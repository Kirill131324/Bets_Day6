package com.example.bets_day6;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private ImageView Foto;

    @FXML
    private ListView<String> Lis = new ListView<>();

    @FXML
    private Label lay1;

    @FXML
    private Label lay2;

    @FXML
    private Button But;

    @FXML
    private ProgressBar Prog;

    @FXML
    private TextField Tex;


    private ObservableList<String> imageURLs = FXCollections.observableArrayList();

    private final String filesFolderPath = "src/main/resources/com/example/bets_day6/files/";
    private String sourceFilename;

    @FXML
    private void initialize() {

        But.setOnAction(click -> {
            if (Tex.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введіть шлях файлу для його копіювання!");
                alert.showAndWait();
                return;
            }
            Thread thread = new Thread(() -> {
                double progress = 0;
                File source = new File(Tex.getText());
                sourceFilename = source.getName();
                File target = new File(filesFolderPath + sourceFilename);

                try (FileInputStream inputStream = new FileInputStream(source);
                     FileOutputStream outputStream = new FileOutputStream(target)) {
                    while (true) {
                        int b = inputStream.read();
                        if (b == -1) {
                            break;
                        }
                        progress = progress+10.0/source.length();
                        Prog.setProgress(progress);
                        outputStream.write(b);
                    }
                    Platform.runLater(() -> {
                        Lis.getItems().add(sourceFilename);
                    });
                } catch (FileNotFoundException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Помилка!");
                        alert.setHeaderText(null);
                        alert.setContentText("Файл не знайдено!");
                        alert.showAndWait();
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        });

        Lis.setOnMouseClicked(click -> {
            String selectedFileName = Lis.getSelectionModel().getSelectedItem();
            if (selectedFileName != null) {
                String imagePath = "file:src/main/resources/com/example/bets_day6/files/" + selectedFileName;
                Foto.setImage(new Image(imagePath));
            }
        });

    }
}