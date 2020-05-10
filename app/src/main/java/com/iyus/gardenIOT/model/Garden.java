package com.iyus.gardenIOT.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Garden {
    String id;
    @SerializedName("garden_name")
    @Expose
    String garden_name;
    @SerializedName("garden_humidity")
    @Expose
    String garden_humidity;
    @SerializedName("garden_temperature")
    @Expose
    String garden_temperature;
    @SerializedName("garden_light")
    @Expose
    String garden_light;


    public Garden(String id, String garden_name, String garden_humidity, String garden_temperature, String garden_light) {
        this.id = id;
        this.garden_name = garden_name;
        this.garden_humidity = garden_humidity;
        this.garden_temperature = garden_temperature;
        this.garden_light = garden_light;
    }

    public Garden(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGarden_name() {
        return garden_name;
    }

    public void setGarden_name(String garden_name) {
        this.garden_name = garden_name;
    }

    public String getGarden_humidity() {
        return garden_humidity;
    }

    public void setGarden_humidity(String garden_humidity) {
        this.garden_humidity = garden_humidity;
    }

    public String getGarden_temperature() {
        return garden_temperature;
    }

    public void setGarden_temperature(String garden_temperature) {
        this.garden_temperature = garden_temperature;
    }

    public String getGarden_light() {
        return garden_light;
    }

    public void setGarden_light(String garden_light) {
        this.garden_light = garden_light;
    }
}
