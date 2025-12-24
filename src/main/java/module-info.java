module com.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;
    requires com.google.gson;
    requires java.sql;
    requires org.slf4j;

    opens com.weatherapp to javafx.fxml;
    opens com.weatherapp.controller to javafx.fxml;
    opens com.weatherapp.model to com.google.gson;

    exports com.weatherapp;
    exports com.weatherapp.controller;

    // Указываем главный класс
    provides javafx.application.Application with com.weatherapp.MainApp;
}