package com.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ForecastData {
    @SerializedName("list")
    private List<ForecastItem> list;

    @SerializedName("city")
    private CityInfo city;

    public List<ForecastItem> getList() { return list; }
    public CityInfo getCity() { return city; }

    public static class ForecastItem {
        @SerializedName("dt")
        private long timestamp;

        @SerializedName("main")
        private Main main;

        @SerializedName("weather")
        private List<Weather> weather;

        @SerializedName("clouds")
        private Clouds clouds;

        @SerializedName("wind")
        private Wind wind;

        @SerializedName("visibility")
        private int visibility;

        @SerializedName("pop")
        private double pop; // Probability of Precipitation

        @SerializedName("rain")
        private Rain rain;

        @SerializedName("snow")
        private Snow snow;

        @SerializedName("dt_txt")
        private String dateText;

        public long getTimestamp() { return timestamp; }
        public Main getMain() { return main; }
        public List<Weather> getWeather() { return weather; }
        public Clouds getClouds() { return clouds; }
        public Wind getWind() { return wind; }
        public int getVisibility() { return visibility; }
        public double getPop() { return pop; }
        public Rain getRain() { return rain; }
        public Snow getSnow() { return snow; }
        public String getDateText() { return dateText; }

        public LocalDateTime getDateTime() {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp),
                    ZoneId.systemDefault()
            );
        }
    }

    public static class CityInfo {
        @SerializedName("name")
        private String name;

        @SerializedName("country")
        private String country;

        @SerializedName("timezone")
        private int timezone;

        public String getName() { return name; }
        public String getCountry() { return country; }
        public int getTimezone() { return timezone; }
    }

    public static class Main {
        @SerializedName("temp")
        private double temp;

        @SerializedName("feels_like")
        private double feelsLike;

        @SerializedName("temp_min")
        private double tempMin;

        @SerializedName("temp_max")
        private double tempMax;

        @SerializedName("pressure")
        private int pressure;

        @SerializedName("humidity")
        private int humidity;

        public double getTemp() { return temp; }
        public double getFeelsLike() { return feelsLike; }
        public double getTempMin() { return tempMin; }
        public double getTempMax() { return tempMax; }
        public int getPressure() { return pressure; }
        public int getHumidity() { return humidity; }
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
        private int all;

        public int getAll() { return all; }
    }

    public static class Wind {
        @SerializedName("speed")
        private double speed;

        @SerializedName("deg")
        private int deg;

        @SerializedName("gust")
        private double gust;

        public double getSpeed() { return speed; }
        public int getDeg() { return deg; }
        public double getGust() { return gust; }
    }

    public static class Rain {
        @SerializedName("3h")
        private double threeHours;

        public double getThreeHours() { return threeHours; }
    }

    public static class Snow {
        @SerializedName("3h")
        private double threeHours;

        public double getThreeHours() { return threeHours; }
    }
}