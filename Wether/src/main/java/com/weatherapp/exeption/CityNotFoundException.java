package com.weatherapp.exception;

public class CityNotFoundException extends Exception {
    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}