package com.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application
{
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Loading weather app");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            primaryStage.setTitle("Прогноз погоды");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(1100);
            primaryStage.setMinHeight(700);
            primaryStage.show();
            logger.info("Loading suserfules");
        } catch (Exception e) {
            logger.error("Error loading app", e);
            showErrorDialog(e);
        }
    }

    private void showErrorDialog(Exception e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }


}