package com.weatherapp.util;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class WeatherIcon {
    private static final Map<String, String> iconMap = new HashMap<>();

    static {
        // Соответствие иконок OpenWeatherMap
        iconMap.put("01d", "☀️"); // clear sky day
        iconMap.put("01n", "🌙"); // clear sky night
        iconMap.put("02d", "⛅"); // few clouds day
        iconMap.put("02n", "☁️"); // few clouds night
        iconMap.put("03d", "☁️"); // scattered clouds
        iconMap.put("03n", "☁️");
        iconMap.put("04d", "☁️☁️"); // broken clouds
        iconMap.put("04n", "☁️☁️");
        iconMap.put("09d", "🌧️"); // shower rain
        iconMap.put("09n", "🌧️");
        iconMap.put("10d", "🌦️"); // rain day
        iconMap.put("10n", "🌧️"); // rain night
        iconMap.put("11d", "⛈️"); // thunderstorm
        iconMap.put("11n", "⛈️");
        iconMap.put("13d", "❄️"); // snow
        iconMap.put("13n", "❄️");
        iconMap.put("50d", "🌫️"); // mist
        iconMap.put("50n", "🌫️");
    }

    public static String getEmoji(String iconCode) {
        return iconMap.getOrDefault(iconCode, "☀️");
    }

    public static Image loadImageFromUrl(String url) {
        try {
            return new Image(url, 100, 100, true, true);
        } catch (Exception e) {
            return null;
        }
    }
}