package com.weatherapp.util;

public class WindConverter {

    public static double metersPerSecondToKmPerHour(double mps) {
        return mps * 3.6;
    }

    public static double metersPerSecondToMilesPerHour(double mps) {
        return mps * 2.23694;
    }

    public static String getWindDirection(int degrees) {
        String[] directions = {"С", "СВ", "В", "ЮВ", "Ю", "ЮЗ", "З", "СЗ"};
        int index = (int) Math.round(degrees / 45.0) % 8;
        return directions[index];
    }

    public static String formatWindSpeed(double speedMps) {
        return String.format("%.1f м/с (%.0f км/ч)", speedMps, metersPerSecondToKmPerHour(speedMps));
    }
}