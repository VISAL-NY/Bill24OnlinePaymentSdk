package com.bill24.onlinepaymentsdk.model.appearance;


import com.bill24.onlinepaymentsdk.model.appearance.darkMode.DarkModeModel;
import com.bill24.onlinepaymentsdk.model.appearance.lightMode.LightModeModel;
import com.google.gson.annotations.SerializedName;

public class AppearanceModel {
    public LightModeModel getLightMode() {
        return lightMode;
    }

    public void setLightMode(LightModeModel lightMode) {
        this.lightMode = lightMode;
    }

    public DarkModeModel getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(DarkModeModel darkMode) {
        this.darkMode = darkMode;
    }

    @SerializedName("light_mode")
    private LightModeModel lightMode;
    @SerializedName("dark_mode")
    private DarkModeModel darkMode;
}
