package com.weatherapp.util;

public class TemperatureConverter {

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5/9;
    }

    public static String formatTemperature(double temp) {
        return String.format("%.1f°C", temp);
    }

    public static String formatTemperatureWithFeelsLike(double temp, double feelsLike) {
        return String.format("%.1f°C (ощущается как %.1f°C)", temp, feelsLike);
    }
}