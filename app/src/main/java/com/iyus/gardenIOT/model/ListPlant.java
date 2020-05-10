package com.iyus.gardenIOT.model;

import java.util.ArrayList;

public class ListPlant {

int ID;
String name;
ArrayList<DataPlant> plants;
Setting dataSetting;

    public ListPlant(int ID,String name, ArrayList<DataPlant> plants) {
        this.ID=ID;
        this.name = name;
        this.plants = new ArrayList<DataPlant>();
        for(DataPlant plant:plants){
            DataPlant newplant= new DataPlant(plant.getId(),plant.getName(),plant.getHumidityTanah(),plant.getHumidity(),plant.getTemperature(),plant.getLight(),plant.getCreated_at(),plant.getClock_at());
            this.plants.add(newplant);
        }
        Setting setting= new Setting(name,"","","","","","");
        this.dataSetting=(setting);
    }

    public ListPlant(int ID, String name, ArrayList<DataPlant> plants, Setting dataSetting) {
        this.ID = ID;
        this.name = name;
        this.plants = plants;
        this.dataSetting = dataSetting;
    }

    public Setting getDataSetting() {
        return dataSetting;
    }

    public void setDataSetting(Setting dataSetting) {
        this.dataSetting = dataSetting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DataPlant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<DataPlant> plants) {
        this.plants = plants;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}
