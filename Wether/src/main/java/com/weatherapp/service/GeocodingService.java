package com.weatherapp.service;

import com.google.gson.*;
import com.weatherapp.exception.CityNotFoundException;
import com.weatherapp.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GeocodingService {
    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
    private static final String api = "293a588b333748336de598c68458e33d"; // ЗАМЕНИТЕ НА РЕАЛЬНЫЙ КЛЮЧ!
    private static final String Url = "http://api.openweathermap.org/geo/1.0/direct";
    private final HttpClient httpClient;
    private final Gson gson;

    public GeocodingService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<City> getCitySuggestions(String query) throws CityNotFoundException {
        try {
            if (query == null || query.trim().isEmpty() || query.length() < 2) {
                return new ArrayList<>();
            }

            String url = String.format("%s?q=%s&limit=5&appid=%s",
                    Url, query.replace(" ", "%20"), api);

            logger.info("Fetching city suggestions for query: {}", query);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new CityNotFoundException("Geo API request failed with status: " + response.statusCode());
            }

            String responseBody = response.body();
            if (responseBody == null || responseBody.trim().isEmpty()) {
                return new ArrayList<>();
            }

            JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
            List<City> cities = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String name = obj.get("name").getAsString();
                String country = obj.get("country").getAsString();
                double lat = obj.get("lat").getAsDouble();
                double lon = obj.get("lon").getAsDouble();

                City city = new City(name, country, lat, lon);
                cities.add(city);
            }

            logger.info("Found {} suggestions for query: {}", cities.size(), query);
            return cities;

        } catch (Exception e) {
            logger.error("Error fetching city suggestions for query: {}", query, e);
            throw new CityNotFoundException("Cant find city: " + query, e);
        }
    }
}
