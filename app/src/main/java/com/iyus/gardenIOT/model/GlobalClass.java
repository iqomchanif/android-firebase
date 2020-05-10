package com.iyus.gardenIOT.model;

import android.app.Application;

import java.util.ArrayList;

public class GlobalClass extends Application {
   ArrayList<ListPlant> listPlants;
   ArrayList<Setting> settings;
   String modeNamePlant;
   String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModeNamePlant() {
        return modeNamePlant;
    }

    public void setModeNamePlant(String modeNamePlant) {
        this.modeNamePlant = modeNamePlant;
    }

    public ArrayList<ListPlant> getListPlants() {
        return listPlants;
    }

    public void setListPlants(ArrayList<ListPlant> listPlants) {
        this.listPlants = listPlants;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Setting> settings) {
        this.settings = settings;
    }
}
