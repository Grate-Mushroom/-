package com.weatherapp.util;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class WeatherIcon
{
    public static Image loadImageFromUrl(String url) {
        try {
            return new Image(url, 100, 100, true, true);
        } catch (Exception e) {
            return null;
        }
    }
}