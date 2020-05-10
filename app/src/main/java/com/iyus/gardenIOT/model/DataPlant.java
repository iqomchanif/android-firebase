package com.iyus.gardenIOT.model;

public class DataPlant {


    String id;
    String name;
   String humidityTanah;
    String humidity;
    String temperature;
    String light;
    String created_at;
    String clock_at;

    public DataPlant(String id, String name, String humidityTanah, String humidity, String temperature, String light, String created_at, String clock_at) {
        this.id = id;
        this.name = name;
        this.humidityTanah = humidityTanah;
        this.humidity = humidity;
        this.temperature = temperature;
        this.light = light;
        this.created_at = created_at;
        this.clock_at = clock_at;
    }

    public String getClock_at() {
        return clock_at;
    }

    public void setClock_at(String clock_at) {
        this.clock_at = clock_at;
    }

    public String getHumidityTanah() {
        return humidityTanah;
    }

    public void setHumidityTanah(String humidityTanah) {
        this.humidityTanah = humidityTanah;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public DataPlant(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
