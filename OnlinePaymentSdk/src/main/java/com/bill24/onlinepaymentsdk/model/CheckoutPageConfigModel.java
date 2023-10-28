package com.bill24.onlinepaymentsdk.model;

import com.bill24.onlinepaymentsdk.model.appearance.AppearanceModel;
import com.google.gson.annotations.SerializedName;

public class CheckoutPageConfigModel {
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public boolean isDisplayBill24Info() {
        return displayBill24Info;
    }

    public void setDisplayBill24Info(boolean displayBill24Info) {
        this.displayBill24Info = displayBill24Info;
    }

    public FaviIconModel getFavicon() {
        return favicon;
    }

    public void setFavicon(FaviIconModel favicon) {
        this.favicon = favicon;
    }
    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
    public AppearanceModel getAppearance() {
        return appearance;
    }

    public void setAppearance(AppearanceModel appearance) {
        this.appearance = appearance;
    }

    @SerializedName("logo")
    private String logo;
    @SerializedName("background_image")
    private  String backgroundImage;
    @SerializedName("display_bill24_info")
    private  boolean displayBill24Info;
    @SerializedName("favicon")
    private FaviIconModel favicon;
    @SerializedName("setting")
    private Setting setting;
    @SerializedName("appearance")
    private AppearanceModel appearance;
}
