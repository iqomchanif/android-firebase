package com.iyus.gardenIOT.model;

public class Setting {
    String name;
    String IDsetting;
    String semprot;
    String led;
    String kipas;
    String valueSettingHThL;
    String mode;

    public Setting(){

    }

    public Setting(String name, String IDsetting, String semprot, String led, String kipas,  String valueSettingHThL,String mode) {
        this.name = name;
        this.IDsetting = IDsetting;
        this.semprot = semprot;
        this.led = led;
        this.kipas = kipas;
        this.mode = mode;
        this.valueSettingHThL = valueSettingHThL;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDsetting() {
        return IDsetting;
    }

    public void setIDsetting(String IDsetting) {
        this.IDsetting = IDsetting;
    }

    public String getSemprot() {
        return semprot;
    }

    public void setSemprot(String semprot) {
        this.semprot = semprot;
    }

    public String getled() {
        return led;
    }

    public void setled(String led) {
        this.led = led;
    }

    public String getKipas() {
        return kipas;
    }

    public void setKipas(String kipas) {
        this.kipas = kipas;
    }

    public String getValueSettingHThL() {
        return valueSettingHThL;
    }

    public void setValueSettingHThL(String valueSettingHThL) {
        this.valueSettingHThL = valueSettingHThL;
    }

}
