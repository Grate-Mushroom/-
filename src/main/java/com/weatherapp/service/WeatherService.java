package com.weatherapp.service;

import com.google.gson.Gson;
import com.weatherapp.exception.WeatherApiException;
import com.weatherapp.model.WeatherData;
import com.weatherapp.model.ForecastData;  // Исправьте импорт
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private static final String api = "293a588b333748336de598c68458e33d";  // Замените на ваш ключ
    private static final String Url = "https://api.openweathermap.org/data/2.5";
    private final HttpClient httpClient;
    private final Gson gson;

    public WeatherService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    public WeatherData getCurrentWeather(double lat, double lon) throws WeatherApiException {
        try {
            String url = String.format("%s/weather?lat=%.4f&lon=%.4f&appid=%s&units=metric&lang=ru",
                    Url, lat, lon, api);

            logger.info("Fetching current weather for lat={}, lon={}", lat, lon);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new WeatherApiException("API request failed: " + response.statusCode());
            }

            return gson.fromJson(response.body(), WeatherData.class);
        } catch (Exception e) {
            logger.error("Error fetching current weather", e);
            throw new WeatherApiException("Failed to fetch weather data", e);
        }
    }

    public ForecastData getForecast(double lat, double lon) throws WeatherApiException {
        try {
            String url = String.format("%s/forecast?lat=%.4f&lon=%.4f&appid=%s&units=metric&lang=ru&cnt=40",
                    Url, lat, lon, api);

            logger.info("Fetching forecast for lat={}, lon={}", lat, lon);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new WeatherApiException("API request failed: " + response.statusCode());
            }

            return gson.fromJson(response.body(), ForecastData.class);
        } catch (Exception e) {
            logger.error("Error fetching forecast", e);
            throw new WeatherApiException("Failed to fetch forecast data", e);
        }
    }
}