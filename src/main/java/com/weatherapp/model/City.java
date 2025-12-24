package com.weatherapp.model;

public class City {
    private String name;
    private String country;
    private double x;
    private double y;

    public City(){}

    public City(String name, String country, double x, double y)
    {
        this.name = name;
        this.country = country;
        this.x = x;
        this.y = y;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double get_x() { return x; }
    public void set_x(double x) { this.x = x; }

    public double get_y() { return y; }
    public void set_y(double y) { this.y = y; }

    public String getDisplayName() {
        return name + ", " + country;
    }
}