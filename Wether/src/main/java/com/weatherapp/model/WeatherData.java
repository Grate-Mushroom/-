package com.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WeatherData {
    @SerializedName("name")
    private String cityName;
    @SerializedName("main")
    private Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("weather")
    private Weather[] weather;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("dt")
    private long timestamp;
    @SerializedName("timezone")
    private int timezone;
    @SerializedName("visibility")
    private int visibility;

    public String getCityName() { return cityName; }
    public Main getMain() { return main; }
    public Wind getWind() { return wind; }
    public Weather[] getWeather() { return weather; }
    public Clouds getClouds() { return clouds; }
    public Sys getSys() { return sys; }
    public long getTimestamp() { return timestamp; }
    public int getTimezone() { return timezone; }
    public Integer getVisibility() { return visibility; }
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneOffset.ofTotalSeconds(timezone)
        );
    }

    // Inner classes (остаются без изменений)
    public static class Main {
        @SerializedName("temp")
        private double temperature;

        @SerializedName("feels_like")
        private double feelsLike;

        @SerializedName("temp_min")
        private double tempMin;

        @SerializedName("temp_max")
        private double tempMax;

        @SerializedName("humidity")
        private int humidity;

        @SerializedName("pressure")
        private int pressure;

        @SerializedName("sea_level")
        private Integer seaLevel;

        @SerializedName("grnd_level")
        private Integer groundLevel;

        public double getTemperature() { return temperature; }
        public double getFeelsLike() { return feelsLike; }
        public double getTempMin() { return tempMin; }
        public double getTempMax() { return tempMax; }
        public int getHumidity() { return humidity; }
        public int getPressure() { return pressure; }
        public Integer getSeaLevel() { return seaLevel; }
        public Integer getGroundLevel() { return groundLevel; }
    }

    public static class Wind {
        @SerializedName("speed")
        private double speed;

        @SerializedName("deg")
        private int direction;

        @SerializedName("gust")
        private Double gust;

        public double getSpeed() { return speed; }
        public int getDirection() { return direction; }
        public Double getGust() { return gust; }


    }

    public static class Weather {
        @SerializedName("id")
        private int id;

        @SerializedName("main")
        private String main;

        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon;

        public int getId() { return id; }
        public String getMain() { return main; }
        public String getDescription() { return description; }
        public String getIcon() { return icon; }

        public String getIconUrl() {
            return "https://openweathermap.org/img/wn/" + icon + "@2x.png";
        }
    }

    public static class Clouds {
        @SerializedName("all")
        private int cloudiness;

        public int getCloudiness() { return cloudiness; }
    }

    public static class Sys {
        @SerializedName("country")
        private String country;

        @SerializedName("sunrise")
        private long sunrise;

        @SerializedName("sunset")
        private long sunset;

        public String getCountry() { return country; }
        public long getSunrise() { return sunrise; }
        public long getSunset() { return sunset; }
    }
}