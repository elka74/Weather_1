package com.example.weather_.model;

public class Main {
    private float temp;
    private int pressure;
    private int humidity;

    public float getTemp() {
        return ((int)Math.round(temp - 273.15));
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return (int)Math.round(pressure / 1.33);
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

}


