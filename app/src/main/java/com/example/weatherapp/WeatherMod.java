package com.example.weatherapp;

public class WeatherMod {
    private String temperature;
    private String icon;

    private String maxTemp;
    private String minTemp;

    public WeatherMod(String temperature, String icon, String maxTemp, String minTemp) {
        this.temperature = temperature;
        this.icon = icon;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
}
