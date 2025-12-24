
package com.weatherapp.controller;

import com.weatherapp.exception.CityNotFoundException;
import com.weatherapp.exception.DatabaseException;
import com.weatherapp.exception.WeatherApiException;
import com.weatherapp.model.City;
import com.weatherapp.model.ForecastData;
import com.weatherapp.model.WeatherData;
import com.weatherapp.service.DatabaseService;
import com.weatherapp.service.GeocodingService;
import com.weatherapp.service.WeatherService;
import com.weatherapp.util.WindConverter; // Исправлен импорт
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private WeatherService weatherService = new WeatherService();
    private GeocodingService geocodingService = new GeocodingService();
    private DatabaseService databaseService;

    private City currentCity;
    private Timer searchTimer;

    @FXML
    private ComboBox<City> cityComboBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;
    @FXML
    private VBox currentWeatherBox;
    @FXML
    private VBox forecastContainer;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label statusLabel;

    private ObservableList<City> citySuggestions = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        logger.info("Initializing MainController");

        try {
            databaseService = new DatabaseService();
        } catch (DatabaseException e) {
            logger.error("Failed to initialize database", e);
            showError("Ошибка базы данных", "Не удалось инициализировать базу данных");
        }

        setupUI();
        setupEventHandlers();
        loadLastCity();
    }

    private void setupUI() {
        // Настройка ComboBox
        cityComboBox.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City city) {
                return city != null ? city.getDisplayName() : "";
            }

            @Override
            public City fromString(String string) {
                return null;
            }
        });

        cityComboBox.setItems(citySuggestions);

        // Стилизация
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        progressIndicator.setVisible(false);
        statusLabel.setText("Готово");
    }

    private void setupEventHandlers() {
        searchButton.setOnAction(event -> searchWeather());
        refreshButton.setOnAction(event -> refreshWeather());

        // Автодополнение при вводе
        cityComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() >= 2) {
                Platform.runLater(() -> updateCitySuggestions(newVal));
            }
        });

        cityComboBox.setOnAction(event -> {
            City selectedCity = cityComboBox.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                loadWeatherForCity(selectedCity);
            }
        });
    }

    private void updateCitySuggestions(String query) {
        Task<List<City>> task = new Task<>() {
            @Override
            protected List<City> call() throws CityNotFoundException {
                return geocodingService.getCitySuggestions(query);
            }
        };

        task.setOnSucceeded(event -> {
            List<City> suggestions = task.getValue();
            citySuggestions.setAll(suggestions);
            cityComboBox.show();
        });

        task.setOnFailed(event -> {
            logger.warn("No suggestions for query: {}", query);
            citySuggestions.clear();
        });

        new Thread(task).start();
    }

    private void searchWeather() {
        String query = cityComboBox.getEditor().getText();
        if (query == null || query.trim().isEmpty()) {
            showAlert("Ошибка", "Введите название города");
            return;
        }

        Task<City> task = new Task<>() {
            @Override
            protected City call() throws Exception {
                List<City> cities = geocodingService.getCitySuggestions(query);
                if (cities.isEmpty()) {
                    throw new CityNotFoundException("Город не найден");
                }
                return cities.get(0);
            }
        };

        task.setOnSucceeded(event -> {
            City city = task.getValue();
            loadWeatherForCity(city);
            logger.info("User searched for city: {}", city.getDisplayName());
        });

        task.setOnFailed(event -> {
            Throwable cause = task.getException();
            if (cause instanceof CityNotFoundException) {
                showAlert("Город не найден", "Попробуйте ввести другое название");
            } else {
                showError("Ошибка поиска", cause.getMessage());
            }
            logger.error("Search failed", cause);
        });

        new Thread(task).start();
    }

    private void loadWeatherForCity(City city) {
        this.currentCity = city;
        cityComboBox.getSelectionModel().select(city);

        Task<Void> weatherTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                updateWeatherData();
                saveFavoriteCity(city);
                return null;
            }
        };

        weatherTask.setOnSucceeded(event -> {
            logger.info("Weather loaded for city: {}", city.getDisplayName());
        });

        weatherTask.setOnFailed(event -> {
            showError("Ошибка загрузки", "Не удалось загрузить данные о погоде");
            logger.error("Failed to load weather", weatherTask.getException());
        });

        showProgress(true, "Загрузка данных...");
        new Thread(weatherTask).start();
    }

    private void updateWeatherData() throws WeatherApiException {
        WeatherData currentWeather = weatherService.getCurrentWeather(
                currentCity.get_x(), currentCity.get_y()
        );

        ForecastData forecast = weatherService.getForecast(
                currentCity.get_x(), currentCity.get_y()
        );

        Platform.runLater(() -> {
            displayCurrentWeather(currentWeather);
            displayForecast(forecast);
            showProgress(false, "Готово");
        });
    }

    private void displayCurrentWeather(WeatherData weather) {
        currentWeatherBox.getChildren().clear();

        // Город
        Label cityLabel = new Label(weather.getCityName() + ", " + weather.getSys().getCountry());
        cityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        cityLabel.setTextFill(Color.DARKBLUE);

        // Температура
        Label tempLabel = new Label(String.format("%.1f°C", weather.getMain().getTemperature()));
        tempLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        tempLabel.setTextFill(Color.ORANGERED);

        // Ощущается как
        Label feelsLikeLabel = new Label(String.format("Ощущается как %.1f°C", weather.getMain().getFeelsLike()));
        feelsLikeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        // Иконка погоды
        ImageView weatherIcon = new ImageView();
        if (weather.getWeather().length > 0) {
            String iconUrl = weather.getWeather()[0].getIconUrl();
            try {
                weatherIcon.setImage(new Image(iconUrl, 100, 100, true, false));
            } catch (Exception e) {
                logger.error("Failed to load weather icon", e);
            }
        }

        // Детали
        GridPane detailsGrid = createDetailsGrid(weather);

        VBox content = new VBox(10,
                cityLabel,
                new HBox(20, tempLabel, weatherIcon, detailsGrid),
                feelsLikeLabel

        );
        content.setPadding(new Insets(20));
        currentWeatherBox.getChildren().add(content);
    }

    private GridPane createDetailsGrid(WeatherData weather) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 0, 0, 0));

        // Влажность
        addDetail(grid, 0, 0, "Влажность:", weather.getMain().getHumidity() + "%");

        // Давление
        addDetail(grid, 1, 0, "Давление:", weather.getMain().getPressure() + " гПа");

        // Ветер
        String windInfo = String.format("%.1f м/с %s",
                weather.getWind().getSpeed(),
                WindConverter.getWindDirection(weather.getWind().getDirection()) // Используем WindConverter
        );
        addDetail(grid, 0, 1, "Ветер:", windInfo);

        // Облачность
        addDetail(grid, 1, 1, "Облачность:", weather.getClouds().getCloudiness() + "%");

        // Видимость
        if (weather.getVisibility() != null) {
            int visibilityKm = weather.getVisibility() / 1000;
            addDetail(grid, 0, 2, "Видимость:", visibilityKm + " км");
        }

        // Описание погоды
        if (weather.getWeather().length > 0) {
            String description = weather.getWeather()[0].getDescription();
            description = description.substring(0, 1).toUpperCase() + description.substring(1);
            addDetail(grid, 1, 2, "Погода:", description);
        }

        return grid;
    }

    private void addDetail(GridPane grid, int col, int row, String title, String value) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", 14));

        VBox box = new VBox(5, titleLabel, valueLabel);
        grid.add(box, col, row);
    }
    private void displayForecast(ForecastData forecast) {
        forecastContainer.getChildren().clear();

        if (forecast == null || forecast.getList() == null) {
            return;
        }

        Map<LocalDate, List<ForecastData.ForecastItem>> forecastByDay = forecast.getList().stream()
                .collect(Collectors.groupingBy(item -> item.getDateTime().toLocalDate()));

        List<LocalDate> nextDays = forecastByDay.keySet().stream()
                .sorted()
                .filter(date -> !date.equals(LocalDate.now()))
                .limit(4)
                .collect(Collectors.toList());

        for (LocalDate day : nextDays) {
            List<ForecastData.ForecastItem> dayForecasts = forecastByDay.get(day);
            if (dayForecasts != null && !dayForecasts.isEmpty()) {
                HBox dayForecastBox = createDayForecastBox(day, dayForecasts);
                forecastContainer.getChildren().add(dayForecastBox);
            }
        }
    }

    private HBox createDayForecastBox(LocalDate date, List<ForecastData.ForecastItem> forecasts) {

        double minTemp = forecasts.stream()
                .mapToDouble(f -> f.getMain().getTempMin())
                .min()
                .orElse(0);

        double maxTemp = forecasts.stream()
                .mapToDouble(f -> f.getMain().getTempMax())
                .max()
                .orElse(0);

        String mainWeather = forecasts.get(0).getWeather().get(0).getDescription();
        String iconCode = forecasts.get(0).getWeather().get(0).getIcon();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM", Locale.forLanguageTag("ru"));

        //контейнер для дня
        HBox dayBox = new HBox(10);
        dayBox.setAlignment(Pos.CENTER_LEFT);
        dayBox.setPadding(new Insets(10));
        dayBox.setStyle("-fx-background-color: rgba(182,222,186,0.7); -fx-background-radius: 10;");
        dayBox.setPrefWidth(280);

        ImageView icon = new ImageView();
        try {
            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            icon.setImage(new Image(iconUrl, 50, 50, true, true));
        } catch (Exception e) {
            logger.error("Failed to load forecast icon", e);
        }

        // Информация о дне
        VBox infoBox = new VBox(5);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        // День недели и дата
        Label dayLabel = new Label(date.format(formatter));
        dayLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        dayLabel.setTextFill(Color.DARKBLUE);

        // Погода
        Label weatherLabel = new Label(mainWeather);
        weatherLabel.setFont(Font.font("Arial", 10));
        weatherLabel.setTextFill(Color.BLACK);

        // Температура
        Label tempLabel = new Label(String.format("%.0f° / %.0f°", minTemp, maxTemp));
        tempLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        tempLabel.setTextFill(Color.ORANGERED);

        // Контейнер для температуры
        VBox tempBox = new VBox(2);
        tempBox.setAlignment(Pos.CENTER_RIGHT);
        tempBox.getChildren().addAll(tempLabel);
        HBox.setHgrow(tempBox, Priority.ALWAYS);

        // Добавляем все элементы
        infoBox.getChildren().addAll(dayLabel, weatherLabel);
        dayBox.getChildren().addAll(icon, infoBox, tempBox);

        return dayBox;
    }

    private void saveFavoriteCity(City city) {
        if (databaseService != null) {
            try {
                databaseService.saveFavoriteCity(city);
            } catch (DatabaseException e) {
                logger.error("Failed to save favorite city", e);
            }
        }
    }

    private void loadLastCity() {
        if (databaseService != null) {
            try {
                Optional<City> lastCity = databaseService.loadFavoriteCity();
                if (lastCity.isPresent()) {
                    currentCity = lastCity.get();
                    cityComboBox.getSelectionModel().select(currentCity);
                    loadWeatherForCity(currentCity);
                }
            } catch (DatabaseException e) {
                logger.error("Failed to load last city", e);
            }
        }
    }

    private void refreshWeather() {
        if (currentCity != null) {
            showProgress(true, "Обновление данных...");
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    updateWeatherData();
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                logger.info("Weather refreshed for city: {}", currentCity.getDisplayName());
            });

            task.setOnFailed(event -> {
                showError("Ошибка обновления", "Не удалось обновить данные");
                logger.error("Refresh failed", task.getException());
                showProgress(false, "Ошибка");
            });

            new Thread(task).start();
        } else {
            showAlert("Информация", "Сначала выберите город");
        }
    }

    private void showProgress(boolean show, String message) {
        progressIndicator.setVisible(show);
        statusLabel.setText(message);
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void shutdown() {
        if (databaseService != null) {
            databaseService.close();
        }
    }
}



